public class HealthAlertNotification {

    public static void main(String[] args) {
        String watcherFile;
        String incidentFile;
        boolean processAll = false;

        if(args.length == 2){
            watcherFile = args[0];
            incidentFile = args[1];

        } else if (args.length == 3 && args[0].equals("--all")) {
            processAll = true;
            watcherFile = args[1];
            incidentFile = args[2];

        } else {
            System.out.println("Usage: java HealthAlertNotification <watcher_file> <incident_file>");
            return;
        }

        Main app = new Main(watcherFile, incidentFile, processAll);
        app.run();
    }

}
