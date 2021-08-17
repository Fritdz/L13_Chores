package sg.edu.rp.c346.id20033454.chores;

import java.io.Serializable;
import java.util.Calendar;

public class Chores implements Serializable {

    private int id;
    private String name;
    private String details;
    private String day;
    private String time;
    private int importance;

    public Chores(String name, String details, String day, String time, int importance) {
        this.name = name;
        this.details = details;
        this.day = day;
        this.time = time;
        this.importance = importance;
    }

    public Chores(int id, String name, String details, String day, String time, int importance) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.day = day;
        this.time = time;
        this.importance = importance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    @Override
    public String toString() {
        return "Chores{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", day='" + day + '\'' +
                ", time=" + time +
                ", importance=" + importance +
                '}';
    }
}
