import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Login extends JFrame implements ActionListener {

    JButton loginButton, registerButton, resetButton;
    JLabel userLabel, passLabel, messageLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    int loginAttempts = 0;

    Login() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 30, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(150, 30, 200, 25);
        panel.add(usernameField);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 70, 80, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 70, 200, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 80, 30);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(190, 110, 100, 30);
        panel.add(registerButton);

        resetButton = new JButton("Reset Password");
        resetButton.setBounds(100, 160, 190, 30);
        panel.add(resetButton);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 200, 300, 25);
        panel.add(messageLabel);

        // Add ActionListener to buttons
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        resetButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (username.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose(); // Close the login window
                new OnlineTestBegin(username); // Open the online test window
            } else {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    JOptionPane.showMessageDialog(this, "Login Attempts Exceeded. Please try again later.");
                    System.exit(0);
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Invalid username or password. Please try again.");
                }
            }
        } else if (ae.getSource() == registerButton) {
            // Placeholder for registration functionality
            JOptionPane.showMessageDialog(this, "Registration is not implemented yet.");
            // Add your registration code here
            // Example: new RegistrationForm();
        } else if (ae.getSource() == resetButton) {
            // Placeholder for password reset functionality
            JOptionPane.showMessageDialog(this, "Password reset is not implemented yet.");
            // Add your password reset code here
            // Example: new ResetPasswordForm();
        }
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Login();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

class OnlineTestBegin extends JFrame implements ActionListener {

    JLabel questionLabel, timeLabel;
    JRadioButton[] options;
    JButton nextButton, submitButton;
    ButtonGroup optionGroup;
    int count = 0, current = 0;

    OnlineTestBegin(String username) {
        setTitle("Online Test - Welcome " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        questionLabel = new JLabel();
        panel.add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            panel.add(options[i]);
        }

        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        nextButton.addActionListener(this);
        submitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        panel.add(buttonPanel);

        timeLabel = new JLabel();
        panel.add(timeLabel);

        setQuestion(current);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            if (check()) count++;
            current++;
            setQuestion(current);
            if (current == 9) {
                nextButton.setEnabled(false);
                submitButton.setText("Submit");
            }
        } else if (e.getSource() == submitButton) {
            if (check()) count++;
            submitAnswers();
        }
    }

    void setQuestion(int index) {
        switch (index) {
            case 0:
                questionLabel.setText("Q1: Who is known as the father of the Java programming language?");
                options[0].setText("Charles Babbage");
                options[1].setText("James Gosling");
                options[2].setText("M.P. Java");
                options[3].setText("Blais Pascal");
                break;
            case 1:
                questionLabel.setText("Q2: Number of primitive data types in Java are?");
                options[0].setText("6");
                options[1].setText("7");
                options[2].setText("8");
                options[3].setText("9");
                break;
            case 2:
                questionLabel.setText("Q3: Where is the System class defined?");
                options[0].setText("java.lang package");
                options[1].setText("java.util package");
                options[2].setText("java.io package");
                options[3].setText("None");
                break;
            case 3:
                questionLabel.setText("Q4: Exception created by the try block is caught in which block?");
                options[0].setText("catch");
                options[1].setText("throw");
                options[2].setText("final");
                options[3].setText("thrown");
                break;
            case 4:
                questionLabel.setText("Q5: Which of the following is not an OOPS concept in Java?");
                options[0].setText("Polymorphism");
                options[1].setText("Inheritance");
                options[2].setText("Compilation");
                options[3].setText("Encapsulation");
                break;
            case 5:
                questionLabel.setText("Q6: Identify the infinite loop?");
                options[0].setText("for(;;)");
                options[1].setText("for()i=0;j<1;i--");
                options[2].setText("for(int=0;i++)");
                options[3].setText("if(All of the above)");
                break;
            case 6:
                questionLabel.setText("Q7: When is the finalize() method called?");
                options[0].setText("Before garbage collection");
                options[1].setText("Before an object goes out of scope");
                options[2].setText("Before a variable goes out of scope");
                options[3].setText("None");
                break;
            case 7:
                questionLabel.setText("Q8: What is the implicit return type of a constructor?");
                options[0].setText("No return type");
                options[1].setText("A class object in which it is defined");
                options[2].setText("void");
                options[3].setText("None");
                break;
            case 8:
                questionLabel.setText("Q9: The class at the top of the exception class hierarchy is?");
                options[0].setText("ArithmeticException");
                options[1].setText("Throwable");
                options[2].setText("Object");
                options[3].setText("Console");
                break;
            case 9:
                questionLabel.setText("Q10: Which provides the runtime environment for Java bytecode to be executed?");
                options[0].setText("JDK");
                options[1].setText("JVM");
                options[2].setText("JRE");
                options[3].setText("JAVAC");
                break;
        }
        for (JRadioButton option : options) {
            option.setSelected(false);
        }
    }

    boolean check() {
        if (current == 0)
            return options[1].isSelected();
        if (current == 1)
            return options[0].isSelected();
        if (current == 2)
            return options[0].isSelected();
        if (current == 3)
            return options[0].isSelected();
        if (current == 4)
            return options[2].isSelected();
        if (current == 5)
            return options[0].isSelected();
        if (current == 6)
            return options[1].isSelected();
        if (current == 7)
            return options[0].isSelected();
        if (current == 8)
            return options[1].isSelected();
        if (current == 9)
            return options[1].isSelected();
        return false;
    }

    void submitAnswers() {
        JOptionPane.showMessageDialog(this, "Score = " + count);
        System.exit(0);
    }
}

class OnlineExam {
    public static void main(String args[]) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Login();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
