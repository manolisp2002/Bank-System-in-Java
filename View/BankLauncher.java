package View;

import javax.swing.*;

public class BankLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginGui("Bank User Login").setVisible(true);

            }
        });
    }
}
