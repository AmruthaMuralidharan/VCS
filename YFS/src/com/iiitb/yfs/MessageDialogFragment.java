package com.iiitb.yfs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MessageDialogFragment extends DialogFragment {
	
	EditText toUser, message;
	
	 public interface MessageDialogListener {
	        public void onMessageDialogPositiveClick(DialogFragment dialog, String toUserName, String messageToSend);
	        public void onMessageDialogNegativeClick(DialogFragment dialog);
	    }
	 
	 MessageDialogListener mListener;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	   final View customView = inflater.inflate(R.layout.send_message, null);
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(customView)
	 // Add action buttons
        .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            	toUser = (EditText) customView.findViewById(R.id.toUser);
       		 String toUserName = toUser.getText().toString();

       			message = (EditText) customView.findViewById(R.id.message);
       			String messageToSend = message.getText().toString();
       			
                mListener.onMessageDialogPositiveClick(MessageDialogFragment.this, toUserName, messageToSend);
                MessageDialogFragment.this.getDialog().cancel();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	MessageDialogFragment.this.getDialog().cancel();
            }
        });      
 return builder.create();
		
	}
	
	 // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (MessageDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement MessageDialogListener");
        }
    }

}
