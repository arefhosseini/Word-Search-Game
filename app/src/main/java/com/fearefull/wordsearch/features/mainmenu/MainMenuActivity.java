package com.fearefull.wordsearch.features.mainmenu;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fearefull.wordsearch.R;
import com.fearefull.wordsearch.commons.Util;
import com.fearefull.wordsearch.features.ViewModelFactory;
import com.fearefull.wordsearch.WordSearchApp;
import com.fearefull.wordsearch.features.FullscreenActivity;
import com.fearefull.wordsearch.features.gamehistory.GameHistoryActivity;
import com.fearefull.wordsearch.features.gameplay.GamePlayActivity;
import com.fearefull.wordsearch.model.GameTheme;
import com.fearefull.wordsearch.easyadapter.MultiTypeAdapter;
import com.fearefull.wordsearch.features.settings.SettingsActivity;
import com.fearefull.wordsearch.model.Word;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fearefull.wordsearch.commons.AppConstants.MAXIMUM_COL_LENGTH;

public class MainMenuActivity extends FullscreenActivity {

    @BindView(R.id.rv) RecyclerView mRv;
    @BindView(R.id.searchBox) EditText searchBox;

    @Inject
    ViewModelFactory mViewModelFactory;
    MainMenuViewModel mViewModel;
    MultiTypeAdapter mAdapter;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);
        ((WordSearchApp) getApplication()).getAppComponent().inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainMenuViewModel.class);
        mViewModel.getOnGameThemeLoaded().observe(this, this::showGameThemeList);
        mViewModel.getOnReadyWords().observe(this, this::onReadyWords);


        mAdapter = new MultiTypeAdapter();
        mAdapter.addDelegate(
                GameTheme.class,
                R.layout.item_game_theme,
                (model, holder) -> holder.<TextView>find(R.id.textThemeName).setText(model.getName()),
                (model, view) -> Toast.makeText(MainMenuActivity.this, model.getName(), Toast.LENGTH_SHORT).show()
        );
        mAdapter.addDelegate(
                HistoryItem.class,
                R.layout.item_histories,
                (model, holder) -> {},
                (model, view) -> {
                    Intent i = new Intent(MainMenuActivity.this, GameHistoryActivity.class);
                    startActivity(i);
                }
        );

        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mViewModel.loadData();
    }

    public void showGameThemeList(List<GameTheme> gameThemes) {
        mAdapter.setItems(gameThemes);
        mAdapter.insertAt(0, new HistoryItem());
    }

    @OnClick(R.id.settings_button)
    public void onSettingsClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.new_game_btn)
    public void onNewGameClick() {
        String text = searchBox.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "لطفا جمله ای وارد کنید", Toast.LENGTH_SHORT).show();
        }
        else {
            Util.hideKeyboard(this);
            mViewModel.fetchRelations(text);
            progressdialog = new ProgressDialog(this);
            progressdialog.setMessage("لطفا منتظر بمانید");
            progressdialog.show();
        }
    }

    private void onReadyWords(List<Word> words) {
        progressdialog.dismiss();
        if (words.isEmpty()) {
            Toast.makeText(getApplicationContext(), "متاسفانه کلمات مشترکی پیدا نشد", Toast.LENGTH_LONG).show();
        }
        else {
            int maxLength = 0;
            for (Word word : words) {
                if (word.getString().length() > maxLength) {
                    maxLength = word.getShortString().length() + 1;
                }
            }
            Intent intent = new Intent(MainMenuActivity.this, GamePlayActivity.class);
            intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, maxLength);
            intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, Math.min(maxLength, MAXIMUM_COL_LENGTH));
            intent.putExtra(GamePlayActivity.EXTRA_DATA, (Serializable) words);
            startActivity(intent);
        }
    }
}
