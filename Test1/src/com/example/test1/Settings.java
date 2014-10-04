package com.example.test1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        TextView txtopenNMSUserName = (TextView) findViewById(R.id.openNMSUserName);
        TextView txtopenNMSServer = (TextView) findViewById(R.id.openNMSServer);
        TextView txtopenNMSPort = (TextView) findViewById(R.id.openNMSPort);
        Button btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        Button btnClearSettings = (Button) findViewById(R.id.btnClearAllSettings);
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String sopenNMSUserName = settings.getString("openNMSUserName", "");
        String sopenNMSServer = settings.getString("openNMSServer", MainActivity.SERVER_IP);
        String sopenNMSPort =settings.getString("openNMSPort", MainActivity.SERVERPORT);
        txtopenNMSUserName.setText(sopenNMSUserName);
        txtopenNMSServer.setText(sopenNMSServer);
        txtopenNMSPort.setText(sopenNMSPort);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	btnSaveSettings_click();
                finish();
            }
        });
        btnClearSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	btnClearSettings_click();
            }
        });
 
    }
	public void btnClearSettings_click(){
	      SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      EditText txtopenNMSUserName = (EditText) findViewById(R.id.openNMSUserName);
	      EditText txtopenNMSServer = (EditText) findViewById(R.id.openNMSServer);
	      EditText txtopenNMSPort = (EditText) findViewById(R.id.openNMSPort);
          txtopenNMSUserName.setText("");
          txtopenNMSServer.setText("");
          txtopenNMSPort.setText("");
	      editor.putString("openNMSUserName", "");
	      editor.putString("openNMSServer", "");
	      editor.putString("openNMSPort", "");
	      editor.commit();
	}
	public void btnSaveSettings_click(){
	      SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      EditText txtopenNMSUserName = (EditText) findViewById(R.id.openNMSUserName);
	      EditText txtopenNMSServer = (EditText) findViewById(R.id.openNMSServer);
	      EditText txtopenNMSPort = (EditText) findViewById(R.id.openNMSPort);
	      String sServerUserName = txtopenNMSUserName.getText().toString();
	      String sServerURL = txtopenNMSServer.getText().toString();
	      String sServerPort = txtopenNMSPort.getText().toString();     
	      editor.putString("openNMSUserName", sServerUserName);
	      editor.putString("openNMSServer", sServerURL);
	      editor.putString("openNMSPort", sServerPort);
	      editor.commit();
	}

}

