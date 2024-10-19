package quizProject;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class PassChange implements ActionListener {
    JFrame frame;
    JLabel rollNoLabel = new JLabel("Roll No");
    JLabel passwordLabel = new JLabel("Password");
    JLabel changePassLabel = new JLabel("New Password");
    JLabel Title = new JLabel("Forgotten Password");
    JLabel repassLabel = new JLabel("Re-Enter New Password");

    JPasswordField passwordField = new JPasswordField();
    JPasswordField changePassField = new JPasswordField();
    JPasswordField rePasswordField = new JPasswordField();
    JTextField rollNoTextField = new JTextField();

    JButton ChangeButton = new JButton("Change");

    PassChange() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("TPO Details Form");
        frame.setBounds(200, 70, 800, 700);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setVisible(true); // Make the frame visible
    }

    public void setLocationAndSize() {
        Title.setBounds(50, 5, 600, 40);
        Title.setBackground(Color.white);
        Title.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));

        rollNoLabel.setBounds(200, 60, 400, 40);
        rollNoLabel.setBackground(Color.white);
        rollNoLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        rollNoTextField.setBounds(200, 110, 400, 40);
        rollNoTextField.setBackground(Color.white);
        rollNoTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        passwordLabel.setBounds(200, 160, 400, 40);
        passwordLabel.setBackground(Color.white);
        passwordLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        passwordField.setBounds(200, 210, 400, 40);
        passwordField.setBackground(Color.white);
        passwordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        changePassLabel.setBounds(200, 260, 400, 40);
        changePassLabel.setBackground(Color.white);
        changePassLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 19));

        changePassField.setBounds(200, 310, 400, 40);
        changePassField.setBackground(Color.white);
        changePassField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        repassLabel.setBounds(200, 360, 400, 40);
        repassLabel.setBackground(Color.white);
        repassLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        rePasswordField.setBounds(200, 410, 400, 40);
        rePasswordField.setBackground(Color.white);
        rePasswordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));

        ChangeButton.setBounds(200, 490, 400, 40);
        ChangeButton.setFont(new Font("Cambria", Font.CENTER_BASELINE, 18));
    }

    public void addComponentsToFrame() {
        frame.add(rollNoLabel);
        frame.add(rollNoTextField);
        frame.add(Title);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(changePassLabel);
        frame.add(changePassField);
        frame.add(repassLabel);
        frame.add(rePasswordField);
        frame.add(ChangeButton);
    }

    public void actionEvent() {
        ChangeButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ChangeButton) {
            try {
                Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizmaster", "root", "");

                String rollNo = rollNoTextField.getText();
                String pass = passwordField.getText();
                String changePass = changePassField.getText();
                String re_pass = rePasswordField.getText();

                if (rollNo.equals("")) {
                    JOptionPane.showMessageDialog(null, "UserId is Missing");
                } else if (pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "Password is Missing");
                } else if (changePass.equals("")) {
                    JOptionPane.showMessageDialog(null, "New Password is Missing");
                } else if (!changePass.equals(re_pass)) {
                    JOptionPane.showMessageDialog(null, "Password Doesn't Match");
                }

                Statement stmt = conn1.createStatement();

                String query = "SELECT * FROM newuser";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String UserId = rs.getString("Roll_No"); // Changed to Roll_No
                    String Password = rs.getString("Password");

                    if (UserId.equals(rollNo)) {
                        JOptionPane.showMessageDialog(null, "User Already Exists");

                        PreparedStatement Pstatement = conn1.prepareStatement("UPDATE newuser SET Password = ? WHERE Roll_No = ?"); // Changed to Roll_No

                        Pstatement.setString(1, changePassField.getText());
                        Pstatement.setString(2, rollNoTextField.getText());

                        Pstatement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Password Updated Successfully");
                    }
                }
                frame.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Main3 {
    public static void main(String[] args) {
        PassChange pc = new PassChange();
    }
}
