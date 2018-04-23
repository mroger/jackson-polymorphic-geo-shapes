package br.org.roger.geo.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

public class GeoCircle extends GeoShape {

    private String shapeType = "Circle";
    private Location center;
    private double radius;

    public GeoCircle() { }

    @JsonCreator
    public GeoCircle(
            @JsonProperty("shapeData") ShapeData shapeData) {
        this.center = shapeData.getCenter();
        this.radius = shapeData.getRadius();
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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
