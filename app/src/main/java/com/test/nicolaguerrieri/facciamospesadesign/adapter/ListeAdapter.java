package com.test.nicolaguerrieri.facciamospesadesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.nicolaguerrieri.facciamospesadesign.R;
import com.test.nicolaguerrieri.facciamospesadesign.model.Lista;
import com.test.nicolaguerrieri.facciamospesadesign.model.ViewHolder;

import java.util.List;

/**
 * Created by nicola.guerrieri2 on 15/09/2015.
 */
public class ListeAdapter extends BaseAdapter {

    private List<Lista> listaListe;
    private Context context;

    public ListeAdapter(Context context, List<Lista> listaListe) {
        this.context = context;
        this.listaListe = listaListe;
    }

    @Override
    public int getCount() {
        return listaListe.size();
    }

    @Override
    public Object getItem(int position) {
        return listaListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
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
            convertView = inflater.inflate(R.layout.fragment_item_list, null);

            //creo un holder per settare dentro i componenti, al primo giro quando è null il convert view
            holderTag = new ViewHolder();
            holderTag.setTextView((TextView) convertView.findViewById(R.id.nomeLista));
            convertView.setTag(holderTag);
        } else {
            holderTag = (ViewHolder) convertView.getTag();
        }

        Lista lista = listaListe.get(position);

        holderTag.getTextView().setText(lista.getNomeLista());

        holderTag.setIdCarta(lista.getId());

        return convertView;
    }
}

