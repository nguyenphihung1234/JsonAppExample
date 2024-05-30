package slot8;

import java.util.List;

public class Subject {
    private String id;
    private String name;
    private List<Integer> scores;

    public Subject(String id, String name, List<Integer> scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Subject ID: " + id + ", Name: " + name + ", Scores: " + scores;
    }
}
