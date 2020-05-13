package com.fearefull.wordsearch.data.json;

import android.content.Context;
import android.content.res.AssetManager;

import com.fearefull.wordsearch.data.OntologyDataSource;
import com.fearefull.wordsearch.model.Ontology;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class OntologyJsonDataSource implements OntologyDataSource {

    private static final String ASSET_ONTOLOGY_BANK_FILE = "ontology.json";

    private AssetManager assetManager;

    @Inject
    public OntologyJsonDataSource(Context context) {
        assetManager = context.getAssets();
    }

    @Override
    public Ontology getOntology() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(loadJSONFromAsset(), Ontology.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Ontology();
    }

    public String loadJSONFromAsset() throws IOException {
        String json = null;
        InputStream is = assetManager.open(ASSET_ONTOLOGY_BANK_FILE);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        return json;
    }
}
