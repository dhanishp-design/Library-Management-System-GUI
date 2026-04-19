package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainGUI extends JFrame {

    private JTabbedPane tabbedPane;

    // Book Tab Components
    private DefaultTableModel bookTableModel;
    private JTable bookTable;

    // Student Tab Components
    private DefaultTableModel studentTableModel;
    private JTable studentTable;

    // Issue/Return Tab Components
    private DefaultTableModel issueTableModel;
    private JTable issueTable;

    public MainGUI() {
        setTitle("Library Management System - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("Manage Books", createBookTab());
        tabbedPane.addTab("Manage Students", createStudentTab());
        tabbedPane.addTab("Issue Book", createIssueTab());
        tabbedPane.addTab("Issue History & Return", createReturnTab());

        add(tabbedPane);

        // Initial Data Load
        loadBooksData("");
        loadStudentsData();
        loadIssuesData();
        
        // Add change listener to refresh tables when switching tabs
        tabbedPane.addChangeListener(e -> {
            loadBooksData("");
            loadStudentsData();
            loadIssuesData();
        });
    }

    // ==========================================
    // 1. MANAGE BOOKS TAB
    // ==========================================
    private JPanel createBookTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (Top)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add / Search Book"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Title:"), gbc);
        JTextField txtTitle = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(txtTitle, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Author:"), gbc);
        JTextField txtAuthor = new JTextField(15);
        gbc.gridx = 3;
        formPanel.add(txtAuthor, gbc);

        JButton btnAdd = new JButton("Add Book");
        gbc.gridx = 4;
        formPanel.add(btnAdd, gbc);

        JButton btnSearch = new JButton("Search Title");
        gbc.gridx = 5;
        formPanel.add(btnSearch, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Table Panel (Center)
        bookTableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(bookTableModel);
        bookTable.setRowHeight(25);
        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Action Panel (Bottom)
        JPanel actionPanel = new JPanel();
        JButton btnDelete = new JButton("Delete Selected Book");
        JButton btnRefresh = new JButton("Refresh");
        actionPanel.add(btnDelete);
        actionPanel.add(btnRefresh);
        panel.add(actionPanel, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(e -> {
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author cannot be empty!");
                return;
            }
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Book(Title, Author) VALUES(?, ?)")) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
                txtTitle.setText("");
                txtAuthor.setText("");
                loadBooksData("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnSearch.addActionListener(e -> {
            String title = txtTitle.getText().trim();
            loadBooksData(title);
        });

        btnRefresh.addActionListener(e -> {
            txtTitle.setText("");
            txtAuthor.setText("");
            loadBooksData("");
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.");
                return;
            }
            int bookId = (int) bookTableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Book ID: " + bookId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Book WHERE BookID = ?")) {
                    pstmt.setInt(1, bookId);
                    pstmt.executeUpdate();
                    loadBooksData("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return panel;
    }

    private void loadBooksData(String keyword) {
        bookTableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt;
            if (keyword == null || keyword.isEmpty()) {
                pstmt = conn.prepareStatement("SELECT * FROM Book");
            } else {
                pstmt = conn.prepareStatement("SELECT * FROM Book WHERE Title LIKE ?");
                pstmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookTableModel.addRow(new Object[]{
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ==========================================
    // 2. MANAGE STUDENTS TAB
    // ==========================================
    private JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Name:"), gbc);
        JTextField txtName = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Class:"), gbc);
        JTextField txtClass = new JTextField(10);
        gbc.gridx = 3;
        formPanel.add(txtClass, gbc);

        gbc.gridx = 4;
        formPanel.add(new JLabel("Contact:"), gbc);
        JTextField txtContact = new JTextField(10);
        gbc.gridx = 5;
        formPanel.add(txtContact, gbc);

        JButton btnAdd = new JButton("Add Student");
        gbc.gridx = 6;
        formPanel.add(btnAdd, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Table Panel
        studentTableModel = new DefaultTableModel(new String[]{"Student ID", "Name", "Class", "Contact"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(studentTableModel);
        studentTable.setRowHeight(25);
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Action Panel
        JPanel actionPanel = new JPanel();
        JButton btnDelete = new JButton("Delete Selected Student");
        actionPanel.add(btnDelete);
        panel.add(actionPanel, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(e -> {
            String name = txtName.getText().trim();
            String stdClass = txtClass.getText().trim();
            String contact = txtContact.getText().trim();
            if (name.isEmpty() || stdClass.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student(Name, Class, Contact) VALUES(?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, stdClass);
                pstmt.setString(3, contact);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student Added Successfully!");
                txtName.setText("");
                txtClass.setText("");
                txtContact.setText("");
                loadStudentsData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to delete.");
                return;
            }
            int stdId = (int) studentTableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Student ID: " + stdId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Student WHERE StudentID = ?")) {
                    pstmt.setInt(1, stdId);
                    pstmt.executeUpdate();
                    loadStudentsData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return panel;
    }

    private void loadStudentsData() {
        studentTableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student")) {
            while (rs.next()) {
                studentTableModel.addRow(new Object[]{
                        rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Class"),
                        rs.getString("Contact")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ==========================================
    // 3. ISSUE BOOK TAB
    // ==========================================
    private JPanel createIssueTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblBook = new JLabel("Book ID:");
        lblBook.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblBook, gbc);

        JTextField txtBookID = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtBookID, gbc);

        JLabel lblStudent = new JLabel("Student ID:");
        lblStudent.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblStudent, gbc);

        JTextField txtStudentID = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtStudentID, gbc);

        JButton btnIssue = new JButton("Issue Book");
        btnIssue.setFont(new Font("Arial", Font.BOLD, 14));
        btnIssue.setBackground(new Color(0, 153, 76));
        btnIssue.setForeground(Color.WHITE);
        btnIssue.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnIssue, gbc);

        btnIssue.addActionListener(e -> {
            String bIdStr = txtBookID.getText().trim();
            String sIdStr = txtStudentID.getText().trim();
            
            if(bIdStr.isEmpty() || sIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both IDs");
                return;
            }

            try {
                int bid = Integer.parseInt(bIdStr);
                int sid = Integer.parseInt(sIdStr);

                Connection conn = DBConnection.getConnection();
                
                // 1. Verify Book availability
                PreparedStatement ps1 = conn.prepareStatement("SELECT Status FROM Book WHERE BookID = ?");
                ps1.setInt(1, bid);
                ResultSet rs1 = ps1.executeQuery();
                if(!rs1.next()) {
                    JOptionPane.showMessageDialog(this, "Book not found!");
                    return;
                }
                if("Issued".equals(rs1.getString("Status"))) {
                    JOptionPane.showMessageDialog(this, "Book is already issued!");
                    return;
                }
                
                // 2. Verify Student exists
                PreparedStatement ps2 = conn.prepareStatement("SELECT Name FROM Student WHERE StudentID = ?");
                ps2.setInt(1, sid);
                ResultSet rs2 = ps2.executeQuery();
                if(!rs2.next()) {
                    JOptionPane.showMessageDialog(this, "Student not found!");
                    return;
                }

                // 3. Issue the book (Insert into Issue table, update Book status)
                conn.setAutoCommit(false); // Transaction
                
                PreparedStatement ps3 = conn.prepareStatement("INSERT INTO Issue(StudentID, BookID, IssueDate) VALUES(?, ?, CURDATE())");
                ps3.setInt(1, sid);
                ps3.setInt(2, bid);
                ps3.executeUpdate();
                
                PreparedStatement ps4 = conn.prepareStatement("UPDATE Book SET Status = 'Issued' WHERE BookID = ?");
                ps4.setInt(1, bid);
                ps4.executeUpdate();
                
                conn.commit();
                conn.setAutoCommit(true);
                
                JOptionPane.showMessageDialog(this, "Book Issued Successfully to " + rs2.getString("Name") + "!");
                txtBookID.setText("");
                txtStudentID.setText("");
                
                loadBooksData("");
                loadIssuesData();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs must be numeric!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error during issue.");
            }
        });

        return panel;
    }

    // ==========================================
    // 4. ISSUE HISTORY & RETURN TAB
    // ==========================================
    private JPanel createReturnTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table Panel
        issueTableModel = new DefaultTableModel(new String[]{"Issue ID", "Student Name", "Book Title", "Issue Date", "Return Date", "Status"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        issueTable = new JTable(issueTableModel);
        issueTable.setRowHeight(25);
        panel.add(new JScrollPane(issueTable), BorderLayout.CENTER);

        // Action Panel
        JPanel actionPanel = new JPanel();
        JButton btnReturn = new JButton("Return Selected Book");
        btnReturn.setBackground(new Color(255, 102, 102));
        btnReturn.setForeground(Color.WHITE);
        actionPanel.add(btnReturn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        btnReturn.addActionListener(e -> {
            int selectedRow = issueTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an issued record to return.");
                return;
            }
            
            String status = (String) issueTableModel.getValueAt(selectedRow, 5);
            if("Returned".equals(status)) {
                JOptionPane.showMessageDialog(this, "This book has already been returned.");
                return;
            }

            int issueId = (int) issueTableModel.getValueAt(selectedRow, 0);
            
            try {
                Connection conn = DBConnection.getConnection();
                
                // Get Issue Date and Book ID to calculate fine and update
                PreparedStatement ps1 = conn.prepareStatement("SELECT BookID, IssueDate FROM Issue WHERE IssueID = ?");
                ps1.setInt(1, issueId);
                ResultSet rs1 = ps1.executeQuery();
                if(rs1.next()) {
                    int bookId = rs1.getInt("BookID");
                    java.sql.Date sqlIssueDate = rs1.getDate("IssueDate");
                    
                    LocalDate issueDate = sqlIssueDate.toLocalDate();
                    LocalDate today = LocalDate.now();
                    
                    // Simple logic: Assume 14 days allowed. ₹5 per day late fine.
                    long daysBetween = ChronoUnit.DAYS.between(issueDate, today);
                    long lateDays = daysBetween - 14;
                    long fine = lateDays > 0 ? lateDays * 5 : 0;
                    
                    String msg = "Confirm Return?\nDays Issued: " + daysBetween;
                    if(fine > 0) {
                        msg += "\nLate Fine: ₹" + fine;
                    }
                    
                    int confirm = JOptionPane.showConfirmDialog(this, msg, "Process Return", JOptionPane.YES_NO_OPTION);
                    
                    if(confirm == JOptionPane.YES_OPTION) {
                        conn.setAutoCommit(false);
                        
                        PreparedStatement ps2 = conn.prepareStatement("UPDATE Issue SET ReturnDate = CURDATE() WHERE IssueID = ?");
                        ps2.setInt(1, issueId);
                        ps2.executeUpdate();
                        
                        PreparedStatement ps3 = conn.prepareStatement("UPDATE Book SET Status = 'Available' WHERE BookID = ?");
                        ps3.setInt(1, bookId);
                        ps3.executeUpdate();
                        
                        conn.commit();
                        conn.setAutoCommit(true);
                        
                        JOptionPane.showMessageDialog(this, "Book Returned Successfully!" + (fine > 0 ? " Please collect fine: ₹" + fine : ""));
                        loadIssuesData();
                        loadBooksData("");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return panel;
    }

    private void loadIssuesData() {
        issueTableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
             
            String sql = "SELECT i.IssueID, s.Name as StudentName, b.Title as BookTitle, i.IssueDate, i.ReturnDate " +
                         "FROM Issue i " +
                         "JOIN Student s ON i.StudentID = s.StudentID " +
                         "JOIN Book b ON i.BookID = b.BookID " +
                         "ORDER BY i.IssueID DESC";
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                java.sql.Date returnDate = rs.getDate("ReturnDate");
                String statusStr = (returnDate == null) ? "Active" : "Returned";
                
                issueTableModel.addRow(new Object[]{
                        rs.getInt("IssueID"),
                        rs.getString("StudentName"),
                        rs.getString("BookTitle"),
                        rs.getDate("IssueDate"),
                        returnDate,
                        statusStr
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
