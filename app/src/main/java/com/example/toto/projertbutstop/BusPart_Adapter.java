package com.example.toto.projertbutstop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ben Kung on 20-Aug-17.
 */

public class BusPart_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> busPast = new ArrayList<>();

    BusPart_Adapter(Context context, ArrayList<String> BusPast) {
        this.context = context;
        this.busPast = BusPast;

    }

    @Override
    public int getCount() {
        if (busPast.isEmpty()) {
            return 1;
        } else {
            return busPast.size();
        }

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.buspart_item, parent, false);
        TextView t = view.findViewById(R.id.item);
        if (busPast.isEmpty()) {
            t.setText("ใส่ป้ายรถประจำทางอีกครั้ง");
        } else {
            for (int a = 0; a < busPast.size(); a++) {
                t.setText("รถประจำทางสาย "+busPast.get(i));
            }
        }

        Log.d("20AugV5", "buspart   " + busPast);

        return view;
    }
}
