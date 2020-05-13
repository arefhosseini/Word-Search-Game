package com.fearefull.wordsearch.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RelationResponse implements Serializable {

    @SerializedName("nodes")
    private List<NodeResponse> nodes;

    @SerializedName("edges")
    private List<EdgeResponse> edges;

    public RelationResponse() {}

    public List<NodeResponse> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeResponse> nodes) {
        this.nodes = nodes;
    }

    public List<EdgeResponse> getEdges() {
        return edges;
    }

    public void setEdges(List<EdgeResponse> edges) {
        this.edges = edges;
    }
}
