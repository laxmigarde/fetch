package com.fetch.receipt.processor.data.response;

import com.fasterxml.jackson.annotation.JsonView;

public class Response {

    public interface IdView {}
    public interface PointView {}

    @JsonView(IdView.class)
    private String id;

    @JsonView(PointView.class)
    private int points;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
