package com.test.nicolaguerrieri.facciamospesadesign;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferenzeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferenzeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferenzeFragment extends PreferenceFragmentCompat {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PreferenzeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onBindPreferences() {
        super.onBindPreferences();


    }

    public static PreferenzeFragment newInstance(String param1, String param2) {
        PreferenzeFragment fragment = new PreferenzeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferenze);
  /**      Preference myPref = (Preference) findPreference("licenze");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
              return  true;
            }
        });**/
}



/**
    01-19 15:06:37.780 5637-5637/? E/SQLiteLog: (1) no such table: ARTICOLO
    01-19 15:06:37.786 8092-8092/? D/ChimeraCfgMgr: Loading module com.google.android.gms.vision from APK com.google.android.gms
    01-19 15:06:37.788 5637-5637/? D/AndroidRuntime: Shutting down VM
    01-19 15:06:37.788 5637-5637/? V/DeployGateUncaughtExceptionHandler: DeployGate caught an exception, trying to send to the service
    01-19 15:06:37.807 5637-5637/? E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.test.nicolaguerrieri.facciamospesadesign, PID: 5637
    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.test.nicolaguerrieri.facciamospesadesign/com.test.nicolaguerrieri.facciamospesadesign.MainActivity}: android.database.sqlite.SQLiteException: no such table: ARTICOLO (code 1): , while compiling: SELECT NOME_ARTICOLO FROM ARTICOLO;
    at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2416)
    at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2476)
    at android.app.ActivityThread.-wrap11(ActivityThread.java)
    at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1344)
    at android.os.Handler.dispatchMessage(Handler.java:102)
    at android.os.Looper.loop(Looper.java:148)
    at android.app.ActivityThread.main(ActivityThread.java:5417)
    at java.lang.reflect.Method.invoke(Native Method)
    at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:726)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:616)
    Caused by: android.database.sqlite.SQLiteException: no such table: ARTICOLO (code 1): , while compiling: SELECT NOME_ARTICOLO FROM ARTICOLO;
    at android.database.sqlite.SQLiteConnection.nativePrepareStatement(Native Method)
    at android.database.sqlite.SQLiteConnection.acquirePreparedStatement(SQLiteConnection.java:887)
    at android.database.sqlite.SQLiteConnection.prepare(SQLiteConnection.java:498)
    at android.database.sqlite.SQLiteSession.prepare(SQLiteSession.java:588)
    at android.database.sqlite.SQLiteProgram.<init>(SQLiteProgram.java:58)
    at android.database.sqlite.SQLiteQuery.<init>(SQLiteQuery.java:37)
    at android.database.sqlite.SQLiteDirectCursorDriver.query(SQLiteDirectCursorDriver.java:44)
    at android.database.sqlite.SQLiteDatabase.rawQueryWithFactory(SQLiteDatabase.java:1316)
    at android.database.sqlite.SQLiteDatabase.rawQuery(SQLiteDatabase.java:1255)
    at com.test.nicolaguerrieri.facciamospesadesign.ListaSpesaFastFragment.onCreateView(ListaSpesaFastFragment.java:323)
    at android.support.v4.app.Fragment.performCreateView(Fragment.java:1965)
    at android.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1078)
    at android.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:1259)
    at android.support.v4.app.BackStackRecord.run(BackStackRecord.java:738)
    at android.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:1624)
    at android.support.v4.app.FragmentController.execPendingActions(FragmentController.java:330)
    at android.support.v4.app.FragmentActivity.onStart(FragmentActivity.java:547)
    at android.app.Instrumentation.callActivityOnStart(Instrumentation.java:1237)
    at android.app.Activity.performStart(Activity.java:6268)
    at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2379)
    at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2476) 
    at android.app.ActivityThread.-wrap11(ActivityThread.java) 
    at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1344) 
    at android.os.Handler.dispatchMessage(Handler.java:102) 
    at android.os.Looper.loop(Looper.java:148) 
    at android.app.ActivityThread.main(ActivityThread.java:5417) 
    at java.lang.reflect.Method.invoke(Native Method) 
    at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:726) 
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:616) 
**/


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
