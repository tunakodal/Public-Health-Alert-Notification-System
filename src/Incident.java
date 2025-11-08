public class Incident {

    private int time;
    private String disease;
    private float latitude;
    private float longitude;
    private String location;
    private float infectionRate;
    private int populationAffected;
    private float severity;
    private String reportingAgency;
    private Position<Incident> severityPosition;

    public Incident(int time, String disease, float latitude, float longitude, String location, float infectionRate, int populationAffected, float severity, String reportingAgency) {
        this.time = time;
        this.disease = disease;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.infectionRate = infectionRate;
        this.populationAffected = populationAffected;
        this.severity = severity;
        this.reportingAgency = reportingAgency;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getInfectionRate() {
        return infectionRate;
    }

    public void setInfectionRate(float infectionRate) {
        this.infectionRate = infectionRate;
    }

    public int getPopulationAffected() {
        return populationAffected;
    }

    public void setPopulationAffected(int populationAffected) {
        this.populationAffected = populationAffected;
    }

    public float getSeverity() {
        return severity;
    }

    public void setSeverity(float severity) {
        this.severity = severity;
    }

    public String getReportingAgency() {
        return reportingAgency;
    }

    public void setReportingAgency(String reportingAgency) {
        this.reportingAgency = reportingAgency;
    }

    public Position<Incident> getSeverityPosition() {
        return severityPosition;
    }

    public void setSeverityPosition(Position<Incident> severityPosition) {
        this.severityPosition = severityPosition;
    }
}
