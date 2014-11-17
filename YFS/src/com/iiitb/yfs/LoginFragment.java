package com.iiitb.yfs;

import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {
	
	public static String fbemail;
	private UiLifecycleHelper uiHelper;
	
	private Session.StatusCallback statusCallback = 
		    new SessionStatusCallback();
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	    ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.login,container, false);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setReadPermissions(Arrays.asList("public_profile","email"));
	    
	    Session session = Session.getActiveSession();
	     if (session != null && session.isOpened()) {
	         // Get the user's data
	         makeMeRequest(session);
	     }
	    
	    return view;	
	    }
	
	public void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	    	
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    // Set the id for the ProfilePictureView
	                    // view that in turn displays the profile picture.
	                 //   profilePictureView.setProfileId(user.getId());
	                    // Set the Textview's text to the user's name.
	                   // userNameView.setText(user.getName());
	                    
	                    fbemail = (String)user.getProperty("email");
	                	
	                   
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	          
	        }
	    });
	    request.executeAsync();
	} 
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (session != null && session.isOpened()) {
	        // Get the user's data.
	        makeMeRequest(session);
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(final Session session, final SessionState state, final Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
	    super.onSaveInstanceState(bundle);
	    uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	private void onClickLogin() {
	    Session session = Session.getActiveSession();
	    if (!session.isOpened() && !session.isClosed()) {
	        session.openForRead(new Session.OpenRequest(this)
	            .setPermissions(Arrays.asList("public_profile","email"))
	            .setCallback(statusCallback));
	    } else {
	        Session.openActiveSession(getActivity(), this, true, statusCallback);
	    }
	}
	
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
	    public void call(Session session, SessionState sessionState, Exception e) {
	    
	                }
	       
	}
	

	

	}
