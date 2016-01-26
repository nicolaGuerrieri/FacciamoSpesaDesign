package com.test.nicolaguerrieri.facciamospesadesign.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.nicolaguerrieri.facciamospesadesign.R;
import com.test.nicolaguerrieri.facciamospesadesign.model.Carta;
import com.test.nicolaguerrieri.facciamospesadesign.model.ViewHolder;

import java.util.List;

/**
 * Created by nicola.guerrieri2 on 02/10/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<Carta> listaCarte;

    public ImageAdapter(Context context, List<Carta> listaCarte) {
        this.context = context;
        this.listaCarte = listaCarte;
    }

    //restituisce la view che rappresenta la riga della listview
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //indica una row
        View gridView;

        ViewHolder holderTag = null;
        //se la view che mi stai passando è null io la creo altrimenti riutilizzo
        //Il primo quando il convertView è uguale a null, quindi ci troviamo al primo elemento della lista
        if (convertView == null) {

            convertView = new View(context);

            // attraverso l'inflater io ti dico, con un xml, come deve essere sta view
            convertView = inflater.inflate(R.layout.item_image_grid, null);

            // set image based on selected text
         /*   ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.grid_item_image);
            TextView tv = (TextView) convertView.findViewById(R.id.textImage);*/

            //creo un holder per settare dentro i componenti, al primo giro quando è null il convert view
            holderTag = new ViewHolder();
            holderTag.setImageView((ImageView) convertView.findViewById(R.id.grid_item_image));
            holderTag.setTextView((TextView) convertView.findViewById(R.id.textImage));
            convertView.setTag(holderTag);
        } else {
            holderTag = (ViewHolder) convertView.getTag();
        }

        Carta carta = listaCarte.get(position);
        String uri = "@drawable/" + carta.getLogo().toLowerCase().trim().replace(" ", "");
        Log.d("uri cercato >>>>> ", uri);
        Integer imageResource = null;
        Drawable res = null;
        try {
            imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            res = context.getResources().getDrawable(imageResource);
        } catch (Exception e) {
            //in caso di errore prendo immagine generica
            imageResource = context.getResources().getIdentifier("@drawable/genericcard", null, context.getPackageName());
            res = context.getResources().getDrawable(imageResource);
        }

        holderTag.getImageView().setImageDrawable(res);
        holderTag.getImageView().setScaleType(ImageView.ScaleType.FIT_CENTER);

        holderTag.getTextView().setText(carta.getTitolo());

        holderTag.setIdCarta(carta.getId());
        return convertView;
    }

    //restituisce numero di elementi in lista
    @Override
    public int getCount() {
        return listaCarte.size();
    }

    //restituisce elemento in posizione position
    @Override
    public Object getItem(int position) {
        return listaCarte.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}