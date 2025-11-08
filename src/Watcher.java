public class Watcher {

    private String name;
    private float latitude;
    private float longitude;
    private int time;

    public Watcher(String name, float latitude, float longitude, int time) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
