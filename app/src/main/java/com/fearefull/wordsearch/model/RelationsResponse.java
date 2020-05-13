package com.fearefull.wordsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelationsResponse implements Serializable {

    @SerializedName("relations")
    private RelationResponse relations;

    public RelationsResponse() {}

    public RelationResponse getRelations() {
        return relations;
    }

    public void setRelations(RelationResponse relations) {
        this.relations = relations;
    }
}
