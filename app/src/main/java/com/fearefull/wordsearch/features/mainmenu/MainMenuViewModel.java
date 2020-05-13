package com.fearefull.wordsearch.features.mainmenu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fearefull.wordsearch.commons.SingleLiveEvent;
import com.fearefull.wordsearch.data.DataManager;
import com.fearefull.wordsearch.data.GameThemeRepository;
import com.fearefull.wordsearch.data.OntologyDataSource;
import com.fearefull.wordsearch.model.EntityType;
import com.fearefull.wordsearch.model.NodeResponse;
import com.fearefull.wordsearch.model.Ontology;
import com.fearefull.wordsearch.model.RelationsResponse;
import com.fearefull.wordsearch.model.GameTheme;
import com.fearefull.wordsearch.model.Word;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MainMenuViewModel extends ViewModel {

    private DataManager dataManager;
    private CompositeDisposable compositeDisposable;
    private GameThemeRepository mGameThemeRepository;

    private MutableLiveData<List<GameTheme>> mOnGameThemeLoaded;

    private SingleLiveEvent<List<Word>> onReadyWords;

    private List<Ontology> ontologies;

    @Inject
    public MainMenuViewModel(GameThemeRepository gameThemeRepository, DataManager dataManager,
                             OntologyDataSource ontologyDataSource) {
        mGameThemeRepository = gameThemeRepository;
        this.dataManager = dataManager;
        compositeDisposable = new CompositeDisposable();
        mOnGameThemeLoaded = new MutableLiveData<>();
        onReadyWords = new SingleLiveEvent<>();

        Disposable disposable = Observable.create((ObservableOnSubscribe<List<Ontology>>) emitter -> {
            emitter.onNext(ontologyDataSource.getOntology().getAllOntology());
            emitter.onComplete();
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ontologies -> {
                    this.ontologies = ontologies;
                });

        compositeDisposable.add(disposable);
    }

    public void loadData() {
        mOnGameThemeLoaded.setValue(mGameThemeRepository.getGameThemes());
    }

    public LiveData<List<GameTheme>> getOnGameThemeLoaded() {
        return mOnGameThemeLoaded;
    }

    public void fetchRelations(String text) {
        //                    onFetchRelations.setValue(relationsResponse);
        Disposable disposable = dataManager.getRelations(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getRelationWords, throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()));

        compositeDisposable.add(disposable);
    }

    private void getRelationWords(RelationsResponse relationsResponse) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<List<Word>>) emitter -> {

            List<Word> words = new ArrayList<>();

            for (NodeResponse nodeResponse : relationsResponse.getRelations().getNodes()) {
                EntityType entityType = nodeResponse.getEntityType();
                String label = nodeResponse.getString();
                if (entityType == EntityType.RESOURCE) {
                    words.add(new Word(words.size(), label));
                    Log.d(EntityType.RESOURCE.toString(), label);
                }
                else if (entityType == EntityType.ONTOLOGY) {
                    String ontologyLabel = null;
                    for (Ontology ontology : ontologies) {
                        if (ontology.getName().equals(label)) {
                            ontologyLabel = ontology.getLabel()
                                    .replaceAll("_", " ")
                                    .trim();
                            break;
                        }
                    }
                    if (ontologyLabel != null) {
                        words.add(new Word(words.size(), ontologyLabel));
                        Log.d(EntityType.ONTOLOGY.toString(), ontologyLabel);
                    }
                }
            }

            emitter.onNext(words);
            emitter.onComplete();
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(words -> {
                    onReadyWords.setValue(words);
                });

        compositeDisposable.add(disposable);
    }

    public SingleLiveEvent<List<Word>> getOnReadyWords() {
        return onReadyWords;
    }
}
