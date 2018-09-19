package test.mobileapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Random;

public class DeliveryModel extends LoadMoreModel implements Serializable{

    private String id = null;
    private String description;
    private String imageUrl;
    private Location location;

    public DeliveryModel(String description, String imageUrl, Location location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public LatLng getPosition()
    {
        if (getLocation().isValid())
            return new LatLng(getLocation().getLat(), getLocation().getLng());
        return null;
    }

    public String getId() {
        if(id == null || id.equals(""))
        {
            Random random = new Random();
            id = String.valueOf(random.nextGaussian());
        }
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location implements Serializable
    {
        double lat;
        double lng;
        String address;

        public Location(double lat, double lng, String address) {
            this.lat = lat;
            this.lng = lng;
            this.address = address;
        }


        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public boolean isValid()
        {
            return !(lat == 0) && !(lng == 0);
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
