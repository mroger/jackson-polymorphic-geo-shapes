package br.org.roger.geo.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MapperTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerSubtypes(new NamedType(GeoRectangle.class, "Rectangle"));
        MAPPER.registerSubtypes(new NamedType(GeoPolygon.class, "Polygon"));
        MAPPER.registerSubtypes(new NamedType(GeoCircle.class, "Circle"));
    }

    @Test
    public void testDeserializePolygon() throws IOException {

        /*
        {
                "id": 1,
                "initialDate": "2018-04-20",
                "finalDate": "2018-04-22",
                "transport": "TRUCK",
                "shapeType": "Polygon",
                "shapeData": [
                    {
                        "lat": -1.16,
                        "lng": -46.22
                    },
                    {
                        "lat": -2.56,
                        "lng": -23.22
                    },
                    {
                        "lat": -1.16,
                        "lng": -23.22
                    },
                    {
                        "lat": -2.56,
                        "lng": -46.22
                    }
                ]
            }
         */

        final String json = "{\n" +
            "\"id\": 3,\n" +
            "\"initialDate\": \"2018-04-20\",\n" +
            "\"finalDate\": \"2018-04-22\",\n" +
            "\"transport\": \"TRUCK\",\n" +
            "\"shapeType\": \"Polygon\",\n" +
            "\"shapeData\": [\n" +
            "{\n" +
            "\"lat\": -1.16,\n" +
            "\"lng\": -46.22\n" +
            "},\n" +
            "{\n" +
            "\"lat\": -2.56,\n" +
            "\"lng\": -23.22\n" +
            "},\n" +
            "{\n" +
            "\"lat\": -1.16,\n" +
            "\"lng\": -23.22\n" +
            "},\n" +
            "{\n" +
            "\"lat\": -2.56,\n" +
            "\"lng\": -46.22\n" +
            "}\n" +
            "]\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();

        final GeoShape geoShape = MAPPER.readValue(json, GeoShape.class);

        final GeoPolygon polygon = (GeoPolygon) geoShape;
        assertEquals(3L, geoShape.getId().longValue());
        assertEquals(4, polygon.getPolygonPath().size());
        assertEquals("Polygon", geoShape.getShapeType());
        assertEquals("Polygon", polygon.getShapeType());
        assertEquals(TransportEnum.TRUCK, geoShape.getTransport());
        assertEquals(TransportEnum.TRUCK, polygon.getTransport());

    }

    @Test
    public void testSeserializePolygon() throws IOException {

        List<PolygonVertex> vertices = Arrays.asList(
            new PolygonVertex[] {
                new PolygonVertex(-1.3d, 2.7d),
                new PolygonVertex(-2.5d, 1.7d),
                new PolygonVertex(-3.2d, 3.6d),
                new PolygonVertex(-4.2, -3.3d)
            });

        LocalDate initialDate = LocalDate.of(2018, 4, 22);
        LocalDate finalDate = LocalDate.of(2018, 4, 26);
        String strInitialDate = formatter.format(initialDate);
        String strFinalDate = formatter.format(finalDate);

        GeoShape geoShape = new GeoPolygon();
        geoShape.setId(4L);
        geoShape.setInitialDate(initialDate);
        geoShape.setFinalDate(finalDate);
        geoShape.setTransport(TransportEnum.BIKE);
        ((GeoPolygon) geoShape).setPolygonPath(vertices);

        ObjectMapper mapper = new ObjectMapper();
        String result = new ObjectMapper().writeValueAsString(geoShape);

        String expected = "{\"shapeType\":\"Polygon\",\"id\":4,\"transport\":\"BIKE\",\"polygonPath\":[{\"lat\":-1.3,\"lng\":2.7},{\"lat\":-2.5,\"lng\":1.7},{\"lat\":-3.2,\"lng\":3.6},{\"lat\":-4.2,\"lng\":-3.3}],\"initialDate\":\"" + strInitialDate +"\",\"finalDate\":\"" + strFinalDate +"\"}";

        assertEquals(expected, result) ;
    }

    @Test
    public void testDeserializeRectangle() throws IOException {

        /*
        {
            "id": 1,
            "initialDate": "2018-04-20",
            "finalDate": "2018-04-22",
            "transport": "TRUCK",
            "shapeType": "Polygon",
            "shapeData": [
                {
                    "lat": -1.16,
                    "lng": -46.22
                },
                {
                    "lat": -2.56,
                    "lng": -23.22
                },
                {
                    "lat": -1.16,
                    "lng": -23.22
                },
                {
                    "lat": -2.56,
                    "lng": -46.22
                }
            ]
        }
         */

        final String json = "{\n" +
            "\"id\": 3,\n" +
            "\"initialDate\": \"2018-04-20\",\n" +
            "\"finalDate\": \"2018-04-22\",\n" +
            "\"transport\": \"MOTORCYCLE\",\n" +
            "\"shapeType\": \"Rectangle\",\n" +
            "\"shapeData\": {\n" +
            "    \"north\": 33.685,\n" +
            "    \"south\": 33.671,\n" +
            "    \"east\": -116.234,\n" +
            "    \"west\": -116.251\n" +
            "    }\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();

        final GeoShape geoShape = MAPPER.readValue(json, GeoShape.class);

        final GeoRectangle rectangle = (GeoRectangle) geoShape;
        assertEquals(3L, geoShape.getId().longValue());
        assertEquals(33.685d, rectangle.getBounds().getNorth(), 0.01);
        assertEquals(33.671d, rectangle.getBounds().getSouth(), 0.01);
        assertEquals(-116.234d, rectangle.getBounds().getEast(), 0.01);
        assertEquals(-116.251, rectangle.getBounds().getWest(), 0.01);
        assertEquals("Rectangle", geoShape.getShapeType());
        assertEquals("Rectangle", rectangle.getShapeType());
        assertEquals(TransportEnum.MOTORCYCLE, geoShape.getTransport());
        assertEquals(TransportEnum.MOTORCYCLE, rectangle.getTransport());

    }

    @Test
    public void testSeserializeRectangle() throws IOException {

        Bounds bounds = new Bounds(
            2.7d, -1.3d,1.7d, -2.5d);

        LocalDate initialDate = LocalDate.of(2018, 4, 22);
        LocalDate finalDate = LocalDate.of(2018, 4, 26);
        String strInitialDate = formatter.format(initialDate);
        String strFinalDate = formatter.format(finalDate);

        GeoShape geoShape = new GeoRectangle();
        geoShape.setId(4L);
        geoShape.setInitialDate(initialDate);
        geoShape.setFinalDate(finalDate);
        geoShape.setTransport(TransportEnum.CAR);
        ((GeoRectangle) geoShape).setBounds(bounds);

        ObjectMapper mapper = new ObjectMapper();
        String result = new ObjectMapper().writeValueAsString(geoShape);

        String expected = "{\"shapeType\":\"Rectangle\",\"id\":4,\"transport\":\"CAR\",\"bounds\":{\"north\":2.7,\"south\":-1.3,\"east\":1.7,\"west\":-2.5},\"initialDate\":\"" + strInitialDate + "\",\"finalDate\":\"" + strFinalDate +"\"}";

        assertEquals(expected, result) ;

    }

    @Test
    public void testDeserializeCircle() throws IOException {

        /*
        {
            "id": 1,
            "initialDate": "2018-04-20",
            "finalDate": "2018-04-22",
            "transport": "TRUCK",
            "shapeType": "Polygon",
            "shapeData": [
                {
                    "lat": -1.16,
                    "lng": -46.22
                },
                {
                    "lat": -2.56,
                    "lng": -23.22
                },
                {
                    "lat": -1.16,
                    "lng": -23.22
                },
                {
                    "lat": -2.56,
                    "lng": -46.22
                }
            ]
        }
         */

        final String json = "{\n" +
            "\"id\": 6,\n" +
            "\"initialDate\": \"2018-04-20\",\n" +
            "\"finalDate\": \"2018-04-22\",\n" +
            "\"transport\": \"VAN\",\n" +
            "\"shapeType\": \"Circle\",\n" +
            "\"shapeData\": {\n" +
            "\"center\": {\n" +
            "\"lat\": 33.685,\n" +
            "\"lng\": 33.671\n" +
            "},\n" +
            "\"radius\": 3.56\n" +
            "}\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();

        final GeoShape geoShape = MAPPER.readValue(json, GeoShape.class);

        final GeoCircle circle = (GeoCircle) geoShape;
        assertEquals(6L, geoShape.getId().longValue());
        assertEquals(33.685d, circle.getCenter().getLat(), 0.01);
        assertEquals(33.671d, circle.getCenter().getLng(), 0.01);
        assertEquals(3.56d, circle.getRadius(), 0.01);
        assertEquals("Circle", geoShape.getShapeType());
        assertEquals("Circle", circle.getShapeType());
        assertEquals(TransportEnum.VAN, geoShape.getTransport());
        assertEquals(TransportEnum.VAN, circle.getTransport());

    }

    @Test
    public void testSeserializeCircle() throws IOException {

        Location location = new Location(33.685d, 33.671d);

        LocalDate initialDate = LocalDate.of(2018, 4, 22);
        LocalDate finalDate = LocalDate.of(2018, 4, 26);
        String strInitialDate = formatter.format(initialDate);
        String strFinalDate = formatter.format(finalDate);

        GeoShape geoShape = new GeoCircle();
        geoShape.setId(4L);
        geoShape.setInitialDate(initialDate);
        geoShape.setFinalDate(finalDate);
        geoShape.setTransport(TransportEnum.VAN);
        ((GeoCircle) geoShape).setCenter(location);
        ((GeoCircle) geoShape).setRadius(3.56d);

        ObjectMapper mapper = new ObjectMapper();
        String result = new ObjectMapper().writeValueAsString(geoShape);

        String expected = "{\"shapeType\":\"Circle\",\"id\":4,\"transport\":\"VAN\",\"center\":{\"lat\":33.685,\"lng\":33.671},\"radius\":3.56,\"initialDate\":\"" + strInitialDate + "\",\"finalDate\":\"" + strFinalDate + "\"}";

        assertEquals(expected, result) ;

    }

}
