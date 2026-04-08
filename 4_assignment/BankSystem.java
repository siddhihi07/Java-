import java.io.*;
import java.util.*;

class MinimumBalanceException extends Exception {
    public MinimumBalanceException(String msg) { super(msg); }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String msg) { super(msg); }
}

class InvalidCustomerIDException extends Exception {
    public InvalidCustomerIDException(String msg) { super(msg); }
}

class NegativeAmountException extends Exception {
    public NegativeAmountException(String msg) { super(msg); }
}

class Customer implements Serializable {
    int cid;
    String cname;
    double amount;

    public Customer(int cid, String cname, double amount) {
        this.cid = cid;
        this.cname = cname;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ID: " + cid + " | Name: " + cname + " | Balance: Rs. " + amount;
    }
}

public class BankSystem {
    private static final String FILE_NAME = "bank_data.ser";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- BANKING MENU ---");
            System.out.println("1. Create Account");
            System.out.println("2. Withdraw Amount");
            System.out.println("3. Display All Records");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: withdrawAmount(); break;
                    case 3: displayRecords(); break;
                    case 4: System.exit(0);
                    default: System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void createAccount() throws Exception {
        System.out.print("Enter Customer ID (1-20): ");
        int cid = sc.nextInt();
        if (cid < 1 || cid > 20) throw new InvalidCustomerIDException("ID must be 1 to 20.");

        System.out.print("Enter Customer Name: ");
        sc.nextLine(); // Clear buffer
        String cname = sc.nextLine();

        System.out.print("Enter Initial Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) throw new NegativeAmountException("Amount must be positive.");
        if (amount < 1000) throw new MinimumBalanceException("Minimum Rs. 1000 required to open account.");

        List<Customer> list = readFromFile();
        list.add(new Customer(cid, cname, amount));
        writeToFile(list);
        System.out.println("Account created successfully!");
    }

    private static void withdrawAmount() throws Exception {
        System.out.print("Enter Customer ID: ");
        int cid = sc.nextInt();
        System.out.print("Enter Withdrawal Amount: ");
        double wth_amt = sc.nextDouble();

        if (wth_amt <= 0) throw new NegativeAmountException("Amount must be positive.");

        List<Customer> list = readFromFile();
        boolean found = false;

        for (Customer c : list) {
            if (c.cid == cid) {
                if (wth_amt > c.amount) throw new InsufficientFundsException("Insufficient balance!");
                c.amount -= wth_amt;
                found = true;
                break;
            }
        }

        if (found) {
            writeToFile(list);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Customer ID not found.");
        }
    }

    private static void displayRecords() {
        List<Customer> list = readFromFile();
        if (list.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Customer c : list) System.out.println(c);
        }
    }

    
    private static void writeToFile(List<Customer> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("File Write Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Customer> readFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Customer>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
