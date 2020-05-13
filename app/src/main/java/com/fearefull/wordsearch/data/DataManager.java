package com.fearefull.wordsearch.data;

import com.fearefull.wordsearch.model.RelationsResponse;

import io.reactivex.Observable;

public interface DataManager {
    Observable<RelationsResponse> getRelations(String text);
}
