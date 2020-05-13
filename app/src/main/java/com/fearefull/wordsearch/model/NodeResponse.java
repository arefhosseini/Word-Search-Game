package com.fearefull.wordsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeResponse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("label")
    private String label;

    public NodeResponse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public EntityType getEntityType() {
        if (label.contains("fkgr")) {
            return EntityType.RESOURCE;
        }
        else if (label.contains("fkgo")) {
            return EntityType.ONTOLOGY;
        }
        return EntityType.UNKNOWN;
    }

    public String getString() {
        EntityType entityType = getEntityType();
        if (entityType == EntityType.RESOURCE) {
            return label.replace("fkgr:", "").replaceAll("_", " ").trim();
        }
        else if (entityType == EntityType.ONTOLOGY) {
            return label.replace("fkgo:", "").replaceAll("_", " ").trim();
        }
        return null;
    }
}
