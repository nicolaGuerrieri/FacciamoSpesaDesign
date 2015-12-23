package com.test.nicolaguerrieri.facciamospesadesign.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nicola.guerrieri2 on 02/12/2015.
 */
public class ViewHolder {
    private ImageView imageView;
    private TextView textView;

    private int idCarta;


    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
