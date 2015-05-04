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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

            /*
            City one = new City(cityFrom);
            City two = new City(cityTo);
            one.setLocation(src);
            two.setLocation(dest);

            cities.add(one);
            cities.add(two);
            */

            int[] connection = getCities(cityFrom, cityTo);

            double distance = LatLng.calculateDistance(src, dest);

            connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] = distance;

            connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[0]).getIndexPos()] = 0;
            connections[cities.get(connection[1]).getIndexPos()][cities.get(connection[1]).getIndexPos()]= 0;

            System.out.println(cityFrom + " --> " + cityTo + " added: " + distance);

        }catch(IOException ex) {}
    }

    private int[] getCities(String cityFrom, String cityTo)
    {
        int[] citiesToReturn = new int[2];
        for(int i = 0; i < cities.size(); i++)
        {
            if(cities.get(i).getCity().equalsIgnoreCase(cityFrom))
                citiesToReturn[0] = i;

            if(cities.get(i).getCity().equalsIgnoreCase(cityTo))
                citiesToReturn[1] = i;
        }

        return citiesToReturn;
    }

    public void removeConnection(String cityFrom, String cityTo)
    {
        int[] connection = getCities(cityFrom, cityTo);

        connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] = Double.POSITIVE_INFINITY;

        System.out.println("Connection from " + cityFrom + " to " + cityTo + " has been removed!");
    }

    private double[][] floydMatrix()
    {
        double[][] floyd = new double[MAX_CITIES][MAX_CITIES];

        for(int u = 0; u < MAX_CITIES; u++)
        {
            for(int v = 0; v < MAX_CITIES; v++)
            {
                floyd[u][v] = connections[u][v];
            }
        }

        return floyd;
    }

    public String shortestPath(String cityFrom, String cityTo)
    {
        int[] connection = getCities(cityFrom, cityTo);

        if(connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] == Double.POSITIVE_INFINITY || connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] == 0)
            return "Connection from " + cityFrom + " to " + cityTo + " does not exist!";

        double[][] floyd = floydMatrix();
        City[][] city = new City[MAX_CITIES][MAX_CITIES];

        String path = "";




        /*
        double deltaLatitude = Math.toRadians(connection[1].getLocation().getLat() - connection[0].getLocation().getLat());
        double deltaLongitude = Math.toRadians(connection[1].getLocation().getLng() - connection[0].getLocation().getLng());
        double lat1 = Math.toRadians(connection[0].getLocation().getLat());
        double lat2 = Math.toRadians(connection[1].getLocation().getLat());

        double a = Math.pow(Math.sin(deltaLatitude/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLongitude/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6371 * c;

        return "Shortest path from " + cityFrom + " to " + cityTo + " is " + d + ".";
        */

        return "";
    }

    public void printAllCities(String comp) // prints all cities in the order based on the given Comprator
    {
        System.out.printf("%28s%19s%19s", "City Name", "Latitude", "Longitude");
        if(comp.equalsIgnoreCase("name"))
            printAllCitiesByName();

        if(comp.equalsIgnoreCase("lat"))
            printAllCitiesByLat();

        if(comp.equalsIgnoreCase("lng"))
            printAllCitiesByLat();
    }

    private void printAllCitiesByName()
    {
        City[] toSort = new City[cities.size()];
        NameComparator comp = new NameComparator();
        for(int i = 0; i < cities.size(); i++)
        {
            toSort[i] = cities.get(i);
        }

        int j;
        boolean flag = true;
        City temp;

        while ( flag )
        {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < toSort.length -1;  j++ )
            {
                if (comp.compare(toSort[j], toSort[j+1]) == -1)
                {
                    temp = toSort[ j ];
                    toSort[ j ] = toSort[ j+1 ];
                    toSort[ j+1 ] = temp;
                    flag = true;
                }
            }
        }

        for(int i = 0; i < toSort.length; i++)
        {
            System.out.printf("%28s%19f%19f", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    private void printAllCitiesByLat()
    {
        City[] toSort = new City[cities.size()];
        LatComparator comp = new LatComparator();
        for(int i = 0; i < cities.size(); i++)
        {
            toSort[i] = cities.get(i);
        }

        int j;
        boolean flag = true;
        City temp;

        while ( flag )
        {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < toSort.length -1;  j++ )
            {
                if (comp.compare(toSort[j], toSort[j+1]) == -1)
                {
                    temp = toSort[ j ];
                    toSort[ j ] = toSort[ j+1 ];
                    toSort[ j+1 ] = temp;
                    flag = true;
                }
            }
        }

        for(int i = 0; i < toSort.length; i++)
        {
            System.out.printf("%28s%19f%19f", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    private void printAllCitiesByLng()
    {
        City[] toSort = new City[cities.size()];
        LngComparator comp = new LngComparator();
        for(int i = 0; i < cities.size(); i++)
        {
            toSort[i] = cities.get(i);
        }

        int j;
        boolean flag = true;
        City temp;

        while ( flag )
        {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < toSort.length -1;  j++ )
            {
                if (comp.compare(toSort[j], toSort[j+1]) == -1)
                {
                    temp = toSort[ j ];
                    toSort[ j ] = toSort[ j+1 ];
                    toSort[ j+1 ] = temp;
                    flag = true;
                }
            }
        }

        for(int i = 0; i < toSort.length; i++)
        {
            System.out.printf("%28s%19f%19f", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    public void printAllConnections()
    {
        for(int u = 0; u < MAX_CITIES; u++)
        {
            for(int v = 0; v < MAX_CITIES; v++)
            {
                if(connections[u][v] != 0 && connections[u][v] != Double.POSITIVE_INFINITY)
                {
                    System.out.printf("%20s-->%20s%30f\n", cities.get(u).getCity(), cities.get(v).getCity(), connections[u][v]);
                }
            }
        }
    }

    public void loadAllCities(String fileName)
    {
        try
        {
            Scanner reader = new Scanner(new File(fileName));
            while(reader.hasNext())
            {
                cities.add(new City(reader.nextLine()));
            }

        }catch(IOException ex)
        {
            System.out.println("File was not found.");
        }
    }

    public void loadAllConnections(String fileName)
    {
        try{
            Scanner reader = new Scanner(new File(fileName));
            String split = "";
            String[] citySplit = new String[2];

            while(reader.hasNext())
            {
                split = reader.nextLine();
                citySplit = split.split(",");
                this.addConnection(citySplit[0], citySplit[1]);
            }

        }catch(IOException ex)
        {
            System.out.println("File was not found.");
        }
    }
}
