package com.doctorblinch;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ButtonFragment extends Fragment implements View.OnClickListener{

    private OnButtonFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        final Button ok_btn = view.findViewById(R.id.orderButtonFragment);
        ok_btn.setOnClickListener(this);
        return view;
    }

    interface OnButtonFragmentInteractionListener {
        void onOrderClick();
    }

    @Override
    public void onAttach(Activity MainActivity) {
        super.onAttach(MainActivity);
        mListener = (   OnButtonFragmentInteractionListener) MainActivity;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.orderButtonFragment) {
            mListener.onOrderClick();
        }
    }
}
