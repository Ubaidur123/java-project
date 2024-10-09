import java.util.ArrayList;
import java.util.Scanner;

class ATM {
    private double balance;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    public ATM(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            System.out.println("Successfully withdrew: $" + amount);
        } else {
            System.out.println("Invalid or insufficient amount.");
        }
    }

    public void transfer(double amount, ATM receiver) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to another account");
            System.out.println("Successfully transferred: $" + amount);
        } else {
            System.out.println("Invalid or insufficient amount.");
        }
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create an ATM account with an initial balance
        ATM userAccount = new ATM(1000);
        ATM secondAccount = new ATM(500);  // Optional second account for transfer example

        boolean quit = false;

        while (!quit) {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userAccount.printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    userAccount.transfer(transferAmount, secondAccount);
                    break;
                case 5:
                    quit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }

        scanner.close();
    }
}
