package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.nicolaguerrieri.facciamospesadesign.adapter.ListeAdapter;
import com.test.nicolaguerrieri.facciamospesadesign.model.Lista;
import com.test.nicolaguerrieri.facciamospesadesign.model.ViewHolder;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListeSpesaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeSpesaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeSpesaFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    SQLiteDatabase sampleDB = null;
    ListView listView = null;
    List<Lista> results = new ArrayList<Lista>();
    ListeAdapter adapter = null;

    public static ListeSpesaFragment newInstance(String param1, String param2) {
        ListeSpesaFragment fragment = new ListeSpesaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ListeSpesaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String METHOD_NAME = ".onCreateView() >>>> ";
        View view = inflater.inflate(R.layout.fragment_liste_spesa, container, false);
        Button buttonCrea = (Button) view.findViewById(R.id.creaLista);
        final EditText nomeLista = (EditText) view.findViewById(R.id.nuovaLista);

        listView = (ListView) view.findViewById(R.id.listeSpesa);

        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);

        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        Cursor risultato = null;

        // sampleDB.execSQL(Costanti.QUERY_DROP_TABLE_NAME_LISTA);
        if (sampleDB != null) {
            Cursor tableExist = sampleDB.rawQuery("SELECT * from sqlite_master WHERE name ='" + Costanti.TABLE_NAME_LISTA + "' and type='table'", null);
            Log.d(METHOD_NAME, "tableExist: " + tableExist);
            if (tableExist != null) {
                sampleDB.execSQL(Costanti.QUERY_CREATE_LISTA);

                risultato = sampleDB.rawQuery("SELECT * FROM " + Costanti.TABLE_NAME_LISTA, null);
                Log.d(METHOD_NAME, "risultato: " + risultato);
                if (risultato.getCount() > 0) {
                    Log.d(METHOD_NAME, "risultato presente ");

                    if (risultato.moveToNext()) {
                        do {
                            Lista lista = new Lista();
                            Integer idLista = risultato.getInt(risultato.getColumnIndex(Costanti.COLUMN_NAME_ID));

                            String nomeListaR = risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_NOME_LISTA));
                            lista.setId(idLista);
                            lista.setNomeLista(nomeListaR);
                            results.add(lista);
                        } while (risultato.moveToNext()); //Move to next row
                    }
                }

            }
        }

        TextView tw = (TextView) view.findViewById(R.id.myListe);
        if (results.size() != 0) {
            tw.setVisibility(View.VISIBLE);
        }

        buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (org.apache.commons.lang3.StringUtils.isBlank(nomeLista.getText().toString())) {
                    Toast.makeText(getActivity(), "Inserire nome o data lista", Toast.LENGTH_LONG).show();
                    return;
                }
                Bundle args = new Bundle();
                args.putString("nomeLista", nomeLista.getText().toString());
                args.putBoolean("crea", true);
                // nomeLista.setText("");
                ((MainActivity) getActivity()).goToFragmentMenu(0, args);
            }
        });

        adapter = new ListeAdapter(getActivity(), results);
        if (listView != null) {
            listView.setAdapter(adapter);
            Log.d(METHOD_NAME, "aggiunto adapter");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder lista = (ViewHolder) view.getTag();
                Log.d("", "" + lista.getIdCarta());

                Bundle args = new Bundle();
                args.putString("nomeLista", lista.getTextView().getText().toString());
                args.putLong("idLista", lista.getIdCarta());
                args.putBoolean("crea", true);
                // nomeLista.setText("");
                ((MainActivity) getActivity()).goToFragmentMenu(0, args);
                //uso id carta per pigrizia
              /**  Toast.makeText(getActivity(), "" + lista.getIdCarta(), Toast.LENGTH_LONG).show();
                listView.setItemChecked(position, false);**/
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                view.setSelected(true);
                return true;
            }
        });


        return view;
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
