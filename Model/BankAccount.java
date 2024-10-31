package Model;

public class BankAccount {
    private User user;

    public BankAccount(User user) {
        this.user = user;
    }

    public void deposit(double amount) {  }
    public void withdraw(double amount) { }
    public void transfer(User recipient, double amount) {  }
    public Transaction getLastTransactions(int count) {
        return null;
    }

}
