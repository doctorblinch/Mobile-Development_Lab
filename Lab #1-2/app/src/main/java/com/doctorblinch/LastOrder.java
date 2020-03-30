package com.doctorblinch;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LastOrder extends Fragment {

    private TextView tv, tv_label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_order, container, false);

        tv = view.findViewById(R.id.last_order_textT);
        tv.setVisibility(View.INVISIBLE);

        tv_label = view.findViewById(R.id.last_order_label);
        tv_label.setVisibility(View.INVISIBLE);

        return view;
    }

    void setLastOrderText(String lastOrder){
        tv.setVisibility(View.VISIBLE);
        tv_label.setVisibility(View.VISIBLE);
        tv.setText(lastOrder);
    }

}
