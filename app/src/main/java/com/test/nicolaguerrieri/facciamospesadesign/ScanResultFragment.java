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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.test.nicolaguerrieri.facciamospesadesign.model.Carta;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;
import com.test.nicolaguerrieri.facciamospesadesign.utility.JSONParser;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Utility;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public List<String> negoziResult = new ArrayList<String>();

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


    private void fetch() {

        JsonObjectRequest request = new JsonObjectRequest("http://negozi-negozi.rhcloud.com/", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray negozi = response.getJSONArray("negozi");
                            for (int i = 0; i < negozi.length(); i++) {
                                negoziResult.add(negozi.get(i).toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("", response.toString());
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("", error.toString());
                    }
                }
        );
        App.getInstance().getRequestQueue().add(request);
    }

    public void sparaNuovoNegozio(String negozio) {
        boolean sparare = true;
        for (String neg : negoziResult) {
            if (neg.equalsIgnoreCase(negozio)) {
                sparare = false;
            }
        }
        if (sparare) {
            JSONParser dd = new JSONParser() {
                @Override
                protected void onPostExecute(List<String> result) {

                    ((MainActivity) getActivity()).setListaNegozi(result);
                }
            };
            dd.execute("http://negozi-negozi.rhcloud.com/?negozio=" + negozio);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scan_result, container, false);
        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);


        Bitmap bitmap = null;
        ImageView iw = (ImageView) view.findViewById(R.id.scanResult);
        TextView textCodice = (TextView) view.findViewById(R.id.textCodiceScan);
        final Spinner spinner = (Spinner) view.findViewById(R.id.nomeCarta);
        final Button buttonSave = (Button) view.findViewById(R.id.salvaImage);
        final Button buttonModify = (Button) view.findViewById(R.id.inserisciNome);
        final EditText inserisciNome = (EditText) view.findViewById(R.id.nomeCartaIns);

        buttonSave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == buttonSave) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setAlpha(.5f);
                    } else {
                        v.setAlpha(1f);
                    }
                    return false;
                }

                return false;
            }
        });
        buttonModify.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == buttonSave) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setAlpha(.5f);
                    } else {
                        v.setAlpha(1f);
                    }
                    return false;
                }

                return false;
            }
        });

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inserisciNome.getVisibility() == View.VISIBLE) {
                    inserisciNome.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                } else {
                    inserisciNome.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                }
            }
        });


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        negoziResult = ((MainActivity) getActivity()).getListaNegozi();
        if (negoziResult.size() == 0) {
            // se qualcosa è andato storto
            int holderint = getResources().getIdentifier("negozi", "array",
                    getActivity().getPackageName()); // You had used "name"
            String[] mess = getResources().getStringArray(holderint);
            ((MainActivity) getActivity()).setListaNegozi(new ArrayList<String>(Arrays.asList(mess)));
        }
        //tiriamo su da chiamata json
        adapter.addAll(negoziResult);
        adapter.add("Seleziona un negozio");

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        try {
            if (codice != null) {
                bitmap = utility.encodeAsBitmap(codice, BarcodeFormat.CODE_128, 600, 300);
                textCodice.setText(codice);
                iw.setImageBitmap(bitmap);
                final Bitmap finalBitmap = bitmap;

                buttonSave.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        final String nomeCarta;

                        if (inserisciNome.getVisibility() == View.VISIBLE) {
                            nomeCarta = inserisciNome.getText().toString();
                        } else {
                            nomeCarta = spinner.getSelectedItem().toString();
                        }
                        if (StringUtils.isBlank(nomeCarta)) {
                            Toast.makeText(getActivity(), "Inserire nome carta", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (nomeCarta.equalsIgnoreCase("Seleziona un negozio")) {
                            Toast.makeText(getActivity(), "Selezionare nome carta", Toast.LENGTH_LONG).show();
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
                                spinner.setSelection(adapter.getCount());
                                inserisciNome.setText("");
                                sparaNuovoNegozio(nomeCarta);
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
