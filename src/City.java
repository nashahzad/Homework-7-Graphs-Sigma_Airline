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

    public City()
    {
        if(cityCount == 0)
            cityCount = 0;
        indexPos = cityCount;
        cityCount++;
    }

    public City(String city)
    {
        if(cityCount == 0)
            cityCount = 0;
        indexPos = cityCount;
        cityCount++;

        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getIndexPos() {
        return indexPos;
    }

    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    public static int getCityCount() {
        return cityCount;
    }

    public static void setCityCount(int cityCount) {
        City.cityCount = cityCount;
    }
}
