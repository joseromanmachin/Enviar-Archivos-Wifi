package com.jose.enviardatoswifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

/**
 * Created by jose on 18/10/17.
 */

class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    public WiFiDirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, Principal principal) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "activado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "desacctivado", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
