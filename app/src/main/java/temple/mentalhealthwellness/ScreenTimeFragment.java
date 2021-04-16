package temple.mentalhealthwellness;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScreenTimeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Usage Stats Activity";
    private static final boolean localLOGV = false;
    private UsageStatsManager mUsageStatsManager;
    private LayoutInflater mInflater;
    private UsageStatsAdapter mAdapter;
    private PackageManager pm;
    Context parent;
    private int totalScreenTime = 0;
    private View view;

    public static class UsageTimeComparator implements Comparator<UsageStats> {
        @Override
        public final int compare(UsageStats a, UsageStats b) {
            return (int)(b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
        }
    }


    static class AppViewHolder {
        TextView packageName;
        TextView usageTime;
    }

    class UsageStatsAdapter extends BaseAdapter {
        private static final int _DISPLAY_ORDER_USAGE_TIME = 0;


        private int mDisplayOrder = _DISPLAY_ORDER_USAGE_TIME;
        private UsageTimeComparator timeComparator = new UsageTimeComparator();
        private final ArrayMap<String, String> labelMap = new ArrayMap<>();
        private final ArrayList<UsageStats> packageStats = new ArrayList<>();

        UsageStatsAdapter() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -5);

            final List<UsageStats> stats =
                    mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                            cal.getTimeInMillis(), System.currentTimeMillis());
            if (stats == null) {
                return;
            }

            ArrayMap<String, UsageStats> map = new ArrayMap<>();
            final int statCount = stats.size();
            for (int i = 0; i < statCount; i++) {
                final android.app.usage.UsageStats pStats = stats.get(i);

                try {
                    ApplicationInfo appInfo = pm.getApplicationInfo(pStats.getPackageName(), 0);
                    String label = appInfo.loadLabel(pm).toString();
                    labelMap.put(pStats.getPackageName(), label);

                    UsageStats existingStats =
                            map.get(pStats.getPackageName());
                    if (existingStats == null) {
                        map.put(pStats.getPackageName(), pStats);
                    } else {
                        existingStats.add(pStats);
                    }

                } catch (PackageManager.NameNotFoundException e) {
                }
            }
            packageStats.addAll(map.values());

            sortList();
        }





        @Override
        public int getCount() {
            return packageStats.size();
        }

        @Override
        public Object getItem(int position) {
            return packageStats.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AppViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.usage_stats_item, null);

                holder = new AppViewHolder();
                holder.packageName = (TextView) convertView.findViewById(R.id.package_name);
                holder.usageTime = (TextView) convertView.findViewById(R.id.usageTime);
                convertView.setTag(holder);
            } else {

                holder = (AppViewHolder) convertView.getTag();
            }

            UsageStats packageStats = this.packageStats.get(position);
            if (packageStats != null) {
                String label = labelMap.get(packageStats.getPackageName());
                holder.packageName.setText(label);
                holder.usageTime.setText(
                        DateUtils.formatElapsedTime(packageStats.getTotalTimeInForeground() / 1000));
            } else {
                Log.w(TAG, "No usage stats info for package:" + position);
            }
            return convertView;
        }


        public void getTotalTime() {

            int i = 0;
            totalScreenTime = 0;
            while (i < getCount()) {
                totalScreenTime += ((UsageStats)getItem(i)).getTotalTimeInForeground() / 1000;
                i++;
            }

            int seconds = totalScreenTime % 60;
            int hours = totalScreenTime / 60;
            int minutes = hours % 60;
            hours = hours / 60;

            String minutesString = "" + minutes;
            if (minutesString.length() == 1) {
                minutesString = "0" + minutesString;
            }

            String secondsString = "" + seconds;
            if (secondsString.length() == 1) {
                secondsString = "0" + secondsString;
            }

            String time = hours + ":" + minutesString + ":" + secondsString;
            TextView label = (TextView) view.findViewById(R.id.totalTime);
            label.setText(time);

        }


        void sortList(int sortOrder) {
            if (mDisplayOrder == sortOrder) {
                return;
            }
            mDisplayOrder= sortOrder;
            sortList();
        }
        private void sortList() {
            if (localLOGV) Log.i(TAG, "Sorting by usage time");
            Collections.sort(packageStats, timeComparator);

            notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            parent = context;
        } else {
            throw new RuntimeException();
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.usage_stats, container, false);

        mUsageStatsManager = (UsageStatsManager) ((MainActivity)parent).getSystemService(Context.USAGE_STATS_SERVICE);
        mInflater = (LayoutInflater) ((MainActivity)parent).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pm = ((MainActivity)parent).getPackageManager();
//        requestPermissions();

        ListView listView = (ListView) view.findViewById(R.id.pkg_list);
        mAdapter = new UsageStatsAdapter();
        listView.setAdapter(mAdapter);
        ((UsageStatsAdapter) listView.getAdapter()).getTotalTime();


        return view;

    }





//    private void requestPermissions() {
//        List<UsageStats> stats = mUsageStatsManager
//                .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis());
//        boolean isEmpty = stats.isEmpty();
//        if (isEmpty) {
//
//            new AlertDialog.Builder(parent)
//                    .setMessage("Do you want to allow usage permissions")
//                    .setTitle("Allow Permissions")
//                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//                        }
//
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .show();
//
//        }
//    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.sortList(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}