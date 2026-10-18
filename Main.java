package OOPPROJECT;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
   static ArrayList<Account> accounts   = new ArrayList<>();
   static Transaction transaction = new Transaction();
   static ArrayList<SavingsAccount> savingsAccounts = new ArrayList<>();
   static ArrayList<CheckingAccount> checkingAccounts = new ArrayList<>();
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
      
        while (true) {
           System.out.println("*****************");
           System.out.println("WELCOME TO MYBANK");
           System.out.println("*****************");
           System.out.println();
           System.out.println("Select Options: ");
           System.out.println("(A) Create Account");
           System.out.println("(B) Sign in");
           System.out.println("(E) Exit");
           System.out.println("*****************");
           System.out.print("Type option: ");
           char opt = sc.nextLine().charAt(0);
    
           switch (Character.toUpperCase(opt)) {
            case 'A':
                getInfo(sc);
                break;
            case 'B':
                if (accounts.isEmpty() && savingsAccounts.isEmpty() && checkingAccounts.isEmpty()) {
                System.out.println();
                System.out.println("Can't login, no accounts existing in the system.");
                System.out.println("Please create an account first.");
                System.out.println();
                break; 
            }
                signIn(sc);
                break;   
            case 'E': 
                System.exit(0);
            default:
                System.out.println();
                System.out.println("Invalid Option");
                System.out.println();
                break;
           }
    
        }
       }

//                              SIGN IN METHOD
    public static void signIn(Scanner sc){

        Account foundAccount = null;
        SavingsAccount foundSavingsAccount = null;
        CheckingAccount foundCheckingAccount = null;

        while(foundAccount == null){
            System.out.print("Enter your Account ID: ");
            int accountID = sc.nextInt();
            sc.nextLine();

            for(Account testAccount: accounts ){
                if(testAccount.getAccountID() == accountID){
                    foundAccount = testAccount;
                    break;
                }
                
            }
            for(SavingsAccount savingsAccount : savingsAccounts){
                if(savingsAccount.getAccountID() == accountID){
                    foundSavingsAccount = savingsAccount;
                    break;
                }

            }
            
            for (CheckingAccount checkingAccount : checkingAccounts) {
                if (checkingAccount.getAccountID() == accountID) {
                    foundCheckingAccount = checkingAccount; 
                    break;
                }
            }

            if(foundAccount == null){
                System.out.println("Account ID is not found. Please try again.");
                System.out.println();
            }
        }


        boolean isTrue = false;
        while(!isTrue){
            System.out.print("Enter your password: ");
            String accountPassword = sc.nextLine();

            if(foundAccount.getAccountPassword().equals(accountPassword)){
                isTrue = true;
                System.out.println();
                System.out.println("Sign in Successfully!!");
                System.out.println("Welcome "+ foundAccount.getAccountName() +"!");
                System.out.println();

                transaction.goTransaction(sc, foundAccount,accounts, foundSavingsAccount, foundCheckingAccount);

            }else{
                System.out.println("Incorrect Password, Please try again");
                System.out.println();
            }
        }
    }


//                                                          CREATE ACCOUNT METHOD
    public static void getInfo(Scanner sc){
        System.out.println();
        System.out.println("Creating Account...");
        System.out.println();
        System.out.print("Enter your name: ");
        String accountName = sc.nextLine();

        
        int accountID =0;
        while(true){
            try{
                System.out.print("Enter your Account ID: ");
                accountID = sc.nextInt();
                sc.nextLine();

                boolean idExists = false;
//                                                                 ITERATION OF EVERY ACCOUNT
 
                for (Account account : accounts) {
                    if (account.getAccountID() == accountID) {
                        idExists = true;
                        break;
                    }
                }
                
                for (SavingsAccount savingsAccount : savingsAccounts) {
                    if (savingsAccount.getAccountID() == accountID) {
                        idExists = true;
                        break;
                    }
                }

                for (CheckingAccount checkingAccount : checkingAccounts) {
                    if (checkingAccount.getAccountID() == accountID) {
                        idExists = true;
                        break;
                    }
                }
    
                if (idExists) {
                    System.out.println("Account ID already exists. Please choose a different ID.");
                } else {
                    break; 
                }

            }catch (Exception e){
                System.out.println("Invalid input. Enter a numeric value");
                sc.nextLine(); 
            }
        }

        System.out.print("Enter your Email: ");
        String accountEmail = sc.nextLine();

        System.out.print("Enter your Address: ");
        String address = sc.nextLine();

        System.out.print("Enter your Tel. number: ");
        String telNumber = sc.nextLine();

        System.out.print("Enter your Password: ");
        String accountPassword = sc.nextLine();
    
        System.out.println();
        System.out.println("Enter Account Type: ");
        System.out.println("<1> Savings Account");
        System.out.println("<2> Checking Account");
        
        int accType = 0;
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print("Type here: ");
                accType = sc.nextInt();
                sc.nextLine();
                if (accType == 1 || accType == 2) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a valid account type (1 or 2).");
                    System.out.println();
                }
            }catch(Exception e){
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                sc.nextLine();
            }
        }
        
        switch (accType) {
            case 1:
                System.out.println();
                System.out.print("Enter Interest Rate for Savings Account: ");
                double interestRate = sc.nextDouble();
                while(interestRate > 10 || interestRate <= 0){
                    System.out.println("The interest rate you entered is too high or invalid.");
                    System.out.print("Please enter a rate between 0 and 10%: ");
                    interestRate = sc.nextDouble();
                }
                sc.nextLine();
                
                SavingsAccount newSavingsAccount = new SavingsAccount(accountName, accountID, accountEmail, address, telNumber, accountPassword, interestRate);
                savingsAccounts.add(newSavingsAccount);
                accounts.add(newSavingsAccount);
                break;
            
                case 2:
                System.out.print("Enter Overdraft Limit for Checking Account: ");
                double overdraftLimit = sc.nextDouble();
                sc.nextLine();

                CheckingAccount newCheckingAccount = new CheckingAccount(accountName, accountID, accountEmail, address, telNumber, accountPassword, 0,overdraftLimit);
                checkingAccounts.add(newCheckingAccount);
                accounts.add(newCheckingAccount);  
                break;
        
            default:
                System.out.println("Invalid account type selected.");
                break;
        }

        System.out.println();
        System.out.println("ACCOUNT CREATED!");
        System.out.println();

    }
}
