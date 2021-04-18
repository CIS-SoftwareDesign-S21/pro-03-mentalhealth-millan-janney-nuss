package temple.mentalhealthwellness.ui.home;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import temple.mentalhealthwellness.MainActivity;
import temple.mentalhealthwellness.R;

public class HomeFragment extends Fragment {

    TextView timeLabel;
    String time;
    Context parent;

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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        timeLabel = view.findViewById(R.id.screenTime);
        timeLabel.setText(time);

        return view;
    }

    public void setTotal(String total) {
        System.out.println("HOME TIME" + total);
            time = total;
    }
}