public class Main {

    public LinkedPositionalList<Watcher> watcher_list = new LinkedPositionalList<>();
    public LinkedQueue<Incident> incident_queue = new LinkedQueue<>();
    public LinkedPositionalList<Incident> severity_order_list = new LinkedPositionalList<>();
    String watcherFile;
    String incidentFile;
    boolean processAll;

    public Main(String watcherFile, String incidentFile, boolean processAll) {
        this.watcherFile = watcherFile;
        this.incidentFile = incidentFile;
        this.processAll = processAll;
    }

    public void run(){

    }

}
