package com.example.test1;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;




import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
	private static final int CALLBACKTIMER = 30*1000; // in ms
	private static final int ALARMALERTTIMER = 15*1000; // in ms
	private static final int ALARMREALERTTIMER = 10*1000; // in ms
	private Location currentLocation ;
	private Timer pingTimer;
	MyTimerTask myTimerTask;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;	 
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		currentLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (currentLocation == null)  {
			currentLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		pingTimer = new Timer();
		myTimerTask = new MyTimerTask();
		pingTimer.schedule(myTimerTask,CALLBACKTIMER,CALLBACKTIMER);
		Button btnReport = (Button) findViewById(R.id.button2);
		btnReport.setEnabled(false);
		Button btnFinish = (Button) findViewById(R.id.button3);
		btnFinish.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public boolean btnStartJobClick(View view){
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("Start");
		setupAlarm();
		String str = BuildEvent("uei.opennms.org/application/mobilelocation-startjob");
		new MakeNMSPost().execute(str);
		Button btnReport = (Button) findViewById(R.id.button2);
		btnReport.setEnabled(true);
		Button btnFinish = (Button) findViewById(R.id.button3);
		btnFinish.setEnabled(true);
		return true;
	}
	public boolean btnReportInOnClick(View view){
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("Running");
		String str = BuildEvent("uei.opennms.org/application/mobilelocation-startjob");
		new MakeNMSPost().execute(str);
		pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.test1"),0 );
		am.cancel(pi);
		setupAlarm();
		return true;
	}
	public boolean btnJobFinishClick(View view){
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("Finish");
		pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.test1"),0 );
		am.cancel(pi);
		String str = BuildEvent("uei.opennms.org/application/mobilelocation-endjob");
		new MakeNMSPost().execute(str);
		Button btnReport = (Button) findViewById(R.id.button2);
		btnReport.setEnabled(false);
		Button btnFinish = (Button) findViewById(R.id.button3);
		btnFinish.setEnabled(false);
		return true;
	}
	/* Class My Location Listener */

	private void setupAlarm() {
	      br = new BroadcastReceiver() {
	             @Override
	             public void onReceive(Context c, Intent i) {
	                    
	                    MediaPlayer mMediaPlayer; 
	                    Uri alert = RingtoneManager
	                            .getDefaultUri(RingtoneManager.TYPE_ALARM);
	                    if (alert == null) {
	                        alert = RingtoneManager
	                                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	                        if (alert == null) {
	                            alert = RingtoneManager
	                                    .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	                        }
	                     }
                         
                         try {
                        	mMediaPlayer = MediaPlayer.create(c, R.raw.airhorn);
                            mMediaPlayer.start();
                        } catch (Exception e) {
                        	Log.e("com.example.test1","",e);
                        }
               	      	pi = PendingIntent.getBroadcast( c, 0, new Intent("com.example.test1"),0 );
               	      	am = (AlarmManager)(c.getSystemService( Context.ALARM_SERVICE ));
               	      	am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ALARMALERTTIMER, pi );
                         Toast.makeText(c, "Need To Check In", Toast.LENGTH_LONG).show();
	                    }
	             };
	      registerReceiver(br, new IntentFilter("com.example.test1") );
	      pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.test1"),0 );
	      am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
	      am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ALARMREALERTTIMER, pi );
	}	
	
	
	public String BuildEvent(String eventType){
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String devID = telephonyManager.getDeviceId();
		String str = "<log>" +
				 "<events>" +
				 "<event>" +
				 "<uei>" + eventType + "</uei>" +
				 "<uei></uei>"+
				 "<source>OpenNMS Android Application</source>"+
				 "<time>Saturday, 27 September 2014 9:23:30 o'clock GMT</time>"+
				 "<host>mack-virtual-machine</host>"+
				"<parms>" +
				    "<parm>" +
				     "<parmName><![CDATA[longitude]]></parmName>";
				    if (currentLocation == null) {
				        str = str +"<value type=\"string\" encoding=\"text\"><![CDATA[0]]></value>" +
				        " </parm>"+
				        " <parm>"+
				        " <parmName><![CDATA[latitude]]></parmName>" +
					    "  <value type=\"string\" encoding=\"text\"><![CDATA[0]]></value>" +
					    " </parm>"+ 
					    " <parm>"+
				        " <parmName><![CDATA[locationvalid]]></parmName>" +
					    "  <value type=\"string\" encoding=\"text\"><![CDATA[false]]></value>" +
					    " </parm>"; 
				    } else{
				    	str = str+"<value type=\"string\" encoding=\"text\"><![CDATA[" + currentLocation.getLongitude() +"]]></value>" +
					    " </parm>"+
					    " <parm>"+
					    " <parmName><![CDATA[latitude]]></parmName>" +
					    "  <value type=\"string\" encoding=\"text\"><![CDATA[" + currentLocation.getLatitude() +"]]></value>" +
					    " </parm>" +
					    " <parm>"+
				        " <parmName><![CDATA[locationvalid]]></parmName>" +
					    "  <value type=\"string\" encoding=\"text\"><![CDATA[true]]></value>" +
					    " </parm>"; 
				    }
				    	
				   str=str+" <parm>" +
				   "  <parmName><![CDATA[assetnumber]]></parmName>" +
				    " <value type=\"string\" encoding=\"text\"><![CDATA["+ devID +"]]></value>" +
				   " </parm>" +
				  " </parms>" +		 
				 "</event>"+
				 "</events>"+
				 "</log>";		
		return str;
	}
	

	 class MyTimerTask extends TimerTask {

	  @Override
	  public void run() {
		  runOnUiThread(new Runnable(){
			  @Override
			  public void run() {
				  String str = BuildEvent("uei.opennms.org/application/mobile-location-updated");
			
				  new MakeNMSPost().execute(str);
					Toast.makeText( getApplicationContext(),
							"Pinged Home",
							Toast.LENGTH_SHORT ).show();
			  }});
	  	}
	  
	 }
	
	
	
	
	public class MyLocationListener implements LocationListener 
	{
		@Override
		public void onLocationChanged(Location loc)
		{
			currentLocation = loc;
			loc.getLatitude();
			loc.getLongitude();
			String Text = "My current location is: " +
			"Latitude = " + loc.getLatitude() +
			"Longitude = " + loc.getLongitude();
			Toast.makeText( getApplicationContext(),
			Text,
			Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(),
			"Gps Disabled",
			Toast.LENGTH_SHORT ).show();
		}
		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(),
			"Gps Enabled",
			Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
		}/* End of Class MyLocationListener */

	@Override

	protected void onDestroy() {
	       am.cancel(pi);
	       unregisterReceiver(br);
	       super.onDestroy();
	}
	
	}

class MakeNMSPost extends AsyncTask<String, Void, Void> {

    //private Exception exception;
	private Socket socket;

	private static final int SERVERPORT = 5817;
	private static final String SERVER_IP = "demo1.opennms.co.uk";
	
	protected Void doInBackground(String... arg0) {  
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

			socket = new Socket(serverAddr, SERVERPORT);

		} catch (UnknownHostException e1) {
			Log.e("openNMS",e1.toString());
		} catch (IOException e1) {
			Log.e("openNMS",e1.toString());
		}
		try {
			String str = arg0[0];
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),
					true);
			out.println(str);
		} catch (UnknownHostException e) {
			Log.e("openNMS",e.toString());
		} catch (IOException e) {
			Log.e("openNMS",e.toString());
		} catch (Exception e) {
			Log.e("openNMS",e.toString());
		}
		return null;
	}

}

