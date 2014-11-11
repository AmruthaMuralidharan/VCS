package com.iiitb.yfs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListProjects extends Fragment {
  @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
          View newProjects = inflater.inflate(R.layout.my_projects, container, false);         
          return newProjects;
}}