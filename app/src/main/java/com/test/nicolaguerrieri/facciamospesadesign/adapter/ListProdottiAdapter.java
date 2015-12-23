package com.test.nicolaguerrieri.facciamospesadesign.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.util.HashMap;
import java.util.List;

/**
 * Created by nicola.guerrieri2 on 15/09/2015.
 */
public class ListProdottiAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public ListProdottiAdapter(Context context, int textViewResourceId,
                               List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public boolean hasStableIds() {
        return true;
    }

}

