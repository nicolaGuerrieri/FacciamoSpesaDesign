package com.test.nicolaguerrieri.facciamospesadesign.utility;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.test.nicolaguerrieri.facciamospesadesign.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nicola.guerrieri2 on 16/09/2015.
 */
public class Utility {

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;

    public Bitmap saveBarcode(Bitmap bitmap, String nome) throws FileNotFoundException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/facciamoSpesa");
        myDir.mkdirs();
        File file = new File(myDir, nome.trim() + ".jpg");
        if (file.exists()) file.delete();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();

            Bitmap result = BitmapFactory.decodeFile(file.getAbsolutePath());
            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap encodeAsBitmap(String str, BarcodeFormat format, int width, int height) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    format, width, height, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public WindowManager.LayoutParams setLuminosita(WindowManager.LayoutParams lp, float luminosita) {
        float newBrightness = luminosita;
        lp.screenBrightness = newBrightness / (float) 255;

        return lp;
    }

    public void spiegaLista(final FragmentActivity activity) {
        spiegaUnPoDoppio("Inserisci i prodotti.\nClicca sul tasto \"Più\" oppure il tasto \"Microfono\" (perchè scrivere ?) per aggiungerli alla tua lista della spesa", "Tieni premuto sul prodotto per eliminarlo", "Condividi la lista della spesa con chi fa la spesa al posto tuo...", 2, R.layout.dialog_custom_spesa_long, "Ok", activity);
    }

    public void spiegaUnPoDoppio(String messaggio, String messaggio2, String messaggio3, int step, int resourseId, final String messaggioBottone, final FragmentActivity activity) {

        // apriamo dialog per spiegare
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(resourseId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView iw = (ImageView) dialog.findViewById(R.id.imageDialog);
        ImageView iw2 = (ImageView) dialog.findViewById(R.id.imageDialog2);
        ImageView iw3 = (ImageView) dialog.findViewById(R.id.imageDialog3);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView tw = (TextView) dialog.findViewById(R.id.textDialog);
        TextView tw2 = (TextView) dialog.findViewById(R.id.textDialog2);
        TextView tw3 = (TextView) dialog.findViewById(R.id.textDialog3);


        Drawable res = null;
        Integer imageResource = null;
        Integer imageResource2 = null;
        Integer imageResource3 = null;


        tw.setText(messaggio);
        tw2.setText(messaggio2);
        tw3.setText(messaggio3);
        // if button is clicked, close the custom dialog
        dialogButton.setText(messaggioBottone);

        switch (step) {
            case 2:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!messaggioBottone.equalsIgnoreCase("Ok")) {
                            spiegaUnPo("Hai bisogno di più liste della spesa separate?\nClicca su \"Liste della spesa\" e crea la tue...", 5, R.layout.dialog_custom, "Next", activity);
                        }
                    }
                });
                imageResource = activity.getResources().getIdentifier("@drawable/inserisci_prod", null, activity.getPackageName());
                imageResource2 = activity.getResources().getIdentifier("@drawable/elimina_elem", null, activity.getPackageName());
                imageResource3 = activity.getResources().getIdentifier("@drawable/barra", null, activity.getPackageName());
                break;
            case 3:
                //carte
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        spiegaUnPoDoppio("Oppure clicca sul pulsante \"Matita\" ed aggiungi il tuo negozio", "Clicca sul tasto ''Più'' per aggiungere la carta", "O tieni premuto sulla carta che vuoi eliminare", 4, R.layout.dialog_custom_spesa_long_carte, "Next", activity);
                    }
                });
                imageResource = activity.getResources().getIdentifier("@drawable/scan", null, activity.getPackageName());
                imageResource2 = activity.getResources().getIdentifier("@drawable/carta", null, activity.getPackageName());
                imageResource3 = activity.getResources().getIdentifier("@drawable/selez_nome", null, activity.getPackageName());
                break;
            case 4:
                //carte
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        spiegaUnPo("Utilizza il comodissimo widget per avere sempre la tua lista della spesa sott'occhio", 6, R.layout.dialog_custom_widget, "Ok", activity);
                    }
                });
                imageResource = activity.getResources().getIdentifier("@drawable/inserisci_nome_carta", null, activity.getPackageName());
                imageResource2 = activity.getResources().getIdentifier("@drawable/aggiungi_carta", null, activity.getPackageName());
                imageResource3 = activity.getResources().getIdentifier("@drawable/elimina_carta", null, activity.getPackageName());
                break;

            default:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        if (imageResource != null) {
            res = activity.getResources().getDrawable(imageResource);
            iw.setImageDrawable(res);
        }
        if (imageResource2 != null) {
            res = activity.getResources().getDrawable(imageResource2);
            iw2.setImageDrawable(res);
        }
        if (imageResource3 != null) {
            res = activity.getResources().getDrawable(imageResource3);
            iw3.setImageDrawable(res);
        }
        dialog.show();

    }

    public void spiegaUnPo(String messaggio, int step, int resourseId, String messaggioBottone, final FragmentActivity activity) {

        // apriamo dialog per spiegare
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(resourseId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView iw = (ImageView) dialog.findViewById(R.id.imageDialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView tw = (TextView) dialog.findViewById(R.id.textDialog);


        Drawable res = null;
        Integer imageResource = null;
        Integer imageResource2 = null;


        tw.setText(messaggio);
        // if button is clicked, close the custom dialog
        dialogButton.setText(messaggioBottone);

        switch (step) {
            case 1:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        spiegaUnPoDoppio("Inserisci i prodotti.\nClicca sul tasto \"Più\" oppure il tasto \"Microfono\" (perchè scrivere ?) per aggiungerli alla tua lista della spesa", "Tieni premuto sul prodotto per eliminarlo", "Condividi la lista della spesa con chi fa la spesa al posto tuo...", 2, R.layout.dialog_custom_spesa_long, "Next", activity);
                    }
                });
                // imageResource = activity.getResources().getIdentifier("@drawable/frecciatrasparente", null, activity.getPackageName());
                break;
            case 5:
                // usato liste
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spiegaUnPoDoppio("Aggiungi le tue fidelity card, clicca su scan", "Inquadra il tuo codice a barre e attendi la scansione", "Seleziona il negozio a cui appartiene la fidelity card", 3, R.layout.dialog_custom_spesa_long_carte, "Next", activity);
                        dialog.dismiss();
                    }
                });
                imageResource = activity.getResources().getIdentifier("@drawable/liste", null, activity.getPackageName());
                break;
            case 6:
                //widget
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                imageResource = activity.getResources().getIdentifier("@drawable/widget", null, activity.getPackageName());
                break;
            default:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        if (imageResource != null) {
            res = activity.getResources().getDrawable(imageResource);
            iw.setImageDrawable(res);
        }
        if (imageResource2 != null) {
            res = activity.getResources().getDrawable(imageResource2);
            iw.setImageDrawable(res);
        }
        dialog.show();

    }

}
