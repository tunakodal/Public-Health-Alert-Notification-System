# Public Health Alert Notification System

> **Course homework: BIL212 - HW1.** This repository contains an assignment submission, not a production library.

A Java program that processes a timeline of health incident reports and watcher commands, and notifies watchers located near new incidents. The data structures (linked list, positional list, queue) are implemented from scratch.

## How It Works

- Two input files are read in parallel and replayed along a simulated clock: one with watcher commands, one with incident reports.
- **Watchers** can be added or deleted, and can run queries: the most severe recent incident, incidents of a given disease, or incidents within a radius of a point.
- **Incidents** enter an incident queue (incidents older than 6 time units are dropped) and are also kept in a list ordered by severity. When an incident arrives, nearby watchers are notified.

## Repository Structure

```
.
├── src/
│   ├── HealthAlertNotification.java   entry point, argument handling
│   ├── Main.java                      simulation loop and command handling
│   ├── Incident.java, Watcher.java    data classes
│   ├── LinkedPositionalList.java      positional list (and its interface)
│   ├── LinkedQueue.java               queue (and its interface)
│   └── SinglyLinkedList.java, Position.java, PositionalList.java, Queue.java
├── data/
│   ├── input/             sample watcher and incident files
│   └── expected_output/   reference output for each sample pair
└── README.md
```

## Usage

```bash
cd src
javac *.java
java HealthAlertNotification ../data/input/watcher_1.txt ../data/input/incident_1.txt
```

Add the `--all` flag before the file names to also print a line each time an incident is inserted into the incident queue:

```bash
java HealthAlertNotification --all ../data/input/watcher_1.txt ../data/input/incident_1.txt
```

Compare the printed result with `data/expected_output/output_1.txt`.
