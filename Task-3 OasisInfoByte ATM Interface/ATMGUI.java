import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class ATM {
    private BankAccount account;
    private List<Transaction> transactionHistory;

    public ATM(BankAccount account) {
        this.account = account;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean withdraw(double amount) {
        boolean success = account.withdraw(amount);
        if (success) {
            transactionHistory.add(new Transaction("Withdraw", amount));
        }
        return success;
    }

    public void deposit(double amount) {
        account.deposit(amount);
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public boolean transfer(double amount, BankAccount recipientAccount) {
        boolean success = account.transfer(amount, recipientAccount);
        if (success) {
            transactionHistory.add(new Transaction("Transfer", amount));
        }
        return success;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(double amount, BankAccount recipientAccount) {
        if (withdraw(amount)) {
            recipientAccount.deposit(amount);
            return true;
        } else {
            return false;
        }
    }
}

public class ATMGUI {
    private ATM atm;
    private JTextArea historyTextArea; 

    public ATMGUI(ATM atm) {
        this.atm = atm;

        JFrame frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(237, 237, 237));

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(237, 237, 237));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        JButton transferButton = new JButton("Transfer");
        JButton exitButton = new JButton("Exit");

        withdrawButton.setBackground(new Color(32, 191, 107));
        depositButton.setBackground(new Color(32, 191, 107));
        balanceButton.setBackground(new Color(32, 191, 107));
        transferButton.setBackground(new Color(32, 191, 107));
        exitButton.setBackground(new Color(32, 191, 107));

        withdrawButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        balanceButton.setForeground(Color.WHITE);
        transferButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        withdrawButton.setFont(buttonFont);
        depositButton.setFont(buttonFont);
        balanceButton.setFont(buttonFont);
        transferButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        historyTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        historyTextArea.setEditable(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(balanceButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                try {
                    double amount = Double.parseDouble(amountString);
                    boolean success = atm.withdraw(amount);
                    if (success) {
                        historyTextArea.append("Withdrawal successful. New balance: $" + atm.checkBalance() + "\n");
                    } else {
                        historyTextArea.append("Insufficient funds. Current balance: $" + atm.checkBalance() + "\n");
                    }
                    updateTransactionHistory();
                } catch (NumberFormatException ex) {
                    historyTextArea.append("Invalid input. Please enter a valid number.\n");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                try {
                    double amount = Double.parseDouble(amountString);
                    atm.deposit(amount);
                    historyTextArea.append("Deposit successful. New balance: $" + atm.checkBalance() + "\n");
                    updateTransactionHistory();
                } catch (NumberFormatException ex) {
                    historyTextArea.append("Invalid input. Please enter a valid number.\n");
                }
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyTextArea.append("Current balance: $" + atm.checkBalance() + "\n");
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to transfer:");
                try {
                    double amount = Double.parseDouble(amountString);
                    String recipientAmountString = JOptionPane.showInputDialog(frame, "Enter recipient's amount:");
                    double recipientAmount = Double.parseDouble(recipientAmountString);
                    BankAccount recipientAccount = new BankAccount(recipientAmount);
                    boolean success = atm.transfer(amount, recipientAccount);
                    if (success) {
                        historyTextArea.append("Transfer successful. New balance: $" + atm.checkBalance() + "\n");
                    } else {
                        historyTextArea.append("Transfer failed. Insufficient funds.\n");
                    }
                    updateTransactionHistory();
                } catch (NumberFormatException ex) {
                    historyTextArea.append("Invalid input. Please enter a valid number.\n");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.setVisible(true);
    }

    private void updateTransactionHistory() {
        List<Transaction> transactions = atm.getTransactionHistory();
        historyTextArea.setText(""); 
        for (Transaction transaction : transactions) {
            historyTextArea.append(transaction.getType() + ": $" + transaction.getAmount() + "\n");
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100000);
        ATM atm = new ATM(account);
        new ATMGUI(atm);
    }
}
