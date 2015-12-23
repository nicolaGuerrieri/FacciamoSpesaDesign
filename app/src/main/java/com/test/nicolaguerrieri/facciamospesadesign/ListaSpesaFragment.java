package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.nicolaguerrieri.facciamospesadesign.adapter.ListProdottiAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaSpesaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaSpesaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaSpesaFragment extends Fragment {


    SQLiteDatabase sampleDB = null;
    private final String dbName = "facciamoSpesa";
    private final String tableName = "Prodotti";
    private final String columnName = "Prodotto";


    ListView listView = null;
    List<String> results = new ArrayList<String>();

    ListProdottiAdapter adapter = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaSpesaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaSpesaFragment newInstance(String param1, String param2) {
        ListaSpesaFragment fragment = new ListaSpesaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListaSpesaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        results = new ArrayList<String>();
        View vistaReturn = inflater.inflate(R.layout.fragment_lista_spesa, container, false);
        final String METHOD_NAME = ".onCreate() >>>> ";
        Log.d(METHOD_NAME, "start");

        listView = (ListView) vistaReturn.findViewById(R.id.listaSpesa);

        //mette tutto in basso
        /**      listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
         listView.setStackFromBottom(true);**/

        sampleDB = getActivity().openOrCreateDatabase(dbName, getActivity().MODE_PRIVATE, null);
        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        if (sampleDB != null) {
            Cursor tableExist = sampleDB.rawQuery("SELECT * from sqlite_master WHERE name ='" + tableName + "' and type='table'", null);
            Log.d(METHOD_NAME, "tableExist: " + tableExist);
            if (tableExist != null) {
                sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnName + " VARCHAR);");


                Cursor risultato = sampleDB.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
                Log.d(METHOD_NAME, "risultato: " + risultato);
                if (risultato.getCount() > 0) {
                    Log.d(METHOD_NAME, "risultato presente ");

                    if (risultato.moveToNext()) {
                        do {
                            String prodotto = risultato.getString(risultato.getColumnIndex(columnName));
                            Log.d(METHOD_NAME, "prodotto: " + prodotto);
                            results.add(prodotto);
                        } while (risultato.moveToNext()); //Move to next row
                    }
                }
            }

            //
        }
        adapter = new ListProdottiAdapter(getActivity(), R.layout.item_custom, results);
        if (listView != null) {
            listView.setAdapter(adapter);
            Log.d(METHOD_NAME, "aggiunto adapter");
        }

        Button button = (Button) vistaReturn.findViewById(R.id.aggiungi);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addProduct(v);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);
                Log.d(METHOD_NAME, "item: " + item);
                sampleDB.execSQL("DELETE FROM " + tableName + " WHERE " + columnName + "='" + item + "'");
                Log.d(METHOD_NAME, "eliminato: " + item);
                results.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), item + " eliminato...", Toast.LENGTH_LONG).show();
                return false;
            }
        });


        return vistaReturn;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_spesa, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void addProduct(View view) {
        final String METHOD_NAME = ".addProduct() >>>> ";
        Log.d(METHOD_NAME, "start");
        EditText nuovoProdotto = (EditText) getActivity().findViewById(R.id.nuovoProdotto);
        Log.d(METHOD_NAME, "nuovoProdotto: " + nuovoProdotto.getText());
        String prodottoAggiunto = nuovoProdotto.getText().toString();
        if (prodottoAggiunto.equals("")) {
            Toast.makeText(getActivity(), "Inserire un prodotto", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            sampleDB.execSQL("INSERT INTO " + tableName + "(" + columnName + ") values ('" + nuovoProdotto.getText() + "');");
            Toast.makeText(getActivity(), nuovoProdotto.getText() + " aggiunta", Toast.LENGTH_LONG).show();
            nuovoProdotto.setText("");
            Log.d(METHOD_NAME, "aggiunto prodotto");
            adapter.add(prodottoAggiunto);
        } catch (SQLException sqE) {
            Log.e(METHOD_NAME, "sqE: " + sqE);
            Toast.makeText(getActivity(), "Salvataggio non eseguito", Toast.LENGTH_LONG).show();
        }

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


    public List<String> getAllProducts() {
        final String METHOD_NAME = ".getAllProducts() >>>> ";
        List<String> listaQueryProdotti = new ArrayList<String>();
        Cursor risultato = sampleDB.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        Log.d(METHOD_NAME, "risultato: " + risultato);
        if (risultato != null) {
            Log.d(METHOD_NAME, "risultato presente ");
            if (risultato.moveToFirst()) {
                do {
                    String prodotto = risultato.getString(risultato.getColumnIndex(columnName));
                    Log.d(METHOD_NAME, "prodotto: " + prodotto);
                    listaQueryProdotti.add(prodotto);
                } while (risultato.moveToNext()); //Move to next row
            }
        }
        return listaQueryProdotti;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //condividi su whatsapp
        if (id == R.id.action_share) {

            shareOnMsgApp("");
            return true;
        } else if (id == R.id.action_home) {
            Intent startMain = new Intent(getActivity(), MainActivity.class);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return true;
        } else if (id == R.id.action_info) {

// apriamo dialog per spiegare
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_custom);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


            TextView tw = (TextView) dialog.findViewById(R.id.textDialog);

            tw.setText("Inserisci i prodotti per la tua lista della spesa e clicca il tasto più per aggiungerli");
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setText("Next");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    apriAltraDialog("Tieni premuto sul prodotto per eliminarlo", 1);
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void apriAltraDialog(String testoPass, int step) {
// apriamo dialog per spiegare
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_spesa);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView tw = (TextView) dialog.findViewById(R.id.textDialog);
        tw.setText(testoPass);

        ImageView iw = (ImageView) dialog.findViewById(R.id.imageDialog);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Integer imageResource = null;
        Drawable res = null;


        if (step == 1) {
            dialogButton.setText("Next");

            imageResource = getActivity().getResources().getIdentifier("@drawable/testmio", null, getActivity().getPackageName());
            res = getActivity().getResources().getDrawable(imageResource);


            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    apriDialogFinale("Condividi la tua lista della spesa con chi fa la spesa al posto tuo...");
                }
            });
        } else {

            //NON USATO
            dialogButton.setText("Fine");

            imageResource = getActivity().getResources().getIdentifier("@drawable/barrashare", null, getActivity().getPackageName());
            res = getActivity().getResources().getDrawable(imageResource);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }
        iw.setImageDrawable(res);
        dialog.show();

    }

    public void apriDialogFinale(String testoPass) {
// apriamo dialog per spiegare
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_spesa_share);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView tw = (TextView) dialog.findViewById(R.id.textDialog);

        tw.setText(testoPass);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("Fine");

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void shareOnMsgApp(String text) {
        final String METHOD_NAME = ".shareOnWA() >>>> ";
        Log.d(METHOD_NAME, "start");
        boolean prodottoPresente = false;
        String textToSend = "Lista della spesa: \n";
        for (String prodotto : getAllProducts()) {
            textToSend += "- " + prodotto + "\n";
            prodottoPresente = true;
        }

        Log.d(METHOD_NAME, textToSend);
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");

        if (prodottoPresente) {

            //CONDIVIDERE SOLO SU WHATSAPP
//            PackageManager pm = getPackageManager();
//            try {
//                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            //Check if package exists or not. If not then code
//            //in catch block will be called
//            waIntent.setPackage("com.whatsapp");
//
//            waIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
//            startActivity(Intent.createChooser(waIntent, "Share with"));

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, textToSend);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Lista vuota", Toast.LENGTH_LONG).show();
        }
    }
}
