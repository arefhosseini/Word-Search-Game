package com.fearefull.wordsearch.di.modules;

import android.content.Context;

import com.fearefull.wordsearch.data.OntologyDataSource;
import com.fearefull.wordsearch.data.json.OntologyJsonDataSource;
import com.fearefull.wordsearch.data.sqlite.DbHelper;
import com.fearefull.wordsearch.data.sqlite.GameDataSQLiteDataSource;
import com.fearefull.wordsearch.data.xml.WordXmlDataSource;
import com.fearefull.wordsearch.data.GameDataSource;
import com.fearefull.wordsearch.data.WordDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aref Hosseini on 18/07/17.
 */

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    DbHelper provideDbHelper(Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    GameDataSource provideGameRoundDataSource(DbHelper dbHelper) {
        return new GameDataSQLiteDataSource(dbHelper);
    }

//    @Provides
//    @Singleton
//    WordDataSource provideWordDataSource(DbHelper dbHelper) {
//        return new WordSQLiteDataSource(dbHelper);
//    }

    @Provides
    @Singleton
    WordDataSource provideWordDataSource(Context context) {
        return new WordXmlDataSource(context);
    }

    @Provides
    @Singleton
    OntologyDataSource provideOntologyDataSource(Context context) {
        return new OntologyJsonDataSource(context);
    }

}
