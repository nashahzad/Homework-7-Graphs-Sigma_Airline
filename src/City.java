import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import latlng.LatLng;

/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

public class City
{
    private String city;
    private LatLng location;
    private int indexPos;
    private static int cityCount;

    /**
     * No argument constructor for City class.
     * <dt><b>Postconditions:</b></dd> A new City object has been made.
     */
    public City()
    {
        if(cityCount == 0)
            cityCount = 0;
        indexPos = cityCount;
        cityCount++;
    }

    /**
     * Constructor that takes in title of City.
     * @param city Name of the city.
     * <dt><b>Postconditions:</b></dd> A new City object has been made with a specified city name.
     */
    public City(String city)
    {
        if(cityCount == 0)
            cityCount = 0;
        indexPos = cityCount;
        cityCount++;

        this.city = city;
    }

    /**
     * Getter method for city name.
     * @return Returns city name.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for the city name.
     * @param city New name to set city to.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> City name has been changed.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for location of this city.
     * @return Returns location of city.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Setter method for the location.
     * @param location New location for this city.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> Location of this city has been changed.
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * Getter method for the index of this city.
     * @return Returns the index.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * Setter method for the index.
     * @param indexPos New index.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> The index has been changed.
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * Getter method for city count.
     * @return Returns the city count.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> City count has been changed.
     */
    public static int getCityCount() {
        return cityCount;
    }

    /**
     * Setter method for the city count.
     * @param cityCount New city count.
     * <dt><b>Preconditions:</b></dd> City object must already be instantiated.
     * <dt><b>Postconditions:</b></dd> City count has been changed.
     */
    public static void setCityCount(int cityCount) {
        City.cityCount = cityCount;
    }
}
