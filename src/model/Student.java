package model;

public class Student {
    private String studentID;
    private String name;
    private String programme;
    private double marks;

    public Student(String studentID, String name, String programme, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getProgramme() {
        return programme;
    }

    public double getMarks() {
        return marks;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID
                + ", Name: " + name
                + ", Programme: " + programme
                + ", Marks: " + marks;
    }
}
