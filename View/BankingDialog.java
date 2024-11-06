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

        //not to be able to interact with the parent window
        setModal(true);

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
        // Container where we will store each transaction
        pastTranslationPanel = new JPanel();
        // Set layout to stack components vertically
        pastTranslationPanel.setLayout(new BoxLayout(pastTranslationPanel, BoxLayout.Y_AXIS));

        // Add scrollability to the container
        JScrollPane scrollPane = new JScrollPane(pastTranslationPanel);

        // Set vertical scroll bar to show only when necessary
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        // Perform DB call to retrieve all of the past transactions and store in ArrayList
        pastTransactions = ControllerJDBC.getTransaction(user);

        // Check if the transactions list is empty or null
        if (pastTransactions == null || pastTransactions.isEmpty()) {
            System.out.println("No past transactions found.");
        }

        // Iterate through the list and add to the GUI
        for (Transaction transaction : pastTransactions) {
            // Create a container to store an individual transaction
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            // Create transaction type label
            JLabel transactionTypeLabel = new JLabel(transaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // Create transaction amount label
            JLabel transactionAmountLabel = new JLabel("$" + transaction.getAmount());
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // Create transaction date label
            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // Add to the container
            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST); // Place on the west side
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST); // Place on the east side
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH); // Place on the south side

            // Set white background and black border for visual separation
            pastTransactionContainer.setBackground(Color.WHITE);
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Add transaction component to the transaction panel
            pastTranslationPanel.add(pastTransactionContainer);
        }

        // Add the scroll pane to the dialog or JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Ensure it's added to the main content pane

        // Revalidate and repaint to refresh the layout and display
        revalidate();
        repaint();
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
        currentBalanceLabel.setText("Balance: $" + user.getBalance());

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


