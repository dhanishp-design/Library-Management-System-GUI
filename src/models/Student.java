package models;

public class Student {
    private int studentID;
    private String name;
    private String studentClass;
    private String contact;

    public Student() {
    }

    public Student(int studentID, String name, String studentClass, String contact) {
        this.studentID = studentID;
        this.name = name;
        this.studentClass = studentClass;
        this.contact = contact;
    }

    // Getters and Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", name='" + name + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
