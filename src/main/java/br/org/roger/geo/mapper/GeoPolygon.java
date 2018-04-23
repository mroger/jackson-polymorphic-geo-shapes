package br.org.roger.geo.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeoPolygon extends GeoShape {

    private String shapeType = "Polygon";

    private List<PolygonVertex> polygonPath;

    public GeoPolygon() { }

    @JsonCreator
    public GeoPolygon(
        @JsonProperty("shapeData") List<PolygonVertex> polygonPath) {
        this.polygonPath = polygonPath;
    }

    public List<PolygonVertex> getPolygonPath() {
        return polygonPath;
    }

    public void setPolygonPath(List<PolygonVertex> polygonPath) {
        this.polygonPath = polygonPath;
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
