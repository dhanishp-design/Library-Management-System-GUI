package models;

import java.sql.Date;

public class Issue {
    private int issueID;
    private int studentID;
    private int bookID;
    private Date issueDate;
    private Date returnDate;

    public Issue() {
    }

    public Issue(int issueID, int studentID, int bookID, Date issueDate, Date returnDate) {
        this.issueID = issueID;
        this.studentID = studentID;
        this.bookID = bookID;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getIssueID() {
        return issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueID=" + issueID +
                ", studentID=" + studentID +
                ", bookID=" + bookID +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
