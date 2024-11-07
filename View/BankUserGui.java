package View;

import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankUserGui extends BaseFrame implements ActionListener {
    private JTextField currentBalanceField;
    private String userName;
    private double userBalance;


    public JTextField getCurrentBalancField() {
        return currentBalanceField;
    }

    public BankUserGui(User user) {
        super("Bank User " + user.getUsername() , user);


    }

    @Override
    protected void addGuiComponents() {
        //private cause it will be used in other classes




        String welcomeMessage = ("<html><body style='text-align:center'><b>Hello %s</b><br>" +
                "Welcome to the Bank User Portal</body></html>").formatted(user.getUsername());



        JLabel welcomeLabel = new JLabel(welcomeMessage);
        welcomeLabel.setBounds(0, 20, super.getWidth() - 10, 40);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel accountBalanceLabel = new JLabel("Account Balance: ");
        accountBalanceLabel.setBounds(0, 80, super.getWidth() - 10, 30);
        accountBalanceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        accountBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentBalanceField = new JTextField("$" + user.getBalance());
        currentBalanceField.setBounds(20, 120, getWidth() - 50, 40);
        currentBalanceField.setFont(new Font("Arial", Font.BOLD, 28));
        currentBalanceField.setEditable(false);
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 200, getWidth() - 50, 50);
        depositButton.setFont(new Font("Arial", Font.BOLD, 22));
        depositButton.addActionListener(this);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 260, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 22));
        withdrawButton.addActionListener(this);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 320, getWidth() - 50, 50);
        transferButton.setFont(new Font("Arial", Font.BOLD, 22));
        transferButton.addActionListener(this);

        JButton lastTransactionButton = new JButton("Last Transaction");
        lastTransactionButton.setBounds(15, 380, getWidth() - 50, 50);
        lastTransactionButton.setFont(new Font("Arial", Font.BOLD, 22));
        lastTransactionButton.addActionListener(this);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 500, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 22));
        logoutButton.addActionListener(this);

        add(welcomeLabel);
        add(accountBalanceLabel);
        add(accountBalanceLabel);
        add(currentBalanceField);
        add(depositButton);
        add(withdrawButton);
        add(transferButton);
        add(lastTransactionButton);
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        //user logged out
        if (action.equalsIgnoreCase("Logout")) {
            this.dispose();
            new LoginGui("Bank User Login").setVisible(true);
            return;
        }

        BankingDialog bankingDialog = new BankingDialog(this, user);

        bankingDialog.setTitle(action);

        //if user clicked on last transaction, we don't need to show the balance field
        if (action.equalsIgnoreCase("Deposit") || action.equalsIgnoreCase("Withdraw")
                || action.equalsIgnoreCase("Transfer")) {

            bankingDialog.addBalanceField();
            bankingDialog.addActionButton(action);

            if (action.equalsIgnoreCase("Transfer")) {
                bankingDialog.addUserTransferField();
            }
        }
        else if(action.equalsIgnoreCase("Last Transaction")){
            bankingDialog.addPastTransactionsComponents();
        }
        bankingDialog.setVisible(true);
    }







}
