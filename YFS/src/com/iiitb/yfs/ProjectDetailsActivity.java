package com.iiitb.yfs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectDetailsActivity extends ActionBarActivity
implements MessageDialogFragment.MessageDialogListener{
	
	final Context context = this;
	ShareExternalServer appUtil;
	int year, month, day;
	Button buttonGetVolunteers, buttonSendMessages, buttonCheckinDetails; 
	TextView checkinDetailsText;	
	String userName;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);
		appUtil = new ShareExternalServer();
		userName = getIntent().getStringExtra("emailId");
		InitButtonCheckinDetails();		
		SendMessages();
	}
	
	public void SendMessages(){
		buttonSendMessages = (Button) findViewById(R.id.button2);
		buttonSendMessages.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				showMessagePage();
			}
		});
	}
	
	public void showMessagePage(){
		// Create an instance of the dialog fragment and show it
        DialogFragment dialog = new MessageDialogFragment();
        dialog.show(getSupportFragmentManager(), "MessageDialogFragment");
	}
	
	 @Override
	 public void onMessageDialogPositiveClick(DialogFragment dialog, String toUserName, String messageToSend ){
		 if (TextUtils.isEmpty(toUserName)) {
				Toast.makeText(getApplicationContext(),
						"To User is empty!", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(messageToSend)) {
				Toast.makeText(getApplicationContext(),
						"Message is empty!", Toast.LENGTH_LONG).show();
			} else {
				Log.d("MainActivity", "Sending message to user: "
						+ toUserName);
				sendMessageToGCMAppServer(toUserName, messageToSend);
			}		 
	 }
			
			private void sendMessageToGCMAppServer(final String toUserName,
					final String messageToSend) {
				new AsyncTask<Void, Void, String>() {
					@Override
					protected String doInBackground(Void... params) {

						String result = appUtil.sendMessage(userName, toUserName,
								messageToSend);
						Log.d("ProjectDetailsActivity", "Result: " + result);
						return result;
					}

					@Override
					protected void onPostExecute(String msg) {
						Log.d("ProjectDetailsActivity", "Result: " + msg);
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
								.show();
					}
				}.execute(null, null, null);
			}
	 
	 @Override
	 public void onMessageDialogNegativeClick(DialogFragment dialog){
		 
		 
		 
	 }
	
	public void InitButtonCheckinDetails() {
		buttonCheckinDetails = (Button) findViewById(R.id.button6); 		
		buttonCheckinDetails.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				showDatePicker();
			}
		});
	}
	@SuppressLint("NewApi") public void showDatePicker() {
        // Initializiation
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View customView = inflater.inflate(R.layout.checkin_details, null);
        dialogBuilder.setView(customView);
        final Calendar now = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker) customView.findViewById(R.id.dialog_datepicker);
        final TextView dateTextView = (TextView) customView.findViewById(R.id.date_textView);
        checkinDetailsText = (TextView) customView.findViewById(R.id.checkin_details); 
        final SimpleDateFormat dateViewFormatter = new SimpleDateFormat("EEEE, dd.MM.yyyy", Locale.ENGLISH);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        // Minimum date
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        try {
            minDate.setTime(formatter.parse("12.12.2010"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePicker.setMinDate(minDate.getTimeInMillis());
        datePicker.setMaxDate(maxDate.getTimeInMillis());
        // View settings
        dialogBuilder.setTitle("Choose a date");
        Calendar choosenDate = Calendar.getInstance();
        int year = choosenDate.get(Calendar.YEAR);
        int month = choosenDate.get(Calendar.MONTH);
        int day = choosenDate.get(Calendar.DAY_OF_MONTH);
        try {
            Date choosenDateFromUI = formatter.parse(
            		buttonCheckinDetails.getText().toString()
            );
            choosenDate.setTime(choosenDateFromUI);
            year = choosenDate.get(Calendar.YEAR);
            month = choosenDate.get(Calendar.MONTH);
            day = choosenDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar dateToDisplay = Calendar.getInstance();
        dateToDisplay.set(year, month, day);
        dateTextView.setText(
            dateViewFormatter.format(dateToDisplay.getTime())
        );
        // Buttons
        dialogBuilder.setNegativeButton(
            "Close", 
            new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
               
            }
        );
        dialogBuilder.setPositiveButton(
            "Select", 
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Calendar choosen = Calendar.getInstance();
                    choosen.set(
                        datePicker.getYear(), 
                        datePicker.getMonth(), 
                        datePicker.getDayOfMonth()
                    );
                    checkinDetailsText.setText("blah");                    
                
                }
            }
        );
        final AlertDialog dialog = dialogBuilder.create();
     // Initialize datepicker in dialog atepicker
        datePicker.init(
            year, 
            month, 
            day, 
            new DatePicker.OnDateChangedListener() {
                public void onDateChanged(DatePicker view, int year, 
                    int monthOfYear, int dayOfMonth) {
                    Calendar choosenDate = Calendar.getInstance();
                    choosenDate.set(year, monthOfYear, dayOfMonth);
                    dateTextView.setText(
                        dateViewFormatter.format(choosenDate.getTime())
                    );
                    if (choosenDate.get(Calendar.DAY_OF_WEEK) == 
                        Calendar.SUNDAY || 
                        now.compareTo(choosenDate) < 0) {
                        dateTextView.setTextColor(
                            Color.parseColor("#000000")
                        );
                        ((Button) dialog.getButton(
                        AlertDialog.BUTTON_POSITIVE))
                            .setEnabled(false);
                    } else {
                        dateTextView.setTextColor(
                            Color.parseColor("#000000")
                        );
                        ((Button) dialog.getButton(
                        AlertDialog.BUTTON_POSITIVE))
                            .setEnabled(true);
                    }
                }
            }
        );
        // Finish
        dialog.show();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
