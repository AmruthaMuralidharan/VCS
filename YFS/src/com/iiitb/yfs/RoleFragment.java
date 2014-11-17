package com.iiitb.yfs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class RoleFragment extends Fragment implements OnClickListener
{
	public static int uid;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    private static final String LOGIN_URL = StringConstant.BASE_URL+"login.php";
 
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	
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
	        // TODO Auto-generated method stub
	                new AttemptLogin().execute();
	    }
	    
	    class AttemptLogin extends AsyncTask<String, String, String> {

	        
	        @Override
	        protected String doInBackground(String... args) {
	            // TODO Auto-generated method stub
	             // Check for success tag
	            int success;
	            try {
	                // Building Parameters
	                List params = new ArrayList();
	                String email = LoginFragment.fbemail;
	                params.add(new BasicNameValuePair("email", email));

	              

	                
	                // getting user details by making HTTP request
	                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
	               
	                String temp = json.getString("uid");
	                uid = Integer.parseInt(temp);

	                // check your log for json response
	                Log.d("Login attempt", json.toString());

	                // check json success tag
	                success = json.getInt(TAG_SUCCESS);
	                if (success == 1) {
	                    Log.d("Login Successful!", json.toString());
	                    Intent i = new Intent(getActivity(), HomeActivity.class);
	                    startActivity(i);
	                    return json.getString(TAG_MESSAGE);
	                }else{
	                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
	                    return json.getString(TAG_MESSAGE);      
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	           return null;  
	        
	        }      
	    }

	
	
}