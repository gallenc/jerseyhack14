package org.opennms.android.openalert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.opennms.android.openalert.R;

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
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Vibrator;

public class MainActivity extends ActionBarActivity {
	public static final int CALLBACKTIMER = 30 * 1000; // in ms
	public static final int ALARMALERTTIMER = 15 * 1000; // in ms
	public static final int ALARMREALERTTIMER = 10 * 1000; // in ms
	public static final String PREFS_NAME = "OpenNMSSettings";
	public static final String SERVERPORT = "8980";
	public static final String SERVER_IP = "demo1.opennms.co.uk";

	private Location currentLocation;
	private Timer pingTimer;
	MyTimerTask myTimerTask;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;
	int alarmCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		currentLocation = mlocManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (currentLocation == null) {
			currentLocation = mlocManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		pingTimer = new Timer();
		myTimerTask = new MyTimerTask();
		pingTimer.schedule(myTimerTask, CALLBACKTIMER, CALLBACKTIMER);
		Button btnReport = (Button) findViewById(R.id.reportInButton);
		btnReport.setEnabled(false);
		Button btnFinish = (Button) findViewById(R.id.jobFinishButton);
		btnFinish.setEnabled(false);
		alarmCount = 0;
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
			Intent nextScreen = new Intent(getApplicationContext(),
					Settings.class);
			startActivity(nextScreen);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean btnStartJobClick(View view) {
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("Start");
		setupAlarm();
		String str = BuildEvent("uei.opennms.org/application/mobilelocation/single-working");
		createPost(str);
		Button btnReport = (Button) findViewById(R.id.reportInButton);
		btnReport.setEnabled(true);
		Button btnFinish = (Button) findViewById(R.id.jobFinishButton);
		btnFinish.setEnabled(true);
		return true;
	}

	public boolean btnReportInOnClick(View view) {
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("Running");
		String str = BuildEvent("uei.opennms.org/application/mobilelocation/single-working");
		createPost(str);
		pi = PendingIntent.getBroadcast(this, 0, new Intent(
				"org.opennms.android.openalert"), 0);
		am.cancel(pi);
		alarmCount = 0;
		setupAlarm();
		return true;
	}

	public boolean btnPanicClick(View view) {
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("PANIC MODE");
		String str = BuildEvent("uei.opennms.org/application/mobilelocation/single-working-panic");
		createPost(str);
		return true;
	}

	public boolean btnJobFinishClick(View view) {
		TextView mybox = (TextView) findViewById(R.id.textView1);
		mybox.setText("");
		pi = PendingIntent.getBroadcast(this, 0, new Intent(
				"org.opennms.android.openalert"), 0);
		am.cancel(pi);
		alarmCount = 0;
		String str = BuildEvent("uei.opennms.org/application/mobilelocation/single-working-end");
		createPost(str);
		Button btnReport = (Button) findViewById(R.id.reportInButton);
		btnReport.setEnabled(false);
		Button btnFinish = (Button) findViewById(R.id.jobFinishButton);
		btnFinish.setEnabled(false);
		return true;
	}

	private void createPost(String sEvent) {
		String[] sParametersArray = new String[3];
		SharedPreferences settings = getSharedPreferences(
				MainActivity.PREFS_NAME, 0);
		sParametersArray[0] = settings.getString("openNMSServer",
				MainActivity.SERVER_IP);
		sParametersArray[1] = settings.getString("openNMSPort",
				MainActivity.SERVERPORT);
		sParametersArray[2] = sEvent;
		if (sParametersArray[0].length() > 0
				&& sParametersArray[1].length() > 0) {
			new MakeNMSPost().execute(sParametersArray);
		}
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
					if (alarmCount == 3) {
						mMediaPlayer = MediaPlayer.create(c, R.raw.airhorn);
						mMediaPlayer.start();
					} else {
						Vibrator v = (Vibrator) c
								.getSystemService(Context.VIBRATOR_SERVICE);
						// Vibrate for 2500 milliseconds
						v.vibrate(2500);
						alarmCount += 1;
					}
				} catch (Exception e) {
					Log.e("org.opennms.android.openalert", "", e);
				}
				pi = PendingIntent.getBroadcast(c, 0, new Intent(
						"org.opennms.android.openalert"), 0);
				am = (AlarmManager) (c.getSystemService(Context.ALARM_SERVICE));
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
						SystemClock.elapsedRealtime() + ALARMALERTTIMER, pi);
				Toast.makeText(c, "Need To Check In", Toast.LENGTH_LONG).show();
			}
		};
		registerReceiver(br, new IntentFilter("org.opennms.android.openalert"));
		pi = PendingIntent.getBroadcast(this, 0, new Intent(
				"org.opennms.android.openalert"), 0);
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime() + ALARMREALERTTIMER, pi);
		alarmCount = 0;
	}

	public String BuildEvent(String eventType) {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String devID = telephonyManager.getDeviceId();
		SharedPreferences settings = getSharedPreferences(
				MainActivity.PREFS_NAME, 0);
		String sopenNMSUserName = settings.getString("openNMSUserName",
				"Username Undefined");
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<restEventMessage xmlns:ns2=\"http://xmlns.opennms.org/xsd/event\">"
				+ "  <eventList>"
				+ "<event>"
				+ "<ns2:uei>"
				+ eventType
				+ "</ns2:uei>"
				+ "<ns2:source>OpenNMS Android Application</ns2:source>"
				+ "<ns2:time>"
				+ new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date())
				+ "</ns2:time>"
				+ "<ns2:host>mack-virtual-machine</ns2:host>"
				+ "<ns2:severity>warning</ns2:severity>"
				+ "<ns2:parms>"
				+ " <ns2:parm>"
				+ " <ns2:parmName><![CDATA[workerid]]></ns2:parmName>"
				+ "  <ns2:value type=\"string\" encoding=\"text\"><![CDATA["
				+ sopenNMSUserName
				+ "]]></ns2:value>"
				+ " </ns2:parm>"
				+ "<ns2:parm>"
				+ "<ns2:parmName><![CDATA[longitude]]></ns2:parmName>";
		if (currentLocation == null) {
			str = str
					+ "<ns2:value type=\"string\" encoding=\"text\"><![CDATA[0]]></ns2:value>"
					+ " </ns2:parm>"
					+ " <ns2:parm>"
					+ " <ns2:parmName><![CDATA[latitude]]></ns2:parmName>"
					+ "  <ns2:value type=\"string\" encoding=\"text\"><![CDATA[0]]></ns2:value>"
					+ " </ns2:parm>"
					+ " <ns2:parm>"
					+ " <ns2:parmName><![CDATA[locationvalid]]></ns2:parmName>"
					+ "  <ns2:value type=\"string\" encoding=\"text\"><![CDATA[false]]></ns2:value>"
					+ " </ns2:parm>";
		} else {
			str = str
					+ "<ns2:value type=\"string\" encoding=\"text\"><![CDATA["
					+ currentLocation.getLongitude()
					+ "]]></ns2:value>"
					+ " </ns2:parm>"
					+ " <ns2:parm>"
					+ " <ns2:parmName><![CDATA[latitude]]></ns2:parmName>"
					+ "  <ns2:value type=\"string\" encoding=\"text\"><![CDATA["
					+ currentLocation.getLatitude()
					+ "]]></ns2:value>"
					+ " </ns2:parm>"
					+ " <ns2:parm>"
					+ " <ns2:parmName><![CDATA[locationvalid]]></ns2:parmName>"
					+ "  <ns2:value type=\"string\" encoding=\"text\"><![CDATA[true]]></ns2:value>"
					+ " </ns2:parm>";
		}

		str = str + " <ns2:parm>"
				+ "  <ns2:parmName><![CDATA[assetnumber]]></ns2:parmName>"
				+ " <ns2:value type=\"string\" encoding=\"text\"><![CDATA["
				+ devID + "]]></ns2:value>" + " </ns2:parm>" + " </ns2:parms>"
				+ "</event>" + "</eventList> " + "</restEventMessage>";
		return str;
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					String str = BuildEvent("uei.opennms.org/application/mobilelocation");

					createPost(str);
					Toast.makeText(getApplicationContext(), "Pinged Home",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

	public class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location loc) {
			currentLocation = loc;
			loc.getLatitude();
			loc.getLongitude();
			String Text = "My current location is: " + "Latitude = "
					+ loc.getLatitude() + "Longitude = " + loc.getLongitude();
			Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT)
			.show();
		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Disabled",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Enabled",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}/* End of Class MyLocationListener */

	@Override
	protected void onDestroy() {
		if (am != null) {
			am.cancel(pi);
			unregisterReceiver(br);
		}
		pingTimer.cancel();
		super.onDestroy();
	}

}

class MakeNMSPost extends AsyncTask<String, Void, Void> {

	// private Exception exception;
	// private Socket socket;

	protected Void doInBackground(String... arg0) {
		try {

			String sServerName = arg0[0];
			String sServerPort = arg0[1];
			
			String sUserName = "loneworkeruser";
			String sPassword = "loneworkergwpass1";

			CredentialsProvider credProvider = new BasicCredentialsProvider();
			credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST,
					AuthScope.ANY_PORT), new UsernamePasswordCredentials(
							sUserName, sPassword));

			Log.i("openNMS", "BEGIN SENDING MESSAGE");
			String postURL = "http://" + sServerName + ":" + sServerPort
					+ "/opennms/eventgateway/id/loneworkergw";
			// <opennms_home>/eventgateway/id/<gatewayid>

			Log.i("openNMS", "sUserName="+sUserName + " sPassword="+ sPassword + " postURL="+postURL);

			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			defaultHttpClient.setCredentialsProvider(credProvider);
			
			HttpParams params = new BasicHttpParams();
			params.setParameter("http.protocol.handle-redirects",false);

			HttpPost httppost = new HttpPost(postURL);
			httppost.setParams(params);

			// String xml_string_to_send =
			// "<?xml version='1.0' encoding='utf-8'?><root><items><item>1</item><item>2</item></items></root>";
			String xml_string_to_send = arg0[2];

			Log.i("openNMS", "xml_string_to_send="+xml_string_to_send);

			StringEntity string_entity = new StringEntity(xml_string_to_send,
					HTTP.UTF_8);
			string_entity.setContentType("application/xml");

			httppost.setEntity(string_entity);

			HttpResponse response = defaultHttpClient.execute(httppost);

			Log.i("openNMS", "returned from defaultHttpClient.execute(httppost)");

			//
			String response_string=null;

			if (response != null ){

				int statuscode = response.getStatusLine().getStatusCode();
				if(statuscode!=200){
					Log.e("openNMS", "statuscode="+statuscode);
				}
				Log.i("openNMS", "statuscode="+statuscode);

				if (response.getEntity() != null) {
					HttpEntity response_entity = response.getEntity();
					response_string = EntityUtils.toString(response_entity);
					Log.i("openNMS", "response_string="+response_string);
				} else Log.i("openNMS", "response.getEntity()==null");
			} else Log.i("openNMS", "response==null");

			//			if (response != null && response.getEntity() != null) {
			//                InputStream content = response.getEntity().getContent();
			//                if (content != null) {
			//                    BufferedReader reader = new BufferedReader(
			//                            new InputStreamReader(content, "UTF-8"));
			//                    while (true) {
			//                        String addingSource = reader.readLine();
			//                        if (addingSource != null) {
			//                        	response_string = addingSource;
			//                            break;
			//                        }
			//                    }
			//                }
			//            }


			
		} catch(SocketException e1)  {
		    Log.e("openNMS" , "problem with socket " + e1.getMessage());
		} catch (IllegalArgumentException e1) {
			Log.e("openNMS", e1.toString());
		} catch (ClientProtocolException e1) {
			Log.e("openNMS", e1.toString());
		} catch (IOException e1) {
			Log.e("openNMS", e1.toString());
		} finally {

		}

		return null;
	}

}
// uses raw socket
// class MakeNMSPost extends AsyncTask<String, Void, Void> {
//
// //private Exception exception;
// private Socket socket;
//
// protected Void doInBackground(String... arg0) {
// try {
// String sServerName = arg0[0];
// InetAddress serverAddr = InetAddress.getByName(sServerName);
// int iServerPort = Integer.parseInt( arg0[1]);
// socket = new Socket(serverAddr, iServerPort);
//
// } catch (UnknownHostException e1) {
// Log.e("openNMS",e1.toString());
// } catch (IOException e1) {
// Log.e("openNMS",e1.toString());
// }
// try {
// String str = arg0[2];
// PrintWriter out = new PrintWriter(new BufferedWriter(
// new OutputStreamWriter(socket.getOutputStream())),
// true);
// out.println(str);
// } catch (UnknownHostException e) {
// Log.e("openNMS",e.toString());
// } catch (IOException e) {
// Log.e("openNMS",e.toString());
// } catch (Exception e) {
// Log.e("openNMS",e.toString());
// }
// return null;
// }
//
// }
