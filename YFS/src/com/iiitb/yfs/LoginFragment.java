package com.iiitb.yfs;

import java.util.Arrays;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {
	
	private Session.StatusCallback statusCallback = 
		    new SessionStatusCallback();
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	    ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.login,container, false);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setReadPermissions(Arrays.asList("public_profile"));

	    return view;	
	    }
	
	
	private void onClickLogin() {
	    Session session = Session.getActiveSession();
	    if (!session.isOpened() && !session.isClosed()) {
	        session.openForRead(new Session.OpenRequest(this)
	            .setPermissions(Arrays.asList("public_profile"))
	            .setCallback(statusCallback));
	    } else {
	        Session.openActiveSession(getActivity(), this, true, statusCallback);
	    }
	}

	private class SessionStatusCallback implements Session.StatusCallback {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	            // Respond to session state changes, ex: updating the view
	    }
	}
	

	}
