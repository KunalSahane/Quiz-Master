package quizProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HomePage implements ActionListener {
    static String rollNo = null;  // Changed from 'prn' to 'rollNo'
    JFrame frame;
    JLabel welcome = new JLabel("Welcome To Quiz Masters ");

    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu2 = new JMenu("Help");
    JMenu menu3 = new JMenu("Admin Panel");

    JMenuItem menuItem1 = new JMenuItem("New User Registration");
    JMenuItem menuItem2 = new JMenuItem("Login");
    JMenuItem menuItem3 = new JMenuItem("Attempt Quiz");
    JMenuItem menuItem4 = new JMenuItem("Logout");

    JMenuItem menuItem5 = new JMenuItem("Change Password");
    JMenuItem menuItem6 = new JMenuItem("Admin Login");

    JLabel image = new JLabel();
    ImageIcon quizIcon;

    HomePage() {
        createWindows();
        setLocationAndSize();
        addComponentsToFrame();
        actionsEvents();
    }

    public void createWindows() {
        frame = new JFrame();
        frame.setTitle("Online Quiz Program");
        frame.setBounds(50, 20, 1200, 800); // main frame
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
    }

    public void setLocationAndSize() {
        menuBar.setBounds(2, 2, 1200, 30); // File Menu field

        // Menu 1
        menu1.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        menuItem1.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
        menuItem2.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
        menuItem3.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
        menuItem4.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        // Menu 2
        menu2.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
        menuItem5.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        // Menu 3
        menu3.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
        menuItem6.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        menu1.add(menuItem1);
        menu1.add(menuItem2);
        menu1.add(menuItem3);
        menu1.add(menuItem4);

        menu2.add(menuItem5);

        menu3.add(menuItem6);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);

        welcome.setBounds(100, 80, 800, 50); // WELCOME bar
        welcome.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));

        // Load the image using getClass().getResource() with a relative path
        quizIcon = new ImageIcon(getClass().getResource("/quiz.jpeg"));

        // Resize the image if needed (optional)
        Image scaledImage = quizIcon.getImage().getScaledInstance(750, 760, Image.SCALE_SMOOTH); // image length width

        // Set the resized image as an icon
        image.setIcon(new ImageIcon(scaledImage));

        // Adjust the position and size of the image on the right side of the frame
        image.setBounds(500, 2, 750, 760); // image placement
    }

    public void addComponentsToFrame() {
        frame.add(menuBar);
        frame.add(welcome);
        frame.add(image);
        // Force revalidation and repaint to make sure all components render properly
        frame.revalidate();
        frame.repaint();
    }

    public void actionsEvents() {
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);
        menuItem5.addActionListener(this);
        menuItem6.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItem1) {
            // Close the current frame
            frame.setVisible(false);
            new Formpage(this); // Pass current HomePage instance to Formpage
        }

        if (e.getSource() == menuItem2) {
            frame.setVisible(false);
            new Login(); // Directly open Login frame
        }

        if (e.getSource() == menuItem3) {
            rollNo = JOptionPane.showInputDialog("Enter Your Roll No");
            try {
                Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizmaster", "root", "");
                Statement stmt = conn1.createStatement();
                String query = "SELECT RollNo FROM login WHERE RollNo = '" + rollNo + "'";
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    rollNo = rs.getString("RollNo");
                    JOptionPane.showMessageDialog(null, "Logout Successful for Roll No: " + rollNo);
                } else {
                    JOptionPane.showMessageDialog(null, "Roll No not found!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            frame.setVisible(false);
        }

        if (e.getSource() == menuItem5) {
            new PassChange(); // Open Password Change frame
        }

        if (e.getSource() == menuItem6) {
            frame.setVisible(false);
            new AdminPanel(); // Open Admin Panel frame
        }
    }

    public void close() {
        frame.setVisible(true); // Show the HomePage frame when the current frame is closed
    }
}

class main2 {
    public static void main(String[] args) {
        new HomePage(); // Create a new instance of HomePage
    }
}
