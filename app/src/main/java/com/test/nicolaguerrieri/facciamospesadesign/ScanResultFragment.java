package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.test.nicolaguerrieri.facciamospesadesign.model.Carta;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Utility;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String codice;
    private String mParam2;


    Utility utility = new Utility();
    SQLiteDatabase sampleDB = null;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanResultFragment newInstance(String param1, String param2) {
        ScanResultFragment fragment = new ScanResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ScanResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codice = getArguments().getString("codice");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scan_result, container, false);


        Bitmap bitmap = null;
        ImageView iw = (ImageView) view.findViewById(R.id.scanResult);
        TextView textCodice = (TextView) view.findViewById(R.id.textCodiceScan);
        final Spinner spinner = (Spinner) view.findViewById(R.id.nomeCarta);
        Button buttonSave = (Button) view.findViewById(R.id.salvaImage);

        try {
            if (codice != null) {
                bitmap = utility.encodeAsBitmap(codice, BarcodeFormat.CODE_128, 600, 300);
                textCodice.setText(codice);
                iw.setImageBitmap(bitmap);
                final Bitmap finalBitmap = bitmap;

                buttonSave.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Log.d("stampa spinner :", spinner.getSelectedItem().toString());
                        final String nomeCarta = spinner.getSelectedItem().toString();

                        if (nomeCarta.equals("")) {
                            Toast.makeText(getActivity(), "Inserire nome carta", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {

                            Carta carta = new Carta();
                            carta.setIcona(finalBitmap);
                            carta.setTitolo(nomeCarta);
                            carta.setLogo(nomeCarta);
                            carta.setCodice(codice);

                            boolean result = saveCarta(carta);
                            if (result) {
                                Toast.makeText(getActivity(), "Carta salvata correttamente", Toast.LENGTH_LONG).show();
                                spinner.setSelection(0, true);


                                goCarte();
                            } else {
                                Toast.makeText(getActivity(), "Errore salvataggio", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } else {
                throw new Exception("Nessun codice trovato");
            }
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void goCarte() {
        ((MainActivity) getActivity()).goToFragmentMenu(2);
    }

    public boolean saveCarta(Carta carta) {
        final String METHOD_NAME = ".saveCarta() ";

        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);
        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        if (sampleDB != null) {
//            sampleDB.execSQL(Costanti.QUERY_DROP);
            String queryCreate = Costanti.QUERY_CREATE;
            Log.d(METHOD_NAME, "queryCreate: " + queryCreate);
            //   sampleDB.execSQL(queryCreate);

            byte[] byteArray = utility.getBytes(carta.getIcona());
            Log.d(METHOD_NAME, "byteArray: " + byteArray.length);
            String queryInsert = "INSERT INTO " + Costanti.TABLE_NAME_CARTE + "(" + Costanti.COLUMN_NAME_NOME + "," + Costanti.COLUMN_NAME_IMMAGINE + "," + Costanti.COLUMN_NAME_LOGO + "," + Costanti.COLUMN_NAME_CODICE + ") values (?,?,?,?)";


            SQLiteStatement insertStmt = sampleDB.compileStatement(queryInsert);
            insertStmt.clearBindings();
            insertStmt.bindString(1, carta.getTitolo());
            insertStmt.bindBlob(2, byteArray);
            insertStmt.bindString(3, carta.getLogo());
            insertStmt.bindString(4, carta.getCodice());
            Long idCarta = insertStmt.executeInsert();
           // Log.d(METHOD_NAME, "queryInsert: " + queryInsert);


//            sampleDB.execSQL(queryInsert);
            return true;
        }
        return false;
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
        mListener = null;
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
