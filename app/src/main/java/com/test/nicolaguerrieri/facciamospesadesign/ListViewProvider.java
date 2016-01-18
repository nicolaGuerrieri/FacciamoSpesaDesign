package com.test.nicolaguerrieri.facciamospesadesign;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.test.nicolaguerrieri.facciamospesadesign.utility.Costanti;

import java.util.ArrayList;
import java.util.List;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 */
public class ListViewProvider implements RemoteViewsService.RemoteViewsFactory{
    public ArrayList<String> listItemList = new ArrayList<String>();
    private Context context = null;

    SQLiteDatabase sampleDB = null;
    List<String> results = new ArrayList<>();


    private int appWidgetId;

    public ListViewProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        //    listItemList.clear();
        //   listItemList = intent.getStringArrayListExtra("prodotti");
        populateListItem();
    }

    /**
     * private void populateListItem() {
     * <p/>
     * <p/>
     * for (int i = 0; i < 10; i++) {
     * String listItem = "punto n. " + i;
     * listItemList.add(listItem);
     * }
     * <p/>
     * }
     **/
    private void populateListItem() {

        listItemList.clear();
        sampleDB = context.openOrCreateDatabase(Costanti.DB_NAME, context.MODE_PRIVATE, null);

        Cursor risultato = sampleDB.rawQuery("SELECT " + Costanti.COLUMN_NAME_PRODOTTO + " FROM " + Costanti.TABLE_NAME_PRODOTTI, null);

        if (risultato.getCount() > 0) {

            if (risultato.moveToNext()) {
                do {
                    String prodotto = risultato.getString(risultato.getColumnIndex(Costanti.COLUMN_NAME_PRODOTTO));
                    listItemList.add(prodotto);
                } while (risultato.moveToNext()); //Move to next row
            }
        }
    }


    /*
  *Similar to getView of Adapter where instead of View
  *we return RemoteViews
  *
  */
    @Override
    public RemoteViews getViewAt(int position) {

        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.item_custom_widget);
        String listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.testoWidget, listItem);
        return remoteView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        populateListItem();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}