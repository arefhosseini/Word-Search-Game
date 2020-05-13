package com.fearefull.wordsearch.data.remote;

import com.fearefull.wordsearch.model.RelationsResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiHelper {
    /**
     * Get the list of the relationResponses from the API
     */
    @POST("/text/")
    Observable<RelationsResponse> getData(@Body RequestBody request);
}