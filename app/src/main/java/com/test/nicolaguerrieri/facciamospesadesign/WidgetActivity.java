package com.test.nicolaguerrieri.facciamospesadesign;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

public class WidgetActivity extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                WidgetActivity.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.activity_widget);


            Intent svcIntent = new Intent(context, ServiceForWidget.class);
            //passing app widget id to that RemoteViews Service
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            //setting a unique Uri to the intent
            //don't know its purpose to me right now
            //svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            //setting adapter to listview of the widget
            remoteViews.setRemoteAdapter(R.id.listaSpesaWidget, svcIntent);
            //setting an empty view in case of no data
            remoteViews.setEmptyView(R.id.listaSpesaWidget, R.id.listaSpesaWidget);


            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        /**  Log.w("WidgetExample", String.valueOf(number));
         // Set the text
         remoteViews.setTextViewText(R.id.nuovoProdottoWidget, String.valueOf(number));

         // Register an onClickListener
         Intent intent = new Intent(context, WidgetActivity.class);
         // aggiorna il widgeet
         intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
         intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);


         PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
         0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         remoteViews.setOnClickPendingIntent(R.id.nuovoProdottoWidget, pendingIntent);
         appWidgetManager.updateAppWidget(widgetId, remoteViews);**/

    }
}
