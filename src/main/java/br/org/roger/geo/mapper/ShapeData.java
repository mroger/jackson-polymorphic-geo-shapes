package br.org.roger.geo.mapper;

class ShapeData {
    private Location center;
    private double radius;

    public ShapeData() {
    }

    public ShapeData(Location center, double radius) {
        this.center = center;
        this.radius = radius;
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
}