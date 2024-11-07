package View;

import Model.User;
import Controller.AuthenticatorController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        loginButton.addActionListener(new ActionListener() {
                                          @Override
                                          public void actionPerformed(ActionEvent e) {
                                                //get the username and password of the user
                                                String username = usernameField.getText();
                                                String password = new String(passwordField.getPassword());

                                                //validate the user
                                                User user = AuthenticatorController.authenticateLogin(username, password);

                                                //if user is null means invalid account otherwise valid account
                                                if(user != null) {
                                                    //dipose login
                                                    LoginGui.this.dispose();

                                                    //show the user bank gui
                                                    BankUserGui bankUserGui = new BankUserGui(user);
                                                    bankUserGui.setVisible(true);

                                                    //show message dialog
                                                    JOptionPane.showMessageDialog(bankUserGui,"LOGIN SUCCESSFUL");
                                                }
                                                else {
                                                    //show message dialog
                                                    JOptionPane.showMessageDialog(LoginGui.this,"INVALID LOGIN");
                                          }
                                      }    });

        JLabel registerLabel = new JLabel("<html><a href=''>Don't have an account? Register Here!</a></html>");
        registerLabel.setBounds(20, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //dispose login
                LoginGui.this.dispose();

                //show the register gui

                 new RegisterGui().setVisible(true);
            }
        });



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

