/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import latlng.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class SigmaAir
{
    private ArrayList<City> cities;
    public static final int MAX_CITIES = 100;
    private double[][] connections;

    public SigmaAir()
    {
        cities = new ArrayList<City>();
        connections = new double[MAX_CITIES][MAX_CITIES];
    }

    public void addCity(String city)
    {
        City toAdd = new City(city);
        cities.add(toAdd);
    }

    public void addConnection(String cityFrom, String cityTo)
    {
        try{
            Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest;
            GeocodeResponse geocodeResponse;

            double latFrom, latTo, lngFrom, lngTo;

            geocoderRequest = new GeocoderRequestBuilder().setAddress(cityFrom).getGeocoderRequest();
            geocodeResponse = geocoder.geocode(geocoderRequest);
            latFrom = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
            lngFrom = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();

            geocoderRequest = new GeocoderRequestBuilder().setAddress(cityTo).getGeocoderRequest();
            geocodeResponse = geocoder.geocode(geocoderRequest);
            latTo = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
            lngTo = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();

            LatLng src = new LatLng(latFrom, lngFrom);
            LatLng dest = new LatLng(latTo, lngTo);

            City one = new City(cityFrom);
            City two = new City(cityTo);
            one.setLocation(src);
            two.setLocation(dest);

            cities.add(one);
            cities.add(two);

            double distance = LatLng.calculateDistance(src, dest);

            connections[one.getIndexPos()][two.getIndexPos()] = distance;

            connections[one.getIndexPos()][one.getIndexPos()] = 0;
            connections[two.getIndexPos()][two.getIndexPos()]= 0;

        }catch(IOException ex) {}
    }

    private City[] getCities(String cityFrom, String cityTo)
    {
        City[] citiesToReturn = new City[2];
        for(int i = 0; i < cities.size(); i++)
        {
            if(cities.get(i).getCity().equalsIgnoreCase(cityFrom))
                citiesToReturn[0] = cities.get(i);

            if(cities.get(i).getCity().equalsIgnoreCase(cityTo))
                citiesToReturn[1] = cities.get(i);
        }

        return citiesToReturn;
    }

    public void removeConnection(String cityFrom, String cityTo)
    {
        City[] connection = getCities(cityFrom, cityTo);

        connections[connection[0].getIndexPos()][connection[1].getIndexPos()] = Double.POSITIVE_INFINITY;

    }

    public String shortestPath(String cityFrom, String cityTo)
    {
        City[] connection = getCities(cityFrom, cityTo);

        if(connections[connection[0].getIndexPos()][connection[1].getIndexPos()] == Double.POSITIVE_INFINITY || connections[connection[0].getIndexPos()][connection[1].getIndexPos()] == 0)
            return "Connection from " + cityFrom + " to " + cityTo + " does not exist!";

        double deltaLatitude = Math.toRadians(connection[1].getLocation().getLat() - connection[0].getLocation().getLat());
        double deltaLongitude = Math.toRadians(connection[1].getLocation().getLng() - connection[0].getLocation().getLng());
        double lat1 = Math.toRadians(connection[0].getLocation().getLat());
        double lat2 = Math.toRadians(connection[1].getLocation().getLat());

        double a = Math.pow(Math.sin(deltaLatitude/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLongitude/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6371 * c;

        return "Shortest path from " + cityFrom + " to " + cityTo + " is " + d + ".";
    }
}
