package quizProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel implements ActionListener {
    JFrame frame;
    JLabel titleLabel = new JLabel("ADMIN LOGIN"); // Title label
    JLabel adminLoginLabel = new JLabel("ADMIN ID"); // Changed label text
    JTextField adminLoginField = new JTextField();
    JLabel adminPasswordLabel = new JLabel("PASSWORD");
    JPasswordField adminPasswordField = new JPasswordField();
    JButton setAdminButton = new JButton("Set Admin");
    JButton updateQuestionsButton = new JButton("Update Questions");
    JTextArea questionArea = new JTextArea();
    private Connection connection;

    public AdminPanel() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
        connectDatabase();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Admin Panel");
        frame.setBounds(200, 100, 800, 700); // Set frame position to (200, 100)
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        titleLabel.setBounds(300, 50, 400, 40);
        titleLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        titleLabel.setForeground(Color.black);

        adminLoginLabel.setBounds(200, 140, 400, 40); // Changed from AdminLoginLabel to Roll No label
        adminLoginLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        adminLoginLabel.setForeground(Color.red);

        adminLoginField.setBounds(200, 200, 400, 40);
        adminLoginField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
        adminLoginField.setBackground(Color.WHITE);

        adminPasswordLabel.setBounds(200, 260, 400, 40);
        adminPasswordLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        adminPasswordLabel.setForeground(Color.red);

        adminPasswordField.setBounds(200, 320, 400, 40);
        adminPasswordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
        adminPasswordField.setBackground(Color.WHITE);

        JLabel questionLabel = new JLabel("Quiz Questions (one per line):");
        questionLabel.setBounds(200, 350, 400, 40);
        questionLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        questionLabel.setForeground(Color.red);

        questionArea.setBounds(200, 400, 400, 100);
        questionArea.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        setAdminButton.setBounds(200, 400, 180, 40);
        updateQuestionsButton.setBounds(420, 400, 180, 40);
    }

    public void addComponentsToFrame() {
        frame.add(titleLabel);
        frame.add(adminLoginLabel);
        frame.add(adminLoginField);
        frame.add(adminPasswordLabel);
        frame.add(adminPasswordField);
        frame.add(new JScrollPane(questionArea)); // Add scroll pane for questions
        frame.add(setAdminButton);
        frame.add(updateQuestionsButton);
    }

    public void actionEvent() {
        setAdminButton.addActionListener(this);
        updateQuestionsButton.addActionListener(this);
    }

    private void connectDatabase() {
        try {
            // Change URL, USERNAME, and PASSWORD to your database details
            String url = "jdbc:mysql://localhost:3306/quizmaster"; // Update with your database name
            String username = "root"; // Update with your username
            String password = ""; // Update with your password
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database connection failed!");
        }
    }

    private void setAdminLogin() {
        String login = adminLoginField.getText();
        String password = new String(adminPasswordField.getPassword());

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO admin (login, password) VALUES (?, ?)");
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Admin set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error setting admin! Check if the admin already exists.");
        }
    }

    private void updateQuizQuestions() {
        String[] questions = questionArea.getText().split("\n");

        try {
            Statement stmt = connection.createStatement();
            // Clear existing questions
            stmt.executeUpdate("DELETE FROM quiz_questions");
            // Add new questions
            for (String question : questions) {
                if (!question.trim().isEmpty()) {
                    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO quiz_questions (question) VALUES (?)");
                    pstmt.setString(1, question);
                    pstmt.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(frame, "Quiz questions updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating quiz questions!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setAdminButton) {
            setAdminLogin();
        } else if (e.getSource() == updateQuestionsButton) {
            updateQuizQuestions();
        }
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
