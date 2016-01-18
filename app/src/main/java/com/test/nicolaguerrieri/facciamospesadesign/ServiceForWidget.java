package com.test.nicolaguerrieri.facciamospesadesign;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by nicola.guerrieri2 on 13/01/2016.
 */
public class ServiceForWidget extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        return (new ListViewProvider(this.getApplicationContext(), intent));
    }


}
