package mobile.dtd.vn.tourmapdemo;

/**
 * Created by hungson175 on 7/8/2016.
 */
public class PlaceItem {
    private double longt;
    private double lat;
    private String name;
    private double rating;
    private String thumbURL;

    public PlaceItem(double longt, double lat, String name, double rating, String thumbURL) {
        this.longt = longt;
        this.lat = lat;
        this.name = name;
        this.rating = rating;
        this.thumbURL = thumbURL;
    }

    public PlaceItem() {

    }

    public double getLat() {
        return lat;
    }

    public double getLongt() {
        return longt;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}
