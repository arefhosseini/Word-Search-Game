package com.fearefull.wordsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdgeResponse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("labels")
    private List<String> labels;

    @SerializedName("from")
    private int from;

    @SerializedName("to")
    private int to;

    public EdgeResponse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
