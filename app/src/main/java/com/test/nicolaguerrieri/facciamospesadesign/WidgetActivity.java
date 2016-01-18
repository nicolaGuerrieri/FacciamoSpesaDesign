package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class WidgetActivity extends AppWidgetProvider {


  private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        final String METHOD_NAME = ".onCreate() >>>> ";

        final int N = appWidgetIds.length;
        /*int[] appWidgetIds holds ids of multiple instance of your widget
         * meaning you are placing more than one widgets on your homescreen*/

        ComponentName thisWidget = new ComponentName(context,
                getClass());
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);


        for (int widgetId : allWidgetIds) {

            RemoteViews remoteViews = updateWidgetListView(context, widgetId);

            // intent per update lista
            Intent intent = new Intent(context, getClass());
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.refresh, pendingIntent);

            Intent intentOpenApp = new Intent(context, MainActivity.class);
            PendingIntent pendingIntentOpenApp = PendingIntent.getActivity(context, 0, intentOpenApp, 0);
            remoteViews.setOnClickPendingIntent(R.id.modify, pendingIntentOpenApp);

            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.listViewWidget);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

   /**
     * @Override public void onReceive(Context context, Intent intent) {
     * <p/>
     * String action = intent.getAction();
     * Log.d("", "");
     * }
     **/
    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.activity_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, ServiceForWidget.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,  svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        return remoteViews;
    }


}