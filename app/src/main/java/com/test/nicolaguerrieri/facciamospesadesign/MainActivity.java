package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity
        implements ActionBar.TabListener, NavigationDrawerFragment.NavigationDrawerCallbacks, WelcomeFragment.OnFragmentInteractionListener, ListaSpesaFragment.OnFragmentInteractionListener, CarteFragment.OnFragmentInteractionListener, ScanResultFragment.OnFragmentInteractionListener, CarteViewFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            default:
            case 0:
                fragment = new WelcomeFragment().newInstance("", "");
                break;
            case 1:
                fragment = new ListaSpesaFragment().newInstance("", "");
                break;

            case 2:
                fragment = new CarteFragment().newInstance("", "");
                break;
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);

        // PER ELIMINARE IL COMPORTAMENTO PER CUI IL TASTO INDIETRO PORTA AL FRAGMENT PRECEDENTE BASTA RIMUOVERE
        // addToBackStack(null).
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commitAllowingStateLoss();
        onSectionAttached(position + 1);
        getSupportActionBar().setTitle(mTitle);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
        if (requestCode == 18) {
//            if(resultCode == 0) {
/*            Intent intentCarte = new Intent(getActivity(), CarteActivity.class);
            this.finish();
            startActivity(intentCarte);*/
//            }
        } else {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            Fragment fragment = null;
            if (scanResult != null) {
                if (scanResult.getContents() != null) {
                    fragment = new ScanResultFragment().newInstance("", "");
                    Bundle args = new Bundle();
                    args.putInt(ARG_SECTION_NUMBER, 1);
                    args.putString("codice", scanResult.getContents());
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment).commitAllowingStateLoss();
                } else {
                    //abortito chiamata
                    onNavigationDrawerItemSelected(2);
                }

            }
        }


        Log.d(METHOD_NAME, "fine");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
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