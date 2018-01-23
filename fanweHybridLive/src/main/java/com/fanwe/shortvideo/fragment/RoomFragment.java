package com.fanwe.shortvideo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fanwe.live.R;

import java.util.Locale;
import java.util.Random;

/**
 * Created by wxy on 2018/1/20.
 */

public class RoomFragment extends Fragment {

    private Button mButton;
    private Random random = new Random();
    public String sv_id;

    public static RoomFragment newInstance() {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mButton = (Button) view.findViewById(R.id.btn_manage);
        mButton.setText(String.format(Locale.getDefault(), "映客号: %s", sv_id + random.nextInt(10)));
    }


}
