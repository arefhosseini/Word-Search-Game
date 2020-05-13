package com.fearefull.wordsearch.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ontology implements Serializable {
    @SerializedName("url")
    private String url;

    @SerializedName("label")
    private String label;

    @SerializedName("name")
    private String name;

    @SerializedName("children")
    private List<Ontology> children;

    public Ontology() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ontology> getChildren() {
        return children;
    }

    public void setChildren(List<Ontology> children) {
        this.children = children;
    }

    public List<Ontology> getAllOntology() {
        List<Ontology> allOntologies = new ArrayList<>();
        List<Ontology> selectedOntologies = new ArrayList<>();
        selectedOntologies.add(this);
        boolean isFinished = false;
        while (!isFinished) {
            List<Ontology> childOntologies = new ArrayList<>();
            for (Ontology ontology : selectedOntologies) {
                childOntologies.addAll(ontology.children);
            }
            allOntologies.addAll(selectedOntologies);
            selectedOntologies.clear();
            selectedOntologies.addAll(childOntologies);
            if (selectedOntologies.isEmpty()) {
                isFinished = true;
            }
        }
        return allOntologies;
    }
}
