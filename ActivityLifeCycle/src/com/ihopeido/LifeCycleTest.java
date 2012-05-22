package com.ihopeido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LifeCycleTest extends Activity {
	private static final String TAG = "LifeCycleTester";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.main);
        Button launchButton = (Button)findViewById(R.id.launchbutton);
        Button showDialog = (Button)findViewById(R.id.showdialog);
        
        launchButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		startActivity(new Intent(LifeCycleTest.this,SubActivity.class));
        	}
        });
        
        showDialog.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		AlertDialog.Builder dialog = 
        				new AlertDialog.Builder(LifeCycleTest.this);
        		dialog.setMessage("dialog!");
        		dialog.setTitle("Dialog Title");
        		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
        		dialog.show();
        	}
        });
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause()");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i(TAG, "onRestart()");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume()");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "onStart()...");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop()");
	}
}