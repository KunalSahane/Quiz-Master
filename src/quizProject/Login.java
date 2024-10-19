package quizProject;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Login implements ActionListener {
    JFrame frame;
    JLabel userNameLabel = new JLabel("USERNAME");
    JLabel Title = new JLabel("LOGIN FORM");
    JTextField userNameTextField = new JTextField();
    JLabel passwordLabel = new JLabel("PASSWORD");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JLabel image = new JLabel();
    Icon p;

    Login() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Login Form");
        frame.setBounds(300, 50, 800, 700);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Title.setBounds(300, 50, 400, 40);
        Title.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        Title.setBackground(Color.white);
        Title.setForeground(Color.black);

        userNameLabel.setBounds(200, 150, 400, 40);
        userNameLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        userNameLabel.setBackground(Color.white);
        userNameLabel.setForeground(Color.red);

        userNameTextField.setBounds(200, 200, 400, 40);
        userNameTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
        userNameTextField.setBackground(Color.WHITE);

        passwordLabel.setBounds(200, 250, 400, 40);
        passwordLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        passwordLabel.setBackground(Color.white);
        passwordLabel.setForeground(Color.red);

        passwordField.setBounds(200, 300, 400, 40);
        passwordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
        passwordField.setBackground(Color.WHITE);

        // Make sure to use a valid image path
        p = new ImageIcon("C:\\Users\\Shree\\IdeaProjects\\Quiz Project\\src\\icons\\Background.jpeg");

        image.setBounds(0, 0, 800, 700);
        image.setIcon(p);

        frame.add(image);

        loginButton.setBounds(200, 400, 400, 40);
        image.add(loginButton);
    }

    public void addComponentsToFrame() {
        image.add(Title);
        image.add(userNameLabel);
        image.add(userNameTextField);
        image.add(passwordLabel);
        image.add(passwordField);
        image.add(loginButton);
    }

    public void actionEvent() {
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizmaster", "root", "");
                String userName = userNameTextField.getText(); // Get the entered username
                String pass = new String(passwordField.getPassword()); // Get the entered password

                // SQL query to check login credentials
                String query = "SELECT * FROM newuser WHERE UserName = ? AND Password = ?";
                PreparedStatement ps = conn1.prepareStatement(query);
                ps.setString(1, userName);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();

                // If credentials are correct, start the Quiz
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Logged In Successfully");

                    // Log user details into the login table
                    PreparedStatement PStatement = conn1.prepareStatement("INSERT INTO login (UserName, Password) VALUES (?, ?)");
                    PStatement.setString(1, userName);
                    PStatement.setString(2, pass);
                    PStatement.executeUpdate(); // Insert the login information

                    // Use a Timer to delay the execution of the Quiz class
                    Timer timer = new Timer(1000, new ActionListener() { // 1-second delay
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            frame.setVisible(false);
                            Quiz quizMaster = new Quiz(userName);
                            quizMaster.setVisible(true);
                        }
                    });
                    timer.setRepeats(false); // Only execute once
                    timer.start(); // Start the timer

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");

                    // Redirect to Formpage for new user registration
                    frame.setVisible(false);
                    new Formpage(new HomePage()); // Open registration form
                }

                conn1.close(); // Close the connection
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
