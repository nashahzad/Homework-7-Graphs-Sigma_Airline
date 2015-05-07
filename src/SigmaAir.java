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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class SigmaAir implements Serializable
{
    //private static final long serialVersionUID = 7526471155622776147L;
    private ArrayList<City> cities;
    private static final int MAX_CITIES = 100;
    private double[][] connections;

    /**
     * No argument constructor for SigmaAir
     * <dt><b>Postconditions:</b></dd> New SigmaAir object has been made.
     */
    public SigmaAir()
    {
        cities = new ArrayList<City>();
        connections = new double[MAX_CITIES][MAX_CITIES];
    }

    /**
     * Method to add a city into SigmaAir
     * @param city Name of the city to add.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> A new city has been added.
     */
    public void addCity(String city)
    {
        try {
            Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest;
            GeocodeResponse geocodeResponse;
            double latFrom, lngFrom;

            geocoderRequest = new GeocoderRequestBuilder().setAddress(city).getGeocoderRequest();
            geocodeResponse = geocoder.geocode(geocoderRequest);
            latFrom = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
            lngFrom = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
            LatLng src = new LatLng(latFrom, lngFrom);
            City toAdd = new City(city);
            toAdd.setLocation(src);

            cities.add(toAdd);

            connections[toAdd.getIndexPos()][toAdd.getIndexPos()] = 0;

            System.out.println(toAdd.getCity() + " has been added: (" + latFrom + ", " + lngFrom + ")");
        }catch(IOException ex)
        {
            System.out.println("City does not exist.");
        }
    }

    /**
     * Adds a connection between two cities.
     * @param cityFrom Source city.
     * @param cityTo Destination city.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated and both cities must have already been added.
     * <dt><b>Postconditions:</b></dd> A new connection has been added into the adjacency matrix.
     */
    public void addConnection(String cityFrom, String cityTo)
    {

            /*
            City one = new City(cityFrom);
            City two = new City(cityTo);
            one.setLocation(src);
            two.setLocation(dest);

            cities.add(one);
            cities.add(two);
            */

            int[] connection = this.getCities(cityFrom, cityTo);
            if(connection[0] == -1 || connection[1] == -1)
            {
                System.out.println("Cannot add connection one of the cities was not added in first.");
                return;
            }

        Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest;
        GeocodeResponse geocodeResponse;

        double latFrom, latTo, lngFrom, lngTo;

        try {
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


            double distance = LatLng.calculateDistance(src, dest);

            connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] = distance;

            /*
            connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[0]).getIndexPos()] = 0;
            connections[cities.get(connection[1]).getIndexPos()][cities.get(connection[1]).getIndexPos()] = 0;
            */

            System.out.println(cityFrom + " --> " + cityTo + " added: " + distance);
        }catch(IOException ex) {}

    }

    /**
     * Private method to get corresponding index of the cities in the arraylist.
     * @param cityFrom Source city.
     * @param cityTo Destination city.
     * @return Returns array of the index values.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
    private int[] getCities(String cityFrom, String cityTo)
    {
        int[] citiesToReturn = new int[2];
        citiesToReturn[0] = -1;
        citiesToReturn[1] = -1;
        for(int i = 0; i < cities.size(); i++)
        {
            if(cities.get(i).getCity().equalsIgnoreCase(cityFrom))
                citiesToReturn[0] = i;

            if(cities.get(i).getCity().equalsIgnoreCase(cityTo))
                citiesToReturn[1] = i;
        }

        return citiesToReturn;
    }

    /**
     * Method to remove a connection from matrix.
     * @param cityFrom Source city.
     * @param cityTo Destination city.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> The connection has been removed and value has been set to POSITIVE_INFINITY in its place.
     */
    public void removeConnection(String cityFrom, String cityTo)
    {
        int[] connection = getCities(cityFrom, cityTo);

        connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] = Double.POSITIVE_INFINITY;

        System.out.println("Connection from " + cityFrom + " to " + cityTo + " has been removed!");
    }


    /**
     * Method to print out all the cities that have been added.
     * @param comp Comparator on which the order of the cities will be printed out on.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
    public void printAllCities(String comp) // prints all cities in the order based on the given Comprator
    {
        System.out.printf("%-28s%-19s%-19s\n", "City Name", "Latitude", "Longitude");
        if(!cities.isEmpty()) {
            if (comp.equalsIgnoreCase("name"))
                printAllCitiesByName();

            if (comp.equalsIgnoreCase("lat"))
                printAllCitiesByLat();

            if (comp.equalsIgnoreCase("lng"))
                printAllCitiesByLng();
        }
        else
        {
            System.out.println("List of cities is empty, nothing to print out at the moment.");
        }
    }

    /**
     * Method to print out cities by name in alphabetical order.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
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
            System.out.printf("%-28s%-19f%-19f\n", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    /**
     * Prints out order in ascending latitude.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
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
            System.out.printf("%-28s%-19f%-19f\n", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    /**
     * Prints out in order of ascending longitude.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
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
            System.out.printf("%-28s%-19f%-19f\n", toSort[i].getCity(), toSort[i].getLocation().getLat(), toSort[i].getLocation().getLng());
        }
    }

    /**
     * Prints out all of the connections.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
    public void printAllConnections()
    {
        System.out.printf("%-40s %s\n", "Route", "Distance");
        for(int u = 0; u < MAX_CITIES; u++)
        {
            for(int v = 0; v < MAX_CITIES; v++)
            {
                if(connections[u][v] != 0 && connections[u][v] != Double.POSITIVE_INFINITY)
                {
                    System.out.printf("%-40s %f\n", cities.get(u).getCity() + " --> " + cities.get(v).getCity(), connections[u][v]);
                }
            }
        }
    }

    /**
     * Loads in cities from a text file.
     * @param fileName Text file name.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> All of the cities have been loaded in if any from the specified text file if it existed.
     */
    public void loadAllCities(String fileName)
    {
        try
        {
            Scanner reader = new Scanner(new File(fileName));
            String name = "";
            while(reader.hasNext())
            {
                name = reader.nextLine();

                this.addCity(name);
            }

            reader.close();

        }catch(IOException ex)
        {
            System.out.println("File was not found.");
        }
    }

    /**
     * Loads in connections from a text file.
     * @param fileName Text file name.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> All of the connections have been loaded in if any from the specified text file if it existed.
     */
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

            reader.close();

        }catch(IOException ex)
        {
            System.out.println("File was not found.");
        }
    }

    /**
     * Method to copy over adjacency matrix
     * @return Returns copied matrix.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
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

    /**
     * Method that should print out the shortes path and distance between two cities based on connections and cities that have been added.
     * @param cityFrom Source city.
     * @param cityTo Destination city.
     * @return Returns the shortest path if it exists.
     * <dt><b>Preconditions:</b></dd> The SigmaAir object must already be instantiated.
     */
    public String shortestPath(String cityFrom, String cityTo)
    {
        int[] connection = getCities(cityFrom, cityTo);

        /*if(connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] == Double.POSITIVE_INFINITY || connections[cities.get(connection[0]).getIndexPos()][cities.get(connection[1]).getIndexPos()] == 0)
            return "Connection from " + cityFrom + " to " + cityTo + " does not exist!"; */

        double[][] dist = new double[MAX_CITIES][MAX_CITIES];
        City[][] next = new City[MAX_CITIES][MAX_CITIES];

        //dist = this.floydMatrix();

        for(int u = 0; u < cities.size(); u++){
            for(int v = 0; v < cities.size(); v++){
                dist[u][v] = connections[u][v];
                next[u][v] = cities.get(v);
            }
        }

        for(int k = 1; k < MAX_CITIES; k++){
            for(int i = 1; i < MAX_CITIES; i++){
                for(int j = 1; j < MAX_CITIES; j++){
                    if(dist[i][j] + dist[k][j] < dist[i][j])
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        City from = cities.get(connection[0]);
        City to = cities.get(connection[1]);
        String path = "";
        if(from != null && to != null)
        {
            if(next[from.getIndexPos()][to.getIndexPos()] == null)
                return "There is no shortest path.";

            int u = from.getIndexPos(), v = to.getIndexPos();
            path += cities.get(u).getCity();

            while(u != v)
            {
                u = next[u][v].getIndexPos();
                path = path + " --> " + cities.get(u).getCity();
            }

            return path + "\nDistance: " + dist[u][v];
        }

        return dist[connection[0]][connection[1]] + "\n" + next[connection[0]][connection[1]] + "\n";
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

        //return "";
    }
}
