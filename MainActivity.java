package com.devandjav.luisalberto.stimote;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {
    private BeaconManager mBeaconManager;
    private static Context context;
    private static Identifier namespaceId  ;
    private static Identifier instanceId  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        mBeaconManager.bind(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        for (Beacon beacon : collection) {
            instanceId = beacon.getId2();
            namespaceId = beacon.getId1();


            if(instanceId.equals("")){
                Log.e("RangingActivity", "I see a beacon transmitting \nnamespace id: " + namespaceId +
                        " and \ninstance id: " + instanceId +
                        " and \nBluetooth Address id: " + beacon.getBluetoothAddress() +
                        " and \nBluetooth Name id: " + beacon.getBluetoothName() +
                        " and \nRssi : " + beacon.getRssi() +
                        " and \nTxPower : " + beacon.getTxPower() +
                        " and \nServiceUuid : " + beacon.getServiceUuid() +
                        " and \nBeaconTypeCode : " + beacon.getBeaconTypeCode() +
                        " and \nManufacturer : " + beacon.getManufacturer() +
                        " \napproximately " + beacon.getDistance() + " meters away.");
            }
            /*

            if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00) {
                // This is a Eddystone-UID frame
                //Identifier namespaceId = beacon.getId1();
                //Identifier instanceId = beacon.getId2();
                  namespaceId = beacon.getId1();
                  instanceId = beacon.getId2();
                Log.e("RangingActivity", "I see a beacon transmitting \nnamespace id: " + namespaceId +
                        " and \ninstance id: " + instanceId +
                        " and \nBluetooth Address id: " + beacon.getBluetoothAddress() +
                        " and \nBluetooth Name id: " + beacon.getBluetoothName() +
                        " and \nRssi : " + beacon.getRssi() +
                        " and \nTxPower : " + beacon.getTxPower() +
                        " and \nServiceUuid : " + beacon.getServiceUuid() +
                        " and \nBeaconTypeCode : " + beacon.getBeaconTypeCode() +
                        " and \nManufacturer : " + beacon.getManufacturer() +
                        " \napproximately " + beacon.getDistance() + " meters away.");

                runOnUiThread(new Runnable() {
                    public void run() {
                       // ((TextView) ((MainActivity) context).findViewById(R.id.message)).setText("Hello world, and welcome to Eddystone!");
                        ((TextView) ((MainActivity) context).findViewById(R.id.message)).setText(instanceId+"");
                    }
                });
            }

            */
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }
}
