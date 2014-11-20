package com.iiitb.yfs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomeTabsAdapter extends FragmentStatePagerAdapter {
	
	String emailID;
	
    public HomeTabsAdapter(FragmentManager fm, String emailID) {
    super(fm);
    this.emailID = emailID;
    // TODO Auto-generated constructor stub
  }
  @Override
  public Fragment getItem(int i) {
	    switch (i) {
	        case 0:
	        	//Fragment for news feed
	        	return new NewsFeed();
	            
	        case 1:
	        	//Fragment for project details
	        	return new ListProjects(emailID);
	        	
	        }
	    return null;
	  }
	  @Override
	  public int getCount() {
	    // TODO Auto-generated method stub
	    return 2; //No of Tabs
	  }
	}