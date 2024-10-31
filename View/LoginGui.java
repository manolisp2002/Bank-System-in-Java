package View;

import javax.swing.*;
import java.awt.*;

public class LoginGui extends BaseFrame {

    public LoginGui(String title) {
        super("Bank User Login");
    }

    @Override
    protected void addGuiComponents() {

        JLabel bankinfo = new JLabel("Bank User Login");
        bankinfo.setBounds(0, 20, super.getWidth(), 40);
        bankinfo.setFont(new Font("Arial", Font.BOLD, 32));
        bankinfo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(20, 120, getWidth(), 24);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 28));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 280, getWidth() - 50 , 24);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 28));

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 440, getWidth() - 50, 40);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 28));

        JLabel registerLabel = new JLabel("<html><a href=''>Don't have an account? Register Here!</a></html>");
        registerLabel.setBounds(20, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);



        // Add the components to the frame
        add(bankinfo);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerLabel);






    }

}

