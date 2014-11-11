package com.iiitb.yfs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProjectsTabAdapter extends FragmentStatePagerAdapter {
    public ProjectsTabAdapter(FragmentManager fm) {
    super(fm);
    // TODO Auto-generated constructor stub
  }
  @Override
  public Fragment getItem(int i) {
	    switch (i) {
	        case 0:
	        	//Fragment for project details
	        	return new NewsFeed();
	            
	        case 1:
	        	//Fragment for news feed
	        	return new ListProjects();
	        }
	    return null;
	  }
	  @Override
	  public int getCount() {
	    // TODO Auto-generated method stub
	    return 2; //No of Tabs
	  }
	}