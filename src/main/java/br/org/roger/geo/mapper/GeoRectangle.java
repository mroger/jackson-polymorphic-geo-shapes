package br.org.roger.geo.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeoRectangle extends GeoShape {

    private String shapeType = "Rectangle";
    private Bounds bounds;

    public GeoRectangle() { }

    @JsonCreator
    public GeoRectangle(
            @JsonProperty("shapeData") Bounds bounds) {
        this.bounds = bounds;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public String getShapeType() {
        return shapeType;
    }

    @Override
    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }
}
