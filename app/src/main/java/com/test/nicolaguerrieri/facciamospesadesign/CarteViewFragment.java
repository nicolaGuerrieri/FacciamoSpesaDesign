package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.nicolaguerrieri.facciamospesadesign.model.Carta;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarteViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarteViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarteViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int idCarta;

    //luminosita
    public float vecchiaLuminosita = 0F;
    WindowManager.LayoutParams lp = null;


    private OnFragmentInteractionListener mListener;

    SQLiteDatabase sampleDB = null;

    // TODO: Rename and change types and number of parameters
    public static CarteViewFragment newInstance(String param1, String param2) {
        CarteViewFragment fragment = new CarteViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CarteViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCarta = getArguments().getInt("carta");
        }
        //parametri display TODO
        lp = getActivity().getWindow().getAttributes();
        vecchiaLuminosita = lp.screenBrightness;
        float newBrightness = (float) 100;
        lp.screenBrightness = newBrightness / (float) 255;

        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carte_view, container, false);


        ImageView iw = (ImageView) view.findViewById(R.id.viewCarta);
        ImageView iwNegozio = (ImageView) view.findViewById(R.id.negozio);
        TextView textCodice = (TextView) view.findViewById(R.id.textCodice);
        TextView textNomeNegozio = (TextView) view.findViewById(R.id.nomeNegozio);


        Carta carta = findCarta(idCarta);
        textNomeNegozio.setText(carta.getTitolo());

        //devo recuperare il codice e fare la query non il byte array
        iw.setImageBitmap(carta.getIcona());
        textCodice.setText(carta.getCodice());
        textCodice.setTypeface(null, Typeface.BOLD);

        String uri = "@drawable/" + carta.getLogo().toLowerCase().trim().replace(" ", "");
        Log.d("", uri);
        Integer imageResource = null;
        Drawable res = null;
        try {
            imageResource = getActivity().getResources().getIdentifier(uri, null, getActivity().getPackageName());
            res = getActivity().getResources().getDrawable(imageResource);
        } catch (Exception e) {
            //in caso di errore prendo immagine generica
            imageResource = getActivity().getResources().getIdentifier("@drawable/genericcard22", null, getActivity().getPackageName());
            res = getActivity().getResources().getDrawable(imageResource);
        }

        iwNegozio.setImageDrawable(res);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        //parametri display TODO
        lp.screenBrightness = vecchiaLuminosita / (float) 255;
        getActivity().getWindow().setAttributes(lp);
        mListener = null;
    }


    public Carta findCarta(Integer idCarta) {

        final String METHOD_NAME = ".modificaComponente() ";
        Log.d(METHOD_NAME, "invocata onActivityResult");
        Carta carta = null;

        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);
        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        if (sampleDB != null) {
//            sampleDB.execSQL(Costanti.QUERY_DROP);
            String queryCreate = Costanti.QUERY_CREATE;
            Log.d(METHOD_NAME, "queryCreate: " + queryCreate);


            Cursor risultato = sampleDB.query(Costanti.TABLE_NAME_CARTE, new String[]{
                            Costanti.COLUMN_NAME_ID, Costanti.COLUMN_NAME_NOME, Costanti.COLUMN_NAME_IMMAGINE, Costanti.COLUMN_NAME_LOGO, Costanti.COLUMN_NAME_CODICE},
                    Costanti.COLUMN_NAME_ID + "=?", new String[]{String.valueOf(idCarta)},
                    null, null, null, null);
            Log.d(METHOD_NAME, "risultato.size: " + risultato.getCount());

            if (risultato != null) {
                Log.d(METHOD_NAME, "risultato presente ");
                if (risultato.moveToFirst()) {
                    do {
                        carta = new Carta();
                        carta.setId(risultato.getColumnIndex(Costanti.COLUMN_NAME_ID));
                        carta.setTitolo(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_NOME)));
                        carta.setLogo(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_LOGO)));
                        carta.setCodice(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_CODICE)));
                        byte[] image = risultato.getBlob(risultato.getColumnIndex(Costanti.COLUMN_NAME_IMMAGINE));
                        Log.d(METHOD_NAME, "immagine: " + image.length);
                        if (image != null) {
                            Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);
                            carta.setIcona(b);
                        }

                    } while (risultato.moveToNext()); //Move to next row
                }
            }

        }
        return carta;

    }


    private void setBrightness(int brightness) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
