package ng.riby.androidtest.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double startXCoordinate;
    private double startYCoordinate;
    private double endXCoordinate;
    private double endYCoordinate;
    private double distance;


    public TaskEntry(int id, double startXCoordinate, double startYCoordinate, double endXCoordinate, double endYCoordinate, double distance) {
        this.id = id;
        this.startXCoordinate = startXCoordinate;
        this.startYCoordinate = startYCoordinate;
        this.endXCoordinate = endXCoordinate;
        this.endYCoordinate = endYCoordinate;
        this.distance = distance;
    }



    @Ignore
    public TaskEntry(double startXCoordinate, double startYCoordinate, double endXCoordinate, double endYCoordinate, double distance) {
        this.startXCoordinate = startXCoordinate;
        this.startYCoordinate = startYCoordinate;
        this.endXCoordinate = endXCoordinate;
        this.endYCoordinate = endYCoordinate;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStartXCoordinate() {
        return startXCoordinate;
    }

    public void setStartXCoordinate(double startXCoordinate) {
        this.startXCoordinate = startXCoordinate;
    }

    public double getStartYCoordinate() {
        return startYCoordinate;
    }

    public void setStartYCoordinate(double startYCoordinate) {
        this.startYCoordinate = startYCoordinate;
    }

    public double getEndXCoordinate() {
        return endXCoordinate;
    }

    public void setEndXCoordinate(double endXCoordinate) {
        this.endXCoordinate = endXCoordinate;
    }

    public double getEndYCoordinate() {
        return endYCoordinate;
    }

    public void setEndYCoordinate(double endYCoordinate) {
        this.endYCoordinate = endYCoordinate;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
