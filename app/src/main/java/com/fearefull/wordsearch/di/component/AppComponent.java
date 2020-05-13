package com.fearefull.wordsearch.di.component;

import com.fearefull.wordsearch.di.modules.AppModule;
import com.fearefull.wordsearch.di.modules.DataSourceModule;
import com.fearefull.wordsearch.features.FullscreenActivity;
import com.fearefull.wordsearch.features.gamehistory.GameHistoryActivity;
import com.fearefull.wordsearch.features.gameover.GameOverActivity;
import com.fearefull.wordsearch.features.gameplay.GamePlayActivity;
import com.fearefull.wordsearch.features.mainmenu.MainMenuActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Aref Hosseini on 18/07/17.
 */

@Singleton
@Component(modules = {AppModule.class, DataSourceModule.class})
public interface AppComponent {

    void inject(GamePlayActivity activity);

    void inject(MainMenuActivity activity);

    void inject(GameOverActivity activity);

    void inject(FullscreenActivity activity);

    void inject(GameHistoryActivity activity);

}
