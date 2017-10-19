package com.jose.enviardatoswifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class Principal extends AppCompatActivity {


    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;

    IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        mManager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this,getMainLooper(),null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(Principal.this, "Discovery Initialited", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(Principal.this, "Discovery Failed"+i, Toast.LENGTH_SHORT).show();
            }
        });

        ChecarWifiEnable();
        ChecarCarpeta();


    }

    private void ChecarCarpeta() {
        File f = new File(Environment.getExternalStorageDirectory()+"/Wifi-DirecFile/");
        if (!f.isDirectory()){
            String newFolder = "/Wifi-DirecFile/";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File myNewFolder = new File(extStorageDirectory+newFolder);
            myNewFolder.mkdir();
        }else {
            Log.d("TAG","La carpeta ya esta creada ");
        }
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void ChecarWifiEnable(){

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            Log.d("TAG","WIFI ACTIVADO");
            Toast.makeText(this, "activado", Toast.LENGTH_SHORT).show();
        }else {

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Es Necesario activar la conexion wifi" );
            dlgAlert.setTitle("Wifi Desativado :(");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  Intent intent= new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            });
          //  dlgAlert.setCancelable(true);
            dlgAlert.create().show();
         }
  }

  public void Archivos(View view){
      Intent intent = new Intent(this,Archivos.class);
      startActivity(intent);
  }


}
