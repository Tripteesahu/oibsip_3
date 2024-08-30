package O;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// User class to represent a user account
class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

// Account class to manage account details and transactions
class Account {
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new TransactionHistory();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.addTransaction(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.addTransaction(new Transaction("Withdraw", amount, balance));
            return true;
        }
        return false;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.addTransaction(new Transaction("Transfer", amount, balance));
            return true;
        }
        return false;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return type + ": $" + amount + ", Balance after: $" + balanceAfter;
    }
}

// TransactionHistory class to manage and display transaction history
class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void display() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

// Main ATM class to run the application
public class ATM {
    private static User user;

    public static void main(String[] args) {
        // Sample user creation
        user = new User("user123", "1234", 1000.00);
        
        Scanner scanner = new Scanner(System.in);
        
        // User authentication
        System.out.print("Enter User ID: ");
        String userIdInput = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pinInput = scanner.nextLine();

        if (userIdInput.equals(user.getUserId()) && pinInput.equals(user.getPin())) {
            System.out.println("Login successful!");
            boolean running = true;

            while (running) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        user.getAccount().getTransactionHistory().display();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (user.getAccount().withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful!");
                        } else {
                            System.out.println("Insufficient funds!");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        user.getAccount().deposit(depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 4:
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        // For simplicity, we are transferring to the same account
                        if (user.getAccount().transfer(user.getAccount(), transferAmount)) {
                            System.out.println("Transfer successful!");
                        } else {
                            System.out.println("Insufficient funds!");
                        }
                        break;
                    case 5:
                        running = false;
                        System.out.println("Thank you for using the ATM!");
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN!");
        }

        scanner.close();
    }
}