package View;

import javax.swing.*;
import java.awt.*;

public class RegisterGui extends BaseFrame {

    public RegisterGui() {
        super("Bank User Registration");
    }

    @Override
    protected void addGuiComponents() {

        JLabel bankinfo = new JLabel("Bank User Registration");
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
        passwordLabel.setBounds(20, 220, getWidth() - 50 , 24);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 28));

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(20, 320, getWidth() - 50 , 24);
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(20, 360, getWidth() - 50, 40);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 28));

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 440, getWidth() - 50, 40);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 28));


        JLabel loginLabel = new JLabel("<html><a href=''>Already have an acoount? Login here!</a></html>");
        loginLabel.setBounds(20, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);



        // Add the components to the frame
        add(bankinfo);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(registerButton);
        add(loginLabel);


    }
}
