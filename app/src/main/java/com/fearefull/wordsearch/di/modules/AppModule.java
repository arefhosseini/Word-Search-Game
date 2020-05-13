package com.fearefull.wordsearch.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fearefull.wordsearch.data.AppDataManager;
import com.fearefull.wordsearch.data.DataManager;
import com.fearefull.wordsearch.data.OntologyDataSource;
import com.fearefull.wordsearch.data.remote.ApiHelper;
import com.fearefull.wordsearch.features.ViewModelFactory;
import com.fearefull.wordsearch.data.GameDataSource;
import com.fearefull.wordsearch.data.GameThemeRepository;
import com.fearefull.wordsearch.data.WordDataSource;
import com.fearefull.wordsearch.features.gamehistory.GameHistoryViewModel;
import com.fearefull.wordsearch.features.gameover.GameOverViewModel;
import com.fearefull.wordsearch.features.gameplay.GamePlayViewModel;
import com.fearefull.wordsearch.features.mainmenu.MainMenuViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fearefull.wordsearch.commons.AppConstants.BASE_URL;
import static com.fearefull.wordsearch.commons.AppConstants.CONNECT_CLIENT;

/**
 * Created by Aref Hosseini.
 */

@Module
public class AppModule {

    private Application mApp;

    public AppModule(Application application) {
        mApp = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApp;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    ViewModelFactory provideViewModelFactory(GameDataSource gameDataSource,
                                             WordDataSource wordDataSource,
                                             DataManager dataManager,
                                             OntologyDataSource ontologyDataSource) {
        return new ViewModelFactory(
                new GameOverViewModel(gameDataSource),
                new GamePlayViewModel(gameDataSource, wordDataSource),
                new MainMenuViewModel(new GameThemeRepository(), dataManager, ontologyDataSource),
                new GameHistoryViewModel(gameDataSource)
        );
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_CLIENT, TimeUnit.SECONDS)
                .readTimeout(CONNECT_CLIENT, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_CLIENT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(Retrofit retrofit) {
        return retrofit.create(ApiHelper.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }
}
