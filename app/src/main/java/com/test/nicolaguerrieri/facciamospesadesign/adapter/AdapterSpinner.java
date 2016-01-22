package com.test.nicolaguerrieri.facciamospesadesign.adapter;
/**
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.test.nicolaguerrieri.facciamospesadesign.R;

import java.util.List;

/**
 * Created by nicola.guerrieri2 on 02/10/2015.
 */
/**public class AdapterSpinner    extends ArrayAdapter<String> {

    public AdapterSpinner(Context theContext, List<String> objects) {
        super(theContext, R.id.text1, objects);
    }

    public AdapterSpinner(Context theContext, List<Object> objects, int theLayoutResId) {
        super(theContext, theLayoutResId, R.id.text1, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}**/