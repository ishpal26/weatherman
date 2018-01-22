package ishpallutfan.weatherman;

/**
 * Created by Lutfan on 22/1/2018.
 */

public class Data {
    private int id;
    private String datetime;
    private String longtitude;
    private String latitude;

    public Data(int id, String datetime, String longtitude, String latitude) {
        this.id = id;
        this.datetime = datetime;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
