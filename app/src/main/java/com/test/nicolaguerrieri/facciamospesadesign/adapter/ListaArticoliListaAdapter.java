package com.test.nicolaguerrieri.facciamospesadesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.nicolaguerrieri.facciamospesadesign.R;
import com.test.nicolaguerrieri.facciamospesadesign.model.ArticoloCustom;
import com.test.nicolaguerrieri.facciamospesadesign.model.ArticoloCustomHolder;

import java.util.List;

/**
 * Created by nicola.guerrieri2 on 15/09/2015.
 */
public class ListaArticoliListaAdapter extends BaseAdapter {

    private List<ArticoloCustom> listaArticoli;
    private Context context;

    public ListaArticoliListaAdapter(Context context, List<ArticoloCustom> listaArticoli) {
        this.context = context;
        this.listaArticoli = listaArticoli;
    }

    @Override
    public int getCount() {
        return listaArticoli.size();
    }

    @Override
    public Object getItem(int position) {
        return listaArticoli.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void add(ArticoloCustom art) {
        listaArticoli.add(art);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //indica una row
        View gridView;

        ArticoloCustomHolder holderTag = null;
        //se la view che mi stai passando è null io la creo altrimenti riutilizzo
        //Il primo quando il convertView è uguale a null, quindi ci troviamo al primo elemento della lista
        if (convertView == null) {

            convertView = new View(context);

            // attraverso l'inflater io ti dico, con un xml, come deve essere sta view
            convertView = inflater.inflate(R.layout.item_lista_articolo, null);

            //creo un holder per settare dentro i componenti, al primo giro quando è null il convert view
            holderTag = new ArticoloCustomHolder();
            holderTag.setArticolo((TextView) convertView.findViewById(R.id.articolo));
            holderTag.setQuantita((TextView) convertView.findViewById(R.id.numeroPezzi));

            convertView.setTag(holderTag);
        } else {
            holderTag = (ArticoloCustomHolder) convertView.getTag();
        }

        ArticoloCustom articoloLista = listaArticoli.get(position);
        holderTag.getArticolo().setText(articoloLista.getArticolo());
        holderTag.getQuantita().setText("" + articoloLista.getQuantita());

        holderTag.setIdListaArticolo(articoloLista.getIdListaArticolo());

        return convertView;
    }
}

