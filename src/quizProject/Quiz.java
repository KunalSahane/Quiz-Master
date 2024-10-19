package quizProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz extends JFrame implements ActionListener {
    JLabel questionLabel, questionText, timerLabel;
    JCheckBox option1, option2, option3, option4;
    JButton nextButton, submitButton;
    ButtonGroup optionsGroup;
    int currentQuestion = 0;
    int score = 0;
    String userName; // Directly set this in the constructor
    Timer timer;
    int timeRemaining = 10; // 15 seconds for each question

    String[][] questions = {
            {"What is the size of int in Java?", "8 bits", "16 bits", "32 bits", "64 bits"},
            {"Which of the following is not a Java keyword?", "public", "static", "void", "main"},
            {"What does JVM stand for?", "Java Variable Model", "Java Virtual Machine", "Java Visual Machine", "Java Virtual Model"},
            {"Which method is used to start a thread in Java?", "run()", "start()", "init()", "execute()"},
            {"Which of the following is a superclass of every class in Java?", "Object", "Class", "System", "Thread"}
    };

    String[] answers = {"32 bits", "main", "Java Virtual Machine", "start()", "Object"};

    public Quiz(String userName) {
        this.userName = userName; // Initialize directly
        setTitle("Java Quiz Master");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout for the frame
        setLayout(null);

        questionLabel = new JLabel("Question " + (currentQuestion + 1));
        questionLabel.setFont(new Font("Cambria", Font.BOLD, 16));
        questionLabel.setBounds(10, 10, 300, 30);
        add(questionLabel);

        questionText = new JLabel(questions[currentQuestion][0]);
        questionText.setFont(new Font("Cambria", Font.CENTER_BASELINE, 15));
        questionText.setBounds(10, 50, 600, 30);
        add(questionText);

        optionsGroup = new ButtonGroup();
        option1 = new JCheckBox("• " + questions[currentQuestion][1]);
        option1.setBounds(10, 90, 600, 30);
        optionsGroup.add(option1);
        add(option1);

        option2 = new JCheckBox("• " + questions[currentQuestion][2]);
        option2.setBounds(10, 130, 600, 30);
        optionsGroup.add(option2);
        add(option2);

        option3 = new JCheckBox("• " + questions[currentQuestion][3]);
        option3.setBounds(10, 170, 600, 30);
        optionsGroup.add(option3);
        add(option3);

        option4 = new JCheckBox("• " + questions[currentQuestion][4]);
        option4.setBounds(10, 210, 600, 30);
        optionsGroup.add(option4);
        add(option4);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setBounds(10, 250, 100, 30);
        add(nextButton);

        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        submitButton.setBounds(120, 250, 100, 30);
        add(submitButton);

        timerLabel = new JLabel("Time remaining: 15", JLabel.CENTER);
        timerLabel.setFont(new Font("Cambria", Font.BOLD, 16));
        timerLabel.setBounds(10, 300, 200, 30);
        add(timerLabel);

        startTimer();
    }

    private void startTimer() {
        timeRemaining = 10;
        timerLabel.setText("Time remaining: " + timeRemaining);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time remaining: " + timeRemaining);
                if (timeRemaining <= 0) {
                    timer.stop();
                    checkAnswer();
                    if (currentQuestion < questions.length - 1) {
                        currentQuestion++;
                        loadNextQuestion();
                    } else {
                        displayScoreFrame();
                    }
                }
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            checkAnswer();
            if (currentQuestion < questions.length - 1) {
                currentQuestion++;
                loadNextQuestion();
            }
        } else if (e.getSource() == submitButton) {
            checkAnswer();
            displayScoreFrame();
        }
    }

    private void checkAnswer() {
        timer.stop(); // Stop the timer when checking the answer
        String selectedAnswer = null;
        if (option1.isSelected()) {
            selectedAnswer = option1.getText().substring(2);
        } else if (option2.isSelected()) {
            selectedAnswer = option2.getText().substring(2);
        } else if (option3.isSelected()) {
            selectedAnswer = option3.getText().substring(2);
        } else if (option4.isSelected()) {
            selectedAnswer = option4.getText().substring(2);
        }

        if (selectedAnswer != null) {
            if (selectedAnswer.equals(answers[currentQuestion])) {
                score++;
                JOptionPane.showMessageDialog(this, "Correct Answer!", "Feedback", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong Answer! Correct answer: " + answers[currentQuestion], "Feedback", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an option!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadNextQuestion() {
        startTimer();  // Restart timer for the next question
        questionLabel.setText("Question " + (currentQuestion + 1));
        questionText.setText(questions[currentQuestion][0]);
        option1.setText("• " + questions[currentQuestion][1]);
        option2.setText("• " + questions[currentQuestion][2]);
        option3.setText("• " + questions[currentQuestion][3]);
        option4.setText("• " + questions[currentQuestion][4]);

        optionsGroup.clearSelection();

        // Enable the submit button on the last question
        if (currentQuestion == questions.length - 1) {
            nextButton.setEnabled(false);
            submitButton.setEnabled(true);
        }
    }

    private void displayScoreFrame() {
        JFrame scoreFrame = new JFrame("Quiz Result");
        scoreFrame.setSize(400, 400);
        scoreFrame.setLayout(new BorderLayout());
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel scoreLabel = new JLabel("Your Quiz Score is: " + score + "/" + questions.length, JLabel.CENTER);
        scoreLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        scoreFrame.add(scoreLabel, BorderLayout.NORTH);

        scoreFrame.setLocationRelativeTo(null);
        scoreFrame.setVisible(true);

    }

    public static void main(String[] args) {
        String userName = "Student"; // Directly set the username here

        SwingUtilities.invokeLater(() -> {
            Quiz quizMaster = new Quiz(userName);
            quizMaster.setVisible(true);
        });
    }
}
