package com.devandjav.luisalberto.stimote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;

/**
 * Created by Luis alberto on 3/09/2016.
 */
public class BeaconAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Beacon> beaconDTOs;

    public BeaconAdapter(Context context, ArrayList<Beacon> beaconDTOs) {
        this.context = context;
        this.beaconDTOs = beaconDTOs;
    }

    @Override
    public int getCount() {
        return beaconDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return beaconDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderBeaconsView holderBeaconsView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_beacon_view, null);

            holderBeaconsView = new HolderBeaconsView();
            holderBeaconsView.id_beacon = (TextView) convertView.findViewById(R.id.id_beacon);

            convertView.setTag(holderBeaconsView);
        } else {
            holderBeaconsView = (HolderBeaconsView) convertView.getTag();
        }

        holderBeaconsView.id_beacon.setText(beaconDTOs.get(position).getBluetoothName());

        return convertView;
    }

    private static class HolderBeaconsView {
        TextView id_beacon;
    }
}
