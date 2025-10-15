import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

public class BankApp {
    private static final String USERS_FILE = "users.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final Scanner input = new Scanner(System.in);

    static class User {
        String username;
        String passwordHash;
        String salt;

        User(String username, String passwordHash, String salt) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.salt = salt;
        }
    }

    static class Account {
        String username;
        String accountNumber;
        double balance;

        Account(String username, String accountNumber, double balance) {
            this.username = username;
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }

    private static final List<User> users = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        loadUsers();
        loadAccounts();

        System.out.println("===============================");
        System.out.println("         BANK APP SYSTEM       ");
        System.out.println("===============================");
        System.out.println("Student: Takudzwa Mafigu");
        System.out.println("Reg No: H240410M\n");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String option = input.nextLine();

            switch (option) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    saveUsers();
                    saveAccounts();
                    System.out.println("Session ended. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid selection.\n");
            }
        }
    }

    // ========== USER OPERATIONS ==========
    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = input.nextLine();

        if (findUser(username) != null) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Confirm password: ");
        String confirm = input.nextLine();

        if (!password.equals(confirm)) {
            System.out.println("Passwords don't match!");
            return;
        }

        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        users.add(new User(username, hash, salt));

        saveUsers();
        System.out.println("Account registered successfully!");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        User user = findUser(username);
        if (user == null || !user.passwordHash.equals(hashPassword(password, user.salt))) {
            System.out.println("Incorrect username or password.\n");
            return;
        }

        System.out.println("\nWelcome back, " + username + "!");
        showMenu(username);
    }

    // ========== ACCOUNT MENU ==========
    private static void showMenu(String username) {
        Account acc = findAccount(username);
        if (acc == null) {
            acc = new Account(username, generateAccountNumber(), 0.0);
            accounts.add(acc);
        }

        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Logout");
            System.out.print("Select option: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    System.out.printf("Your balance: $%.2f%n", acc.balance);
                    break;
                case "2":
                    System.out.print("Amount to deposit: ");
                    double deposit = Double.parseDouble(input.nextLine());
                    acc.balance += deposit;
                    saveAccounts();
                    System.out.println("Deposit successful!");
                    break;
                case "3":
                    System.out.print("Amount to withdraw: ");
                    double withdrawal = Double.parseDouble(input.nextLine());
                    if (withdrawal > acc.balance) {
                        System.out.println("Insufficient funds.");
                    } else {
                        acc.balance -= withdrawal;
                        saveAccounts();
                        System.out.println("Withdrawal complete.");
                    }
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // ========== FILE HANDLING ==========
    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1], parts[2]));
            }
        } catch (IOException ignored) {}
    }

    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User u : users)
                writer.println(u.username + "," + u.passwordHash + "," + u.salt);
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }

    private static void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                accounts.add(new Account(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        } catch (IOException ignored) {}
    }

    private static void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account a : accounts)
                writer.println(a.username + "," + a.accountNumber + "," + a.balance);
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    // ========== HELPERS ==========
    private static User findUser(String username) {
        for (User u : users)
            if (u.username.equals(username))
                return u;
        return null;
    }

    private static Account findAccount(String username) {
        for (Account a : accounts)
            if (a.username.equals(username))
                return a;
        return null;
    }

    private static String generateSalt() {
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    private static String generateAccountNumber() {
        Random rand = new Random();
        return "BNK" + (100000 + rand.nextInt(900000));
    }
}
