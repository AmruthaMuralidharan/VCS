package com.iiitb.yfs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;



public class ListProjects extends ListFragment { 


	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> projList;
	// url to get all projects list
	private static String LIST_URL = StringConstant.BASE_URL+"UserProjectList.php";


	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	// JSONArray
	JSONArray projects = null;
	
	
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    View view = inflater.inflate(R.layout.my_projects, null);
	    
	    projList = new ArrayList<HashMap<String, String>>();
	    new LoadProjects().execute();
	    
	   
	    return view;

	}
	
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadProjects extends AsyncTask<String, String, String> {
		
		
		
		

		protected String doInBackground(String... args) {
			
			// Building Parameters
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json=null;
			try {
				json = jParser.makeHttpRequest(LIST_URL, "GET", params);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Check your log cat for JSON reponse
			Log.d("All projects: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					projects = json.getJSONArray("projects");
					

					// looping through All projects
					for (int i = 0; i < projects.length(); i++) {
						
						JSONObject c = projects.getJSONObject(i);
						
						
						// Storing each json item in variable
						String  pname= c.getString("pname");
						int req = c.getInt("preq");
						
						int avail = c.getInt("pavail");
						int pend = req - avail;

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						if( c.getInt("userid") == (RoleFragment.uid) )
						{
							
						  map.put("pname", pname);
						  map.put("req", req+"");
						  map.put("pavail",avail+"");
						  map.put("pend",pend+"");
						  
						// adding HashList to ArrayList
							projList.add(map);
						  
						}
						
						
					}
				} else {
					 Toast.makeText(getActivity(), "No Projects to display.. :(", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			
			// updating UI from Background Thread
		
			
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter( getActivity(), projList, R.layout.activity_list_projects, 
							new String[] { "pname","req","pavail","pend"},
							new int[] { R.id.name, R.id.required, R.id.available, R.id.pending });
					// updating list view
					setListAdapter(adapter);
				}
			}); 

			
			}
			
		

	}
}