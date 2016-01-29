package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.deploygate.sdk.DeployGate;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;
import com.test.nicolaguerrieri.facciamospesadesign.utility.Utility;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ActionBar.TabListener, NavigationDrawerFragment.NavigationDrawerCallbacks, WelcomeFragment.OnFragmentInteractionListener, ListaSpesaFastFragment.OnFragmentInteractionListener, CarteFragment.OnFragmentInteractionListener, ScanResultFragment.OnFragmentInteractionListener, CarteViewFragment.OnFragmentInteractionListener, ListeSpesaFragment.OnFragmentInteractionListener, PreferenzeFragment.OnFragmentInteractionListener {

    Utility utility = new Utility();

    public List<String> getListaNegozi() {
        return listaNegozi;
    }

    public void setListaNegozi(List<String> listaNegozi) {
        this.listaNegozi = listaNegozi;
    }

    public List<String> listaNegozi = new ArrayList<String>();
    private ListaSpesaFastFragment fragmentSpesa = null;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence mTitle;
    public CharSequence mTitleHold;

    public CharSequence getmTitle() {
        return mTitle;
    }

    public CharSequence getmTitleHold() {
        return mTitleHold;
    }

    public void setmTitleHold(CharSequence mTitleHold) {
        this.mTitleHold = mTitleHold;
    }

    public void setmTitle(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getString(R.string.title_todo);

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        } catch (Exception e) {
            DeployGate.logError(e.getMessage());
        }
    }


    public ListaSpesaFastFragment getFragmentSpesa() {
        return fragmentSpesa;
    }

    public void setFragmentSpesa(ListaSpesaFastFragment fragmentSpesa) {
        this.fragmentSpesa = fragmentSpesa;
    }


    public void apriHelp() {
        Log.d("", "");
        mNavigationDrawerFragment.closeDrawer();
        utility.spiegaUnPo("Benvenuto in Facciamo spesa, l'app pensata per fare spesa e per portare sempre con te le tue fidelity card, ora tu dirai: \n\"Caro Nicola di app così ce ne sono tante perchè scegliere la tua?\"\n\nClicca su NEXT e lo scoprirai...", 1, R.layout.primo_giro, "Next", this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            default:
            case Costanti.LISTA_FRAGMENT:
                fragment = new ListaSpesaFastFragment().newInstance("", "");
                break;
            case Costanti.LISTE_SPESA_FRAGMENT:
                fragment = new ListeSpesaFragment().newInstance("", "");
                break;
            case Costanti.CARTE_FRAGMENT:
                fragment = new CarteFragment().newInstance("", "");
                break;
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // PER ELIMINARE IL COMPORTAMENTO PER CUI IL TASTO INDIETRO PORTA AL FRAGMENT PRECEDENTE BASTA RIMUOVERE
        // addToBackStack(null).
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right_);
        fragmentTransaction.replace(R.id.container, fragment).commitAllowingStateLoss();
        onSectionAttached(position + 1);
        getSupportActionBar().setTitle(mTitle);
    }

    public void goToFragmentMenu(int position) {
        onNavigationDrawerItemSelected(position);
    }

    /**
     * public static  final int WELCOME_FRAGMENT= 1;
     * public static  final int LISTA_FRAGMENT= 0;
     * public static  final int CARTE_FRAGMENT= 2;
     * public static  final int VIEW_CARTA_FRAGMENT= 3;
     * public static  final int SCAN_CARTA_FRAGMENT= 4;
     **/
    public void goToFragmentMenu(int position, Bundle bundleArgument) {
        goToFragmentMenu(position, bundleArgument, false);
    }

    public void goToFragmentMenu(int position, Bundle bundleArgument, boolean mantieniStato) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            default:
            case Costanti.LISTA_FRAGMENT:
                fragment = new ListaSpesaFastFragment().newInstance("", "");
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case Costanti.LISTE_SPESA_FRAGMENT:
                fragment = new ListeSpesaFragment().newInstance("", "");
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case Costanti.CARTE_FRAGMENT:
                fragment = new CarteFragment().newInstance("", "");
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case Costanti.VIEW_CARTA_FRAGMENT:
                fragment = new CarteViewFragment().newInstance("", "");
                break;
            case Costanti.SCAN_CARTA_FRAGMENT:
                fragment = new ScanResultFragment().newInstance("", "");
                break;
            case Costanti.SETTINGS_FRAGMENT:
                mTitleHold = mTitle;
                fragment = new PreferenzeFragment().newInstance("", "");
                break;
        }
        if (bundleArgument != null) {
            bundleArgument.putInt(ARG_SECTION_NUMBER, position);
            fragment.setArguments(bundleArgument);

        }
        if (bundleArgument.getBoolean("crea", false)) {
            onSectionAttached(position + 1, bundleArgument.getString("nomeLista"));
        } else {
            onSectionAttached(position + 1);
        }
        // PER ELIMINARE IL COMPORTAMENTO PER CUI IL TASTO INDIETRO PORTA AL FRAGMENT PRECEDENTE BASTA RIMUOVERE
        // addToBackStack(null).
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right_);
        if (mantieniStato) {
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commitAllowingStateLoss();
        } else {
            fragmentTransaction.replace(R.id.container, fragment).commitAllowingStateLoss();
        }


        getSupportActionBar().setTitle(mTitle);

    }

    public void onSectionAttached(int number) {
        onSectionAttached(number, null);
    }

    public void onSectionAttached(int number, String nomeLista) {
        switch (number) {
            case 1:
                if (!StringUtils.isBlank(nomeLista)) {
                    mTitle = "Lista " + nomeLista;
                } else {
                    mTitle = getString(R.string.title_todo);
                }
                break;
            case 2:
                mTitle = getString(R.string.title_liste);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                mTitle = getString(R.string.title_info);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /**       actionBar.removeAllTabs();
         for (int i = 1; i <= 3; i++) {
         ActionBar.Tab tab = actionBar.newTab();
         tab.setText("Tab " + i);
         tab.setTabListener(this);
         actionBar.addTab(tab);
         }
         actionBar.setDisplayShowTitleEnabled(true);**/
        actionBar.setTitle(mTitle);
        actionBar.setElevation(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            //   restoreActionBar();
            return true;
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_widget, menu);
        return true;

        //  return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Bundle bundle = new Bundle();
            goToFragmentMenu(5, bundle, true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//16908332

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    //METODO CHE TORNA INDIETRO AL FRAGMENT PRECEDENTE
    @Override
    public void onBackPressed() {

        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            //super.onBackPressed();

            AlertDialog alertbox = new AlertDialog.Builder(this)
                    .setMessage("Sei sicuro di voler uscire dall'app")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            //close();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final String METHOD_NAME = ".modificaComponente() ";
        Log.d(METHOD_NAME, "invocata onActivityResult");

        switch (requestCode) {
            case 131572: {
                Log.d("", "" + resultCode);
                if (resultCode == RESULT_OK && null != intent) {

                    ArrayList<String> result = intent
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (getFragmentSpesa() != null) {
                        getFragmentSpesa().prendiParola(result.get(0));
                    }
                    /** android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create(); //Read Update
                     alertDialog.setTitle(result.get(0));
                     alertDialog.setMessage(result.get(0));

                     alertDialog.setButton("Ok ", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                     }
                     });

                     alertDialog.show();
                     AutoCompleteTextView au = (AutoCompleteTextView) findViewById(R.id.nuovoProdotto);
                     au.setText(result.get(0));**/


                }
                break;
            }
            case 18: {
                break;
            }
            default: {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                Fragment fragment = null;
                if (scanResult != null) {
                    if (scanResult.getContents() != null) {
                        /** fragment = new ScanResultFragment().newInstance("", "");
                         fragment.setArguments(args);
                         getSupportFragmentManager().beginTransaction()
                         .replace(R.id.container, fragment).commitAllowingStateLoss();**/
                        Bundle args = new Bundle();
                        args.putInt(ARG_SECTION_NUMBER, 1);
                        args.putString("codice", scanResult.getContents());
                        goToFragmentMenu(4, args);
                    } else {
                        //abortito chiamata
                        onNavigationDrawerItemSelected(2);
                    }

                }
                break;
            }
        }
        Log.d(METHOD_NAME, "fine");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


/**
 * come inserire il menu action bar
 * You should consider from your Fragment code:
 * <p/>
 * 1) Implementing onCreateOptionsMenu(Menu menu, MenuInflater inflater)
 * <p/>
 * 2) Calling setHasOptionsMenu
 * <p/>
 * 3) And also implementing onOptionsItemSelected(MenuItem item)
 * <p/>
 * Then you will be ok on both the phone and tablet.
 **/


}