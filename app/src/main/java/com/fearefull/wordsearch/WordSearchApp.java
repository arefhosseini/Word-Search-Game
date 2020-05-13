package com.fearefull.wordsearch;

import android.app.Application;

import com.fearefull.wordsearch.di.component.AppComponent;
import com.fearefull.wordsearch.di.component.DaggerAppComponent;
import com.fearefull.wordsearch.di.modules.AppModule;

/**
 * Created by Aref Hosseini.
 */

public class WordSearchApp extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
