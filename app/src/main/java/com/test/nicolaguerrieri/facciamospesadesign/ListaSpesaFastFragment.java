package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.nicolaguerrieri.facciamospesadesign.adapter.ListProdottiAdapter;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaSpesaFastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaSpesaFastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaSpesaFastFragment extends Fragment {


    SQLiteDatabase sampleDB = null;


    private boolean fromWizard = false;

    ListView listView = null;
    List<String> results = new ArrayList<String>();

    ListProdottiAdapter adapter = null;
    EditText quantita = null;
    AutoCompleteTextView nuovoProdotto = null;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String nomeLista = null;
    private boolean crea = false;

    private Long idLista = null;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaSpesaFastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaSpesaFastFragment newInstance(String param1, String param2) {
        ListaSpesaFastFragment fragment = new ListaSpesaFastFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ListaSpesaFastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        idLista = null;
        if (getArguments() != null) {
            nomeLista = getArguments().getString("nomeLista", null);
            idLista = getArguments().getLong("idLista", -1);
            crea = getArguments().getBoolean("crea", false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vistaReturn = inflater.inflate(R.layout.fragment_lista_spesa, container, false);

        Button button = (Button) vistaReturn.findViewById(R.id.aggiungi);
        final String METHOD_NAME = ".onCreate() >>>> ";
        Log.d(METHOD_NAME, "start");

        results = new ArrayList<String>();
        quantita = (EditText) vistaReturn.findViewById(R.id.quantita);
        listView = (ListView) vistaReturn.findViewById(R.id.listaSpesa);
        nuovoProdotto = (AutoCompleteTextView) vistaReturn.findViewById(R.id.nuovoProdotto);
        nuovoProdotto.setThreshold(1);
        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);


        //mette tutto in basso
        /**      listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
         listView.setStackFromBottom(true);**/

        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        Cursor risultato = null;
        if (crea) {
            Log.d("nome lista " + nomeLista, "");
            quantita.setText("1");

            // DROPPA TUTTO
            /**sampleDB.execSQL(Costanti.QUERY_DROP_TABLE_NAME_ARTICOLO);
             sampleDB.execSQL(Costanti.QUERY_DROP_TABLE_NAME_LISTA);
             sampleDB.execSQL(Costanti.QUERY_DROP_TABLE_NAME_LISTA_ARTICOLO);**/


            if (sampleDB != null) {
                Cursor tableExist = sampleDB.rawQuery("SELECT * from sqlite_master WHERE name ='" + Costanti.TABLE_NAME_ARTICOLO + "' and type='table'", null);
                Log.d(METHOD_NAME, "tableExist: " + tableExist);
                if (tableExist != null) {
                    sampleDB.execSQL(Costanti.QUERY_CREATE_ARTICOLO);
                    sampleDB.execSQL(Costanti.QUERY_CREATE_LISTA);
                    sampleDB.execSQL(Costanti.QUERY_CREATE_LISTA_ARTICOLO);

                    Log.d("query ", Costanti.QUERY_JOIN_LISTA_ARTICOLO);
                    //    String queryInsert = "INSERT INTO " + Costanti.TABLE_NAME_LISTA + "(" + Costanti.COLUMN_NAME_NOME_LISTA + ") values (?)";


                    String eseguimi = Costanti.QUERY_JOIN_LISTA_ARTICOLO + idLista + ";";
                    risultato = sampleDB.rawQuery(Costanti.QUERY_JOIN_LISTA_ARTICOL, new String[]{String.valueOf(idLista)});


                    Log.d(METHOD_NAME, "risultato: " + risultato);
                    if (risultato.getCount() > 0) {
                        Log.d(METHOD_NAME, "risultato presente ");

                        if (risultato.moveToNext()) {
                            do {
                                Log.d(METHOD_NAME, "prodotto: " + risultato);
                                String prodotto = risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_NOME_ARTICOLO));
                                Log.d(METHOD_NAME, "prodotto: " + prodotto);
                                results.add(prodotto);
                                Log.d(METHOD_NAME, "risultato presente ");

                            } while (risultato.moveToNext()); //Move to next row
                        }
                    }

                }
            }
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addProductLista(v);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final String item = (String) parent.getItemAtPosition(position);
                    Log.d(METHOD_NAME, "item: " + item);

                    sampleDB.execSQL("DELETE FROM " + Costanti.TABLE_NAME_ARTICOLO + " WHERE " + Costanti.COLUMN_NAME_NOME_ARTICOLO + "='" + item + "'");
                    Log.d(METHOD_NAME, "eliminato: " + item);
                    results.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), item + " eliminato...", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        } else {

            nuovoProdotto.setWidth(245);
            quantita.setVisibility(View.INVISIBLE);
            if (sampleDB != null) {
                Cursor tableExist = sampleDB.rawQuery("SELECT * from sqlite_master WHERE name ='" + Costanti.TABLE_NAME_PRODOTTI + "' and type='table'", null);
                Log.d(METHOD_NAME, "tableExist: " + tableExist);
                if (tableExist != null) {
                    sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + Costanti.TABLE_NAME_PRODOTTI + " (" + Costanti.COLUMN_NAME_PRODOTTO + " VARCHAR);");


                    risultato = sampleDB.rawQuery("SELECT " + Costanti.COLUMN_NAME_PRODOTTO + " FROM " + Costanti.TABLE_NAME_PRODOTTI, null);
                    Log.d(METHOD_NAME, "risultato: " + risultato);
                    if (risultato.getCount() > 0) {
                        Log.d(METHOD_NAME, "risultato presente ");

                        if (risultato.moveToNext()) {
                            do {
                                String prodotto = risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_PRODOTTO));
                                Log.d(METHOD_NAME, "prodotto: " + prodotto);
                                results.add(prodotto);
                            } while (risultato.moveToNext()); //Move to next row
                        }
                    }
                }
            }

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final String item = (String) parent.getItemAtPosition(position);
                    Log.d(METHOD_NAME, "item: " + item);

                    sampleDB.execSQL("DELETE FROM " + Costanti.TABLE_NAME_PRODOTTI + " WHERE " + Costanti.COLUMN_NAME_PRODOTTO + "='" + item + "'");
                    Log.d(METHOD_NAME, "eliminato: " + item);
                    results.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), item + " eliminato...", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addProduct(v);
                }
            });
        }

        adapter = new ListProdottiAdapter(getActivity(), R.layout.item_custom, results);
        if (listView != null) {
            listView.setAdapter(adapter);
            Log.d(METHOD_NAME, "aggiunto adapter");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, results);
        nuovoProdotto.setAdapter(adapter);

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
        Log.d(METHOD_NAME, "nuovoProdotto: " + nuovoProdotto.getText());
        String prodottoAggiunto = nuovoProdotto.getText().toString();
        if (prodottoAggiunto.equals("")) {
            Toast.makeText(getActivity(), "Inserire un prodotto", Toast.LENGTH_LONG).show();
            return;
        }
        try {

            //TODO verifica duplicati

            sampleDB.execSQL("INSERT INTO " + Costanti.TABLE_NAME_PRODOTTI + "(" + Costanti.COLUMN_NAME_PRODOTTO + ") values ('" + nuovoProdotto.getText() + "');");
            Toast.makeText(getActivity(), nuovoProdotto.getText() + " aggiunta", Toast.LENGTH_LONG).show();
            nuovoProdotto.setText("");
            Log.d(METHOD_NAME, "aggiunto prodotto");
            adapter.add(prodottoAggiunto);
        } catch (SQLException sqE) {
            Log.e(METHOD_NAME, "sqE: " + sqE);
            Toast.makeText(getActivity(), "Salvataggio non eseguito", Toast.LENGTH_LONG).show();
        }

    }

    public void addProductLista(View view) {
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
            if (idLista == -1) {
                //crea lista se non presente
                String queryInsert = "INSERT INTO " + Costanti.TABLE_NAME_LISTA + "(" + Costanti.COLUMN_NAME_NOME_LISTA + ") values (?)";
                SQLiteStatement insertStmt = sampleDB.compileStatement(queryInsert);
                insertStmt.clearBindings();
                insertStmt.bindString(1, nomeLista);

                idLista = insertStmt.executeInsert();
                Toast.makeText(getActivity(), idLista + " idLista", Toast.LENGTH_LONG).show();
            }

            //inserisco articolo
            String insertArticolo = "INSERT INTO " + Costanti.TABLE_NAME_ARTICOLO + "(" + Costanti.COLUMN_NAME_NOME_ARTICOLO + ") values (?);";
            SQLiteStatement insertStmtArt = sampleDB.compileStatement(insertArticolo);
            insertStmtArt.clearBindings();
            insertStmtArt.bindString(1, nuovoProdotto.getText().toString());
            Long idArticolo = insertStmtArt.executeInsert();

            String insertListaArticolo = "INSERT INTO " + Costanti.TABLE_NAME_LISTA_ARTICOLO + "(" + Costanti.COLUMN_NAME_ID_LISTA + ", " + Costanti.COLUMN_NAME_ID_ARTICOLO + " , " + Costanti.COLUMN_NAME_QUANTITA + " )" +
                    " values (?, ?, ?);";
            SQLiteStatement insertStmt = sampleDB.compileStatement(insertListaArticolo);
            insertStmt.clearBindings();
            insertStmt.bindLong(1, idLista);
            insertStmt.bindLong(2, idArticolo);
            insertStmt.bindLong(3, Long.parseLong(quantita.getText().toString()));

            Long idJoinTable = insertStmt.executeInsert();

            Toast.makeText(getActivity(), idJoinTable + " aggiunta", Toast.LENGTH_LONG).show();
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
        Cursor risultato = sampleDB.rawQuery("SELECT " + Costanti.COLUMN_NAME_PRODOTTO + " FROM " + Costanti.TABLE_NAME_PRODOTTI, null);
        Log.d(METHOD_NAME, "risultato: " + risultato);
        if (risultato != null) {
            Log.d(METHOD_NAME, "risultato presente ");
            if (risultato.moveToFirst()) {
                do {
                    String prodotto = risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_PRODOTTO));
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
            /** Intent startMain = new Intent(getActivity(), MainActivity.class);
             startMain.addCategory(Intent.CATEGORY_HOME);
             startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(startMain);
             Fragment fragment = new WelcomeFragment().newInstance("", "");
             getActivity().getSupportFragmentManager().beginTransaction()
             .replace(R.id.container, fragment).commitAllowingStateLoss();
             ((MainActivity)getActivity()).onSectionAttached(1);**/
            ((MainActivity) getActivity()).goToFragmentMenu(0);
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
