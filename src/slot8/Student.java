package slot8;

import java.util.ArrayList;
import java.util.List;

// Student class
class Student {
    private String id;
    private String name;
    private List<Subject> subjects;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    // Add a subject to the student's list of subjects
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    // Getters and setters
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
