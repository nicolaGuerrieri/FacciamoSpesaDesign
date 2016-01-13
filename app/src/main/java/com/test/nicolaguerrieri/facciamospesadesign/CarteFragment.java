package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.test.nicolaguerrieri.facciamospesadesign.adapter.ImageAdapter;
import com.test.nicolaguerrieri.facciamospesadesign.model.Carta;
import com.test.nicolaguerrieri.facciamospesadesign.model.ViewHolder;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CarteFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private GridView mListView;
    private ImageAdapter mAdapter;
    private ActionMode mActionMode;
    private List<Carta> listaCarte = null;

    TextView noCarteText;
    ImageView imageViewNoCarte;

    SQLiteDatabase sampleDB = null;

    // TODO: Rename and change types of parameters
    public static CarteFragment newInstance(String param1, String param2) {
        CarteFragment fragment = new CarteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        listaCarte = new ArrayList<>();
        listaCarte.addAll(getCarte());

        mAdapter = new ImageAdapter(getActivity(), listaCarte);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carte_grid, container, false);

        // Set the adapter
        mListView = (GridView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        noCarteText = (TextView) view.findViewById(R.id.textNoCarte);
        imageViewNoCarte = (ImageView) view.findViewById(R.id.immagineNoCarte);

        showResultLista();

        final Button buttonScan = (Button) view.findViewById(R.id.scanCarta);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCard();
            }
        });

        buttonScan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == buttonScan) {
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder carta = (ViewHolder) view.getTag();

                showCarta(carta.getIdCarta());

                mListView.setItemChecked(position, false);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Start the CAB using the ActionMode.Callback defined above
                if (mActionMode != null) {
                    return false;
                }

                mActionMode = getActivity().startActionMode(mActionModeCallback);
                mActionMode.setTag(position);
                mListView.setItemChecked(position, true);
                view.setSelected(true);
                return true;
            }
        });

        return view;
    }

    public void showResultLista() {

        if (listaCarte.size() == 0) {
            /**  final Dialog dialog = new Dialog(getActivity());
             dialog.setContentView(R.layout.dialog_custom);
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


             TextView tw = (TextView) dialog.findViewById(R.id.textDialog);

             tw.setText("Inizia ad aggiungere le tue fidelity card per portarle sempre con te...");
             Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
             // if button is clicked, close the custom dialog
             dialogButton.setText("Ok");

             dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            dialog.dismiss();
            }
            });

             dialog.show();**/
            imageViewNoCarte.setVisibility(View.VISIBLE);
            noCarteText.setVisibility(View.VISIBLE);

            mListView.setVisibility(View.INVISIBLE);
        } else {
            imageViewNoCarte.setVisibility(View.INVISIBLE);
            noCarteText.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    public void showCarta(int idCarta) {
        Bundle args = new Bundle();
        args.putInt("carta", idCarta);
        ((MainActivity) getActivity()).goToFragmentMenu(3, args, true);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
        }
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
        public void onFragmentInteraction(String id);
    }


    public void scanCard() {
        IntentIntegrator intent = new IntentIntegrator(getActivity());
        intent.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);

//        Intent intentShowResult = new Intent(this, ScanActivity.class);
//        intentShowResult.putExtra("codice", "205007113290");
//        startActivityForResult(intentShowResult, 18);
    }


    public List<Carta> getCarte() {
        final String METHOD_NAME = ".getCarte() ";

        List<Carta> listaCarte = new ArrayList<Carta>();
        sampleDB = getActivity().openOrCreateDatabase(Costanti.DB_NAME, getActivity().MODE_PRIVATE, null);
        Log.d(METHOD_NAME, "sampleDB:" + sampleDB.getPath());
        if (sampleDB != null) {
            Cursor tableExist = sampleDB.rawQuery("SELECT * from sqlite_master WHERE name ='" + Costanti.TABLE_NAME_CARTE + "' and type='table'", null);
            Log.d(METHOD_NAME, "tableExist: " + tableExist);
            if (tableExist != null) {

                String queryCreate = Costanti.QUERY_CREATE;
                Log.d(METHOD_NAME, "queryCreate: " + queryCreate);
                sampleDB.execSQL(queryCreate);
                Cursor risultato = sampleDB.rawQuery("SELECT * FROM " + Costanti.TABLE_NAME_CARTE, null);
                Log.d(METHOD_NAME, "risultato.size: " + risultato.getCount());
                Carta carta = null;
                if (risultato != null) {
                    Log.d(METHOD_NAME, "risultato presente ");
                    if (risultato.moveToFirst()) {
                        do {
                            carta = new Carta();
                            carta.setId(risultato.getInt(risultato.getColumnIndex(Costanti.COLUMN_NAME_ID)));
                            carta.setTitolo(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_NOME)));
                            carta.setLogo(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_LOGO)));
                            carta.setCodice(risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_CODICE)));
                            byte[] image = risultato.getBlob(risultato.getColumnIndex(Costanti.COLUMN_NAME_IMMAGINE));
                            Log.d(METHOD_NAME, "immagine: " + image.length);
                            if (image != null) {
                                Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);
                                carta.setIcona(b);
                            }

                            listaCarte.add(carta);
                        } while (risultato.moveToNext()); //Move to next row
                    }
                }
                return listaCarte;
            }
        }
        return listaCarte;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_carte, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_menu, menu);
            return true;

//            getMenuInflater().inflate(R.menu.menu_carte, menu);
//            return false;
        }


        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.action_settings) {
                return true;
            } else if (id == R.id.action_trash) {
                int item_postion = Integer.parseInt(mode.getTag().toString());
                Carta carta = (Carta) mListView.getAdapter().getItem(item_postion);
                sampleDB.execSQL("DELETE FROM " + Costanti.TABLE_NAME_CARTE + " WHERE " + Costanti.COLUMN_NAME_NOME + "='" + carta.getTitolo() + "' and " + Costanti.COLUMN_NAME_LOGO + "='" + carta.getLogo() + "' and " + Costanti.COLUMN_NAME_ID + "='" + carta.getId() + "'");
                Log.d("", "eliminato: " + item);
                listaCarte.remove(item_postion);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Carta eliminata", Toast.LENGTH_LONG).show();
                mode.finish();
                return false;
//              eliminaElemento();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            int item_postion = Integer.parseInt(mode.getTag().toString());
            mListView.setItemChecked(item_postion, false);
            mActionMode = null;

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_info) {
            imageViewNoCarte.setVisibility(View.INVISIBLE);
            noCarteText.setVisibility(View.INVISIBLE);
            apriDialogCarte("Aggiungi le tue fidelity card, clicca su scan", 1);
        }

        return super.onOptionsItemSelected(item);
    }


    public void apriDialogCarte(String messaggio, int step) {


        // apriamo dialog per spiegare
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView iw = (ImageView) dialog.findViewById(R.id.imageDialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView tw = (TextView) dialog.findViewById(R.id.textDialog);


        Drawable res = null;
        Integer imageResource = null;


        tw.setText(messaggio);
        // if button is clicked, close the custom dialog
        dialogButton.setText("Next");

        switch (step) {
            case 1:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apriDialogCarte("Inquadra il tuo codice a barre e attendi la scansione", 2);
                    }
                });
                break;
            case 2:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apriDialogCarte("Seleziona il negozio a cui appartiene la fidelity card", 3);
                    }
                });
                imageResource = getActivity().getResources().getIdentifier("@drawable/carta", null, getActivity().getPackageName());
                break;
            case 3:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apriDialogCarte("Clicca sul tasto ''Più'' per aggiungere la carta", 4);
                    }
                });
                imageResource = getActivity().getResources().getIdentifier("@drawable/sceglinomecarta", null, getActivity().getPackageName());
                break;
            default:
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        showResultLista();
                    }
                });
                imageResource = getActivity().getResources().getIdentifier("@drawable/nomecarta2", null, getActivity().getPackageName());
                dialogButton.setText("Ok");
                break;
        }


        if (imageResource != null) {
            res = getActivity().getResources().getDrawable(imageResource);
            iw.setImageDrawable(res);
        }
        dialog.show();

    }
}
