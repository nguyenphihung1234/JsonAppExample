package slot8;

import java.util.List;

public class Student {
    private String id;
    private String name;
    private List<Subject> subjects;

    public Student(String id, String name, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", Name: ").append(name).append("\n");
        subjects.forEach(subject -> sb.append("  ").append(subject).append("\n"));
        return sb.toString();
    }
}
