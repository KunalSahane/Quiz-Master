package quizProject;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;

public class Formpage implements ActionListener {
    JFrame frame;
    HomePage homePage; // Reference to HomePage

    JLabel Title = new JLabel("Registration Form");
    JLabel rollNoLabel = new JLabel("Roll No");
    JLabel nameLabel = new JLabel("UserName");
    JLabel emailLabel = new JLabel("Email");
    JLabel contactLabel = new JLabel("Contact No.");
    JLabel classLabel = new JLabel("Class");
    JLabel passwordLabel = new JLabel("Password");
    JLabel branchLabel = new JLabel("Branch");

    JTextField rollNoTextField = new JTextField();
    JTextField nameTextField = new JTextField();
    JTextField contactTextField = new JTextField();
    JTextField emailTextField = new JTextField();

    JPasswordField passwordField = new JPasswordField();

    String[] branch = {"CS", "IT", "E&TC", "MECH", "CIVIL"};
    String[] Class = {"FE", "SE", "TE", "BE"};

    JComboBox<String> className = new JComboBox<>(Class);
    JComboBox<String> branchName = new JComboBox<>(branch);

    JButton SubmitButton = new JButton("Submit");
    JButton ResetButton = new JButton("Reset");

    // Constructor taking HomePage reference
    Formpage(HomePage homePage) {
        this.homePage = homePage; // Set the reference
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();

        // Set no selection initially for class and branch
        className.setSelectedIndex(-1); // No selection
        branchName.setSelectedIndex(-1); // No selection

        // Listener to show HomePage when Formpage is closed
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                homePage.close(); // Show HomePage when Formpage is closed
            }
        });
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Student Detail Form ");
        frame.setBounds(50, 10, 800, 750); // Main frame
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close without exiting the application
        frame.setResizable(true);
    }

    public void setLocationAndSize() {
        Title.setBounds(50, 2, 400, 40); // Registration Form
        Title.setBackground(Color.white);
        Title.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));

        rollNoLabel.setBounds(200, 50, 400, 35);
        rollNoLabel.setBackground(Color.white);
        rollNoLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        rollNoTextField.setBounds(200, 90, 400, 35);
        rollNoTextField.setBackground(Color.white);
        rollNoTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        nameLabel.setBounds(200, 130, 400, 35);
        nameLabel.setBackground(Color.white);
        nameLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        nameTextField.setBounds(200, 170, 400, 35);
        nameTextField.setBackground(Color.white);
        nameTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        emailLabel.setBounds(200, 210, 400, 35);
        emailLabel.setBackground(Color.white);
        emailLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        emailTextField.setBounds(200, 250, 400, 35);
        emailTextField.setBackground(Color.white);
        emailTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        passwordLabel.setBounds(200, 290, 400, 35);
        passwordLabel.setBackground(Color.white);
        passwordLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        passwordField.setBounds(200, 330, 400, 35);
        passwordField.setBackground(Color.white);
        passwordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        classLabel.setBounds(200, 370, 400, 35);
        classLabel.setBackground(Color.white);
        classLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        className.setBounds(200, 410, 400, 35);
        className.setBackground(Color.white);
        className.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        branchLabel.setBounds(200, 450, 400, 35);
        branchLabel.setBackground(Color.white);
        branchLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        branchName.setBounds(200, 490, 400, 35);
        branchName.setBackground(Color.white);
        branchName.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        contactLabel.setBounds(200, 530, 400, 35);
        contactLabel.setBackground(Color.white);
        contactLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        contactTextField.setBounds(200, 570, 400, 35);
        contactTextField.setBackground(Color.white);
        contactTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        SubmitButton.setBounds(200, 620, 100, 40);
        SubmitButton.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        ResetButton.setBounds(500, 620, 100, 40);
        ResetButton.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
    }

    public void addComponentsToFrame() {
        frame.add(Title);
        frame.add(rollNoLabel);
        frame.add(rollNoTextField);
        frame.add(nameLabel);
        frame.add(nameTextField);
        frame.add(emailLabel);
        frame.add(emailTextField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(branchLabel);
        frame.add(branchName);
        frame.add(contactLabel);
        frame.add(contactTextField);
        frame.add(SubmitButton);
        frame.add(ResetButton);
        frame.add(classLabel);
        frame.add(className);
    }

    public void actionEvent() {
        SubmitButton.addActionListener(this);
        ResetButton.addActionListener(this);
    }

    // Updated actionPerformed method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SubmitButton) {
            try {
                Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizmaster", "root", "");

                // First check if Roll No or email already exists
                String queryCheck = "SELECT * FROM newuser WHERE Roll_No = ? OR Email = ?";
                PreparedStatement checkStatement = conn1.prepareStatement(queryCheck);
                checkStatement.setString(1, rollNoTextField.getText());
                checkStatement.setString(2, emailTextField.getText());
                ResultSet rs = checkStatement.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Roll No or Email already exists!");
                } else {
                    // Insert new user details
                    PreparedStatement Pstatement = conn1.prepareStatement("INSERT INTO newuser (Roll_No, Email, ContactNo, UserName, Class, Branch, Password) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    Pstatement.setString(1, rollNoTextField.getText());
                    Pstatement.setString(2, emailTextField.getText());
                    Pstatement.setString(3, contactTextField.getText());
                    Pstatement.setString(4, nameTextField.getText());
                    Pstatement.setString(5, className.getSelectedItem() != null ? className.getSelectedItem().toString() : null);
                    Pstatement.setString(6, branchName.getSelectedItem() != null ? branchName.getSelectedItem().toString() : null);
                    Pstatement.setString(7, passwordField.getText());

                    Pstatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "User Registered Successfully");

                    // Close the registration frame and open the Login frame
                    frame.setVisible(false); // Hide the Formpage frame
                    new Login(); // Automatically open the Login frame after successful registration
                }
            } catch (SQLException el) {
                el.printStackTrace();
            }
        }

        if (e.getSource() == ResetButton) {
            rollNoTextField.setText("");
            nameTextField.setText("");
            contactTextField.setText("");
            className.setSelectedIndex(-1); // Reset class selection
            branchName.setSelectedIndex(-1); // Reset branch selection
            passwordField.setText("");
            emailTextField.setText("");
        }
    }

    public static void main(String[] args) {
        new Formpage(new HomePage()); // Create a new Formpage with a new HomePage instance
    }
}
