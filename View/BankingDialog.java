package View;

import Model.Transaction;
import Model.User;
import Controller.ControllerJDBC;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BankingDialog extends JDialog implements ActionListener {
    private User user;
    private BankUserGui bankUserGui;
    private JLabel currentBalanceLabel ,enterAmountLabel ,enterUserTransferLabel  ;
    private JTextField amountField , userTransferField;
    private JButton actionButton;
    private JPanel pastTranslationPanel;
    private ArrayList<Transaction> pastTransactions;


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
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserTransferfield(){
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

    public void addPastTransactionsComponents(){
        pastTranslationPanel = new JPanel();
        pastTranslationPanel.setLayout(new BoxLayout(pastTranslationPanel, BoxLayout.Y_AXIS));
        //add scroll ability
        JScrollPane scrollPane = new JScrollPane(pastTranslationPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        pastTranslationPanel.setBounds(0, 20, getWidth() - 15, getHeight() - 15);

        //perform db call to get the last 5 transactions
        pastTransactions = ControllerJDBC.getTransaction(user);
        //iterate through the transactions and add them to the panel
        for(int i = 0; i < pastTransactions.size(); i++){
            Transaction transaction = pastTransactions.get(i);

            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel = new JLabel(transaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));

            JLabel transactionAmountLabel = new JLabel("$" + transaction.getAmount());
            transactionAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));

            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Arial", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);

            pastTransactionContainer.setBackground(Color.WHITE);

            pastTranslationPanel.add(pastTransactionContainer);
        }


    }



    private void handleTransaction(String transactionType, double amountVal) {
        Transaction transaction;

        if (transactionType.equals("Deposit")) {
            user.setBalance(user.getBalance() + amountVal);
            //null for now, we will update this afterwards from the db
            transaction = new Transaction(user.getId(), transactionType, amountVal, null);

        }
        else {
            user.setBalance(user.getBalance() - amountVal);
            //null for now, we will update this afterwards from the db
            transaction = new Transaction(user.getId(), transactionType, (-amountVal), null);
        }
        if(ControllerJDBC.addTransactiontoDatabase(transaction) && ControllerJDBC.updateBalance(user)) {
            JOptionPane.showMessageDialog(this, transactionType + " Successfully");

            resetFieldsAndUpdateBalance();
        }
        else {
            JOptionPane.showMessageDialog(this, transactionType+ ": Error Occurred");
        }

    }

    private void resetFieldsAndUpdateBalance() {
        amountField.setText("");

        //only when transfering
        if(userTransferField!= null) {
            userTransferField.setText("");
        }

        //update the balance in the dialog
        currentBalanceLabel.setText("Current Balance: $" + user.getBalance());

        //update the balance in the main gui
        bankUserGui.getCurrentBalancField().setText("$" + user.getBalance());



    }

    private void handleTransfer(User user,String buttonPressed, double amountVal) {
        if(ControllerJDBC.transfer(user, userTransferField.getText(), amountVal)) {
            JOptionPane.showMessageDialog(this, buttonPressed + " Successfully");
            resetFieldsAndUpdateBalance();
        }
        else {
            JOptionPane.showMessageDialog(this, buttonPressed + ": Error Occurred");
        }


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        double amountVal = Double.parseDouble(amountField.getText());

        if(buttonPressed.equalsIgnoreCase("Deposit")){
            handleTransaction(buttonPressed, amountVal);
        }
        else{
            //check input amount is less than the current balance for withdraw or transfer
            int result = user.getBalance() < amountVal ? 1 : 0;
            if(result == 1){
                JOptionPane.showMessageDialog(this, "Insufficient Funds");
                return;
            }

            if(buttonPressed.equalsIgnoreCase("Withdraw")){
                handleTransaction(buttonPressed, amountVal);
            }
            else{
                String recipient = userTransferField.getText();

                handleTransfer(user, buttonPressed, amountVal);

            }

        }

    }
}


