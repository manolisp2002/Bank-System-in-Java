package View;

import Model.User;

import javax.swing.*;
import java.awt.*;

public class BankUserGui extends BaseFrame{
    private JTextField currentBalancField;
    private User user;


    public JTextField getCurrentBalancField() {
        return currentBalancField;
    }
    public BankUserGui(User user) {
        super("Bank User " + user.getUsername());
        this.user = user;
    }


    @Override
    protected void addGuiComponents() {
        //private cause it will be used in other classes



        String welcomeMessage = ("<html><body style='text-align:center'><b>Hello %s</b><br>" +
                "Welcome to the Bank User Portal</body></html>").formatted("user.getUsername()");



        JLabel welcomeLabel = new JLabel(welcomeMessage);
        welcomeLabel.setBounds(0, 20, super.getWidth() - 10, 40);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel accountBalanceLabel = new JLabel("Account Balance: ");
        accountBalanceLabel.setBounds(0, 80, super.getWidth() - 10, 30);
        accountBalanceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        accountBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentBalancField = new JTextField("$" + "user.getBalance()");
        currentBalancField.setBounds(20, 120, getWidth() - 50, 40);
        currentBalancField.setFont(new Font("Arial", Font.BOLD, 28));
        currentBalancField.setEditable(false);
        currentBalancField.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 200, getWidth() - 50, 50);
        depositButton.setFont(new Font("Arial", Font.BOLD, 22));

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 260, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 22));

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 320, getWidth() - 50, 50);
        transferButton.setFont(new Font("Arial", Font.BOLD, 22));

        JButton lastTransactionButton = new JButton("Last Transaction");
        lastTransactionButton.setBounds(15, 380, getWidth() - 50, 50);
        lastTransactionButton.setFont(new Font("Arial", Font.BOLD, 22));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 500, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 22));











        add(welcomeLabel);
        add(accountBalanceLabel);
        add(accountBalanceLabel);
        add(currentBalancField);
        add(depositButton);
        add(withdrawButton);
        add(transferButton);
        add(lastTransactionButton);
        add(logoutButton);
    }


}
