package com.iiitb.yfs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment implements OnClickListener
{
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	    ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.role,container, false);
	    Button coord = (Button) view.findViewById(R.id.button1);
	    coord.setOnClickListener(this);
	    return view;
	}

	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button1:

           Intent in = new Intent(getActivity(),HomeActivity.class);
           startActivity(in);
            break;
        }
    }
	
	
	
}