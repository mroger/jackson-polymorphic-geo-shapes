package br.org.roger.geo.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "shapeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GeoRectangle.class, name = "Rectangle"),
        @JsonSubTypes.Type(value = GeoPolygon.class, name = "Polygon"),
        @JsonSubTypes.Type(value = GeoCircle.class, name = "Circle")})
public abstract class GeoShape {

    private Long id;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate initialDate;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate finalDate;

    private TransportEnum transport;
    private String shapeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public abstract String getShapeType();

    @JsonIgnore
    public abstract void setShapeType(String shapeType);

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public TransportEnum getTransport() {
        return transport;
    }

    public void setTransport(TransportEnum transport) {
        this.transport = transport;
    }
}
