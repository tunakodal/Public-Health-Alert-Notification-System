import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class Main {

    public static LinkedPositionalList<Watcher> watcher_list = new LinkedPositionalList<>();
    public static LinkedQueue<Incident> incident_queue = new LinkedQueue<>();
    public static LinkedPositionalList<Incident> severity_order_list = new LinkedPositionalList<>();
    public String watcherFile;
    public String incidentFile;
    public static boolean processAll;

    public Main(String watcherFile, String incidentFile, boolean processAll) {
        this.watcherFile = watcherFile;
        this.incidentFile = incidentFile;
        Main.processAll = processAll;
    }

    public void run(){
        try {
            BufferedReader watcherReader = new BufferedReader(new FileReader(watcherFile));
            BufferedReader incidentReader = new BufferedReader(new FileReader(incidentFile));
            int current_time = 0;

            while(watcherReader.readLine() != null || incidentReader.readLine() != null){
                String watcherLine = watcherReader.readLine();
                String incidentLine = incidentReader.readLine();
                int watcherTime = Integer.parseInt(watcherLine.substring(0, watcherLine.indexOf(' ')));
                int incidentTime = Integer.parseInt(incidentLine.substring(0, incidentLine.indexOf(' ')));

                if (watcherLine != null && watcherTime == current_time){
                    String[] watcherArgs = watcherLine.trim().split("\\s+");
                    switch (watcherArgs[1]) {
                        case "add":
                            createWatcher(watcherLine);
                            break;
                        case "delete":
                            deleteWatcher(watcherLine);
                            break;
                        case "query-highest":
                            queryHighest();
                            break;
                        case "query-disease":
                            queryDisease(watcherArgs[2]);
                            break;
                        case "query-region":
                            float latitude = Float.parseFloat(watcherArgs[2]);
                            float longitude = Float.parseFloat(watcherArgs[3]);
                            int radius = Integer.parseInt(watcherArgs[4]);
                            queryRegion(latitude, longitude, radius);
                            break;
                    }
                }

                if (incidentLine != null && incidentTime == current_time){
                    Incident incident = createIncident(incidentLine);
                    updateIncidentQueue(current_time);
                    addIncident(incident);
                    notifyWatchers(incident);
                }
                current_time++;
            }

        }
        catch (IOException e){
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    public static void createWatcher(String line){
        String[] arguments = line.trim().split("\\s+");
        int time = Integer.parseInt(arguments[0]);
        float latitude = Float.parseFloat(arguments[2]);
        float longitude = Float.parseFloat(arguments[3]);
        String name = arguments[4];
        Watcher watcher = new Watcher(name, latitude, longitude, time);
        watcher_list.addLast(watcher);
        System.out.println(name + " is added to the watcher-list");
    }

    public static void deleteWatcher(String line) {
        String[] arguments = line.trim().split("\\s+");
        String name = arguments[2];
        for (Position<Watcher> p : watcher_list.positions()){
            if (p.getElement().getName().equals(name)) {
                System.out.println(name + " is removed from the watcher-list");
                watcher_list.remove(p);
                return;
            }
        }
    }

    public static void queryHighest(){
        if (severity_order_list.isEmpty()) {
            System.out.println("No incidents");
        }
        else{
            Incident highest = severity_order_list.first().getElement();
            System.out.println("Most severe health incident in past 6 hours:");
            System.out.println("(Disease: " + highest.getDisease() + ") Severity: " + highest.getSeverity() + " at " + highest.getLocation());
        }
    }

    public static void queryDisease(String disease){
        for (Position<Incident> p : severity_order_list.positions()) {
            if (p.getElement().getDisease().equals(disease)) {
                Incident incident = p.getElement();
                System.out.println("(Disease: " + incident.getDisease() + ") Severity: " + incident.getSeverity() + " at " + incident.getLocation());
            }
        }
    }

    public static void queryRegion(float latitude, float longitude, int radius){
        for (Position<Incident> p : severity_order_list.positions()) {
            Incident incident = p.getElement();
            float lat2 = incident.getLatitude();
            float lon2 = incident.getLongitude();
            if (isInRegion(latitude, longitude, lat2, lon2, radius)) {
                System.out.println("(Disease: " + incident.getDisease() + ") Severity: " + incident.getSeverity() + " at " + incident.getLocation());
            }
        }

    }

    public static boolean isInRegion(float lat1, float lon1, float lat2, float lon2, int radius){
        float x = lat1 - lat2;
        float y = lon1 - lon2;
        double distance = Math.sqrt(x*x + y*y);
        return distance <= radius;
    }


    //========= Incident Methods =========

    public static Incident createIncident(String line) {
        String[] arguments = line.trim().split("\\s+");

        int time = Integer.parseInt(arguments[0]);
        String disease = arguments[1];
        float latitude = Float.parseFloat(arguments[2]);
        float longitude = Float.parseFloat(arguments[3]);
        String location = arguments[4];
        float infectionRate = Float.parseFloat(arguments[5]);
        int populationAffected = Integer.parseInt(arguments[6]);
        float severity = Float.parseFloat(arguments[7]);
        String reportingAgency = arguments[8];

        if (processAll) {
            System.out.println("(Disease: " + disease + ") at " + location + " is inserted into incident-queue");
        }

        return new Incident(
                time, disease, latitude, longitude,
                location, infectionRate, populationAffected,
                severity, reportingAgency
        );
    }

    public static void updateIncidentQueue(int currentTime) {
        if (!incident_queue.isEmpty() && currentTime - incident_queue.first().getTime() >= 6) {
            updateSeverityOrderList(incident_queue.first().getSeverityPosition());
            incident_queue.dequeue();
        }
    }

    private static void updateSeverityOrderList(Position<Incident> pos) {
        severity_order_list.remove(pos);
    }

    public static void addIncident(Incident incident) {

        incident_queue.enqueue(incident);

        Position<Incident> newPos = null;
        if (severity_order_list.isEmpty()) {
            newPos = severity_order_list.addFirst(incident);
        } else {
            for (Position<Incident> currentPos : severity_order_list.positions()) {
                if (incident.getSeverity() > currentPos.getElement().getSeverity()) {
                    newPos = severity_order_list.addBefore(currentPos, incident);
                    break;
                }
            }
            if (newPos == null) {
                newPos = severity_order_list.addLast(incident);
            }
        }
        incident.setSeverityPosition(newPos);
    }

    public static void notifyWatchers(Incident incident){
        for (Position<Watcher> p : watcher_list.positions()) {
            Watcher watcher = p.getElement();
            float x = watcher.getLatitude() - incident.getLatitude();
            float y = watcher.getLongitude() - incident.getLongitude();
            double distance = Math.sqrt(x*x + y*y);
            if (distance <= 2 * incident.getSeverity()) {
                System.out.println("(Disease: " + incident.getDisease() + ") at " + incident.getLocation() + " is close to " + watcher.getName());
            }
        }
    }



}
