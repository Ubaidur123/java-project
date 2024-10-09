import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExamSystem extends JFrame {
    // Store user information
    private HashMap<String, String> users = new HashMap<>();
    private String loggedInUser = null;

    // Timer variables
    private Timer timer;
    private int timeRemaining = 60;  // Time limit (in seconds)

    public OnlineExamSystem() {
        // Adding sample user
        users.put("user1", "password1");

        // Display login screen
        showLoginScreen();
    }

    // Method to display the login screen
    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(10);
        JPasswordField passField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (users.containsKey(username) && users.get(username).equals(password)) {
                    loggedInUser = username;
                    loginFrame.dispose();
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);

        loginFrame.add(panel);
        loginFrame.setSize(300, 150);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to display the main menu (after login)
    private void showMainMenu() {
        JFrame mainMenu = new JFrame("Online Exam - Main Menu");
        JButton updateProfileButton = new JButton("Update Profile");
        JButton startExamButton = new JButton("Start Exam");
        JButton logoutButton = new JButton("Logout");

        updateProfileButton.addActionListener(e -> showUpdateProfileScreen());
        startExamButton.addActionListener(e -> startExam(mainMenu));
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            mainMenu.dispose();
            showLoginScreen();
        });

        JPanel panel = new JPanel();
        panel.add(updateProfileButton);
        panel.add(startExamButton);
        panel.add(logoutButton);

        mainMenu.add(panel);
        mainMenu.setSize(300, 150);
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to show the update profile screen
    private void showUpdateProfileScreen() {
        JFrame updateProfileFrame = new JFrame("Update Profile");

        JLabel passLabel = new JLabel("New Password:");
        JPasswordField passField = new JPasswordField(10);
        JButton updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {
            String newPassword = new String(passField.getPassword());
            if (!newPassword.isEmpty()) {
                users.put(loggedInUser, newPassword);
                JOptionPane.showMessageDialog(updateProfileFrame, "Password updated successfully!");
                updateProfileFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(updateProfileFrame, "Password cannot be empty!");
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(passLabel);
        panel.add(passField);
        panel.add(updateButton);

        updateProfileFrame.add(panel);
        updateProfileFrame.setSize(300, 150);
        updateProfileFrame.setVisible(true);
    }

    // Method to start the exam
    private void startExam(JFrame mainMenu) {
        mainMenu.dispose();
        JFrame examFrame = new JFrame("Online Exam");
        examFrame.setLayout(new GridLayout(6, 1));

        JLabel question1 = new JLabel("1. What is the capital of France?");
        JRadioButton q1a1 = new JRadioButton("A) Berlin");
        JRadioButton q1a2 = new JRadioButton("B) Paris");
        JRadioButton q1a3 = new JRadioButton("C) Madrid");
        ButtonGroup q1Group = new ButtonGroup();
        q1Group.add(q1a1);
        q1Group.add(q1a2);
        q1Group.add(q1a3);

        JLabel question2 = new JLabel("2. Which is the largest planet?");
        JRadioButton q2a1 = new JRadioButton("A) Earth");
        JRadioButton q2a2 = new JRadioButton("B) Jupiter");
        JRadioButton q2a3 = new JRadioButton("C) Mars");
        ButtonGroup q2Group = new ButtonGroup();
        q2Group.add(q2a1);
        q2Group.add(q2a2);
        q2Group.add(q2a3);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            timer.cancel();  // Cancel the timer if manually submitted
            autoSubmit(examFrame);
        });

        examFrame.add(question1);
        examFrame.add(q1a1);
        examFrame.add(q1a2);
        examFrame.add(q1a3);
        examFrame.add(question2);
        examFrame.add(q2a1);
        examFrame.add(q2a2);
        examFrame.add(q2a3);
        examFrame.add(submitButton);

        examFrame.setSize(400, 400);
        examFrame.setVisible(true);

        // Start a timer for auto submission
        startTimer(examFrame);
    }

    // Method to start the exam timer
    private void startTimer(JFrame examFrame) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeRemaining--;
                examFrame.setTitle("Online Exam - Time remaining: " + timeRemaining + "s");

                if (timeRemaining == 0) {
                    timer.cancel();
                    autoSubmit(examFrame);
                }
            }
        }, 0, 1000);
    }

    // Method to auto-submit the exam when time is up
    private void autoSubmit(JFrame examFrame) {
        JOptionPane.showMessageDialog(examFrame, "Time's up! Submitting the exam...");
        examFrame.dispose();
        showMainMenu();
    }

    public static void main(String[] args) {
        new OnlineExamSystem();
    }
}
