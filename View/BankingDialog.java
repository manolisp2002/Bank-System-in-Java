package View;

import Model.User;

import javax.swing.*;
import java.awt.*;


public class BankingDialog extends JDialog {
    private User user;
    private BankUserGui bankUserGui;
    private JLabel currentBalanceLabel ,enterAmountLabel ,enterUserTransferLabel  ;
    private JTextField amountField , userTransferField;
    private JButton actionButton;

    public BankingDialog(BankUserGui bankUserGui,User user) {
        setSize(400, 400);
        //set the location at the center of the parent window
        setLocationRelativeTo(bankUserGui);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);

        setLayout(null);

        //need reference to our gui so we can update current balance after deposit/withdraw
        this.bankUserGui = bankUserGui;
        //need reference to our user so we can update the balance in the database
        this.user = user;
    }


    public void addBalance(){

        //balance label
        currentBalanceLabel = new JLabel("Current Balance: $" + user.getBalance());
        currentBalanceLabel.setBounds(0, 10, getWidth()-20, 20);
        currentBalanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        //enter amount label
        enterAmountLabel = new JLabel("Enter Amount: ");
        enterAmountLabel.setBounds(0, 50, getWidth()-20, 20);
        enterAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        //amount field

        amountField = new JTextField();
        amountField.setBounds(15, 80, getWidth()-50, 40);
        amountField.setFont(new Font("Arial", Font.BOLD, 20));
        amountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(amountField);

    }

    public void addActionButton(String action){
        actionButton = new JButton(action);
        actionButton.setBounds(15, 300, getWidth()-50, 40);
        actionButton.setFont(new Font("Arial", Font.BOLD, 20));
        add(actionButton);
    }

    public void addUserTrasferfield(){
        //enter user transfer label
        enterUserTransferLabel = new JLabel("Enter User to Transfer: ");
        enterUserTransferLabel.setBounds(0, 160, getWidth()-20, 20);
        enterUserTransferLabel.setFont(new Font("Arial", Font.BOLD, 16));
        enterUserTransferLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserTransferLabel);

        //user transfer field
        userTransferField = new JTextField();
        userTransferField.setBounds(15, 200, getWidth() - 50, 40);
        userTransferField.setFont(new Font("Arial", Font.BOLD, 20));
        userTransferField.setHorizontalAlignment(SwingConstants.CENTER);

        add(userTransferField);
    }

}


