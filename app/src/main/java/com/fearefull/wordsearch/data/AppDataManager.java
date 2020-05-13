package com.fearefull.wordsearch.data;

import com.fearefull.wordsearch.data.remote.ApiHelper;
import com.fearefull.wordsearch.model.RelationsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@Singleton
public class AppDataManager implements DataManager {
    private ApiHelper apiHelper;

    @Inject AppDataManager(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public Observable<RelationsResponse> getRelations(String text){
        try {
            return apiHelper.getData(createRequestBody(text));
        } catch (JSONException e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    private RequestBody createRequestBody(String text) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("text", text);
        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }
}
