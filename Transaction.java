package OOPPROJECT;

import java.util.ArrayList;
import java.util.Scanner;

public class Transaction implements AccountOperations {

    

    public void goTransaction(Scanner sc, Account currentAccount, ArrayList<Account> accounts, SavingsAccount savingsAccount, CheckingAccount checkingAccount){

        
        while (true) {
            if (savingsAccount != null) {
               System.out.println("Savings Account");  
            } else if (currentAccount instanceof CheckingAccount) {
                System.out.println("Checking Account");
            }
            
            System.out.println("Current Balance: " + currentAccount.getAccountBalance());
            if (currentAccount instanceof CheckingAccount) {
                CheckingAccount checkingAcc = (CheckingAccount) currentAccount;
                System.out.println("Overdraft Limit: " + (checkingAcc.getOverdraftLimit() - checkingAcc.getBorrowedAmount()));
            }
            System.out.println();
            System.out.println("Choose options: "  );
            System.out.println("(1) Deposit");
            System.out.println("(2) Withdraw");
            System.out.println("(3) Transfer");
            System.out.println("(4) Account Statement");
            System.out.println("(5) Transaction History");
            System.out.println("(6) Manage Account");
            System.out.println("(7) Logout");
            
            
            int opt = 0;
            boolean isValid = false;

            while (!isValid) {
                try{
                    System.out.print("Enter here: ");
                    opt = sc.nextInt();
                    sc.nextLine();
                    isValid = true;
                }catch(Exception e){
                    System.out.println("Invalid input. Please enter a number between 1 and 7.");
                    sc.nextLine(); 
                }
            }
          
            
            switch (opt) {
                case 1:
                    deposit(currentAccount, sc);
                    break;
                
                case 2:
                    if(currentAccount.getAccountBalance() == 0 ){
                        System.out.println();
                        System.out.println("You have 0.00 amount to your account.");
                        System.out.println("Try another option or Deposit first.");
                    }else{
                        withdraw(currentAccount, sc);
                    }
                    break;

                case 3:
                    if(currentAccount.getAccountBalance() == 0){
                        System.out.println("You have 0.00 amount to your account.");
                        System.out.println("Try another option or Deposit first.");
                    }else{
                        transfer(currentAccount, sc, accounts);;
                    }
                    break;

                case 4:
                    accountStatement(currentAccount);
                    break;
                
                case 5: 
                    transactionHistory(currentAccount);
                    break;

                case 6:
                    manageAccount(currentAccount, sc);
                    break;

                case 7: 
                    System.out.println("Successfully logout to your acount.");
                    System.out.println();
                    return;

                default:
                    System.out.println("Invalid Input");
                    break;
            }
            System.out.println();
        }
    }

//                                                                          DEPOSIT METHOD
    public void deposit(Account currentAccount, Scanner sc) {
        System.out.println();
        System.out.print("Enter amount to Deposit: ");
        double depositAmount = sc.nextDouble();
        sc.nextLine();
    
        while (depositAmount <= 0) {
            System.out.println();
            System.out.println("The amount must be greater than zero.");
            System.out.print("Enter a valid amount: ");
            depositAmount = sc.nextDouble();
            sc.nextLine();
        }
    
        if (currentAccount instanceof CheckingAccount) {
            CheckingAccount checkingAccount = (CheckingAccount) currentAccount;
    
            if (checkingAccount.getBorrowedAmount() > 0) {
                double remainingBorrowed = checkingAccount.getBorrowedAmount() - depositAmount;
                if (remainingBorrowed > 0) {
                    checkingAccount.setBorrowedAmount(remainingBorrowed);
                    System.out.println();
                    System.out.println("Part of your deposit was used to repay your overdraft.");
                }else {
                    currentAccount.setAccountBalance(Math.abs(remainingBorrowed));
                    checkingAccount.setBorrowedAmount(0);
                    System.out.println();
                    System.out.println("Your overdraft has been fully repaid.");
                }
            } else {
                currentAccount.setAccountBalance(currentAccount.getAccountBalance() + depositAmount);
            }


        } else {    
            currentAccount.setAccountBalance(currentAccount.getAccountBalance() + depositAmount);
        }
    
        System.out.println("Successfully Deposited!");
        System.out.println("Updated Balance: " + currentAccount.getAccountBalance());
        currentAccount.addTransactionHistory("Deposited amount: " + depositAmount);
        System.out.println();
    }
    

//                                                                          WITHDRAW METHOD
    // public void withdraw(Account currentAccount, Scanner sc){
    //         System.out.println();
    //         System.out.println("WITHDRAWING...");
    //         System.out.print("Enter amount to Withdraw: ");
    //         double withdrawAmount = sc.nextDouble();
    //         sc.nextLine();
    
    //         while(withdrawAmount > currentAccount.getAccountBalance() || withdrawAmount <=0){
    //             if(withdrawAmount <=0){
    //                 System.out.println("Invalid Amount...");
    //             }else{
    //                 System.out.println("Insufficient Balance...");
    //             }
    
    //             System.out.print("Enter again: ");
    //             withdrawAmount = sc.nextDouble();
    //             sc.nextLine();
    
    //             }
    
    //                 currentAccount.setAccountBalance(currentAccount.getAccountBalance() - withdrawAmount);
    //                 System.out.println();
    //                 System.out.println("Successfully Withdraw..");
    //                 System.out.println("Updated Balance: " + currentAccount.getAccountBalance());
    //                 currentAccount.addTransactionHistory("Withdrew amount: " + withdrawAmount);
    //                 System.out.println();
    //     }
    public void withdraw(Account currentAccount, Scanner sc) {
        System.out.println();
        System.out.println("WITHDRAWING...");
        System.out.print("Enter amount to Withdraw: ");
        double withdrawAmount = sc.nextDouble();
        sc.nextLine();
    
        if (currentAccount instanceof CheckingAccount) {
            CheckingAccount checkingAccount = (CheckingAccount) currentAccount;
            double totalAvailable = currentAccount.getAccountBalance() + checkingAccount.getOverdraftLimit() - checkingAccount.getBorrowedAmount();
    
            while (withdrawAmount > totalAvailable || withdrawAmount <= 0) {
                if (withdrawAmount > totalAvailable) {
                    System.out.println("Insufficient funds (including overdraft limit).");
                } else {
                    System.out.println("Invalid Amount...");
                }
                System.out.print("Enter again: ");
                withdrawAmount = sc.nextDouble();
                sc.nextLine();
            }
    
            if (withdrawAmount > currentAccount.getAccountBalance()) {
                double overdraftUsed = withdrawAmount - currentAccount.getAccountBalance();
                checkingAccount.setBorrowedAmount(checkingAccount.getBorrowedAmount() + overdraftUsed);
                currentAccount.setAccountBalance(0);
            } else {
                currentAccount.setAccountBalance(currentAccount.getAccountBalance() - withdrawAmount);
            }


        } else {
            while (withdrawAmount > currentAccount.getAccountBalance() || withdrawAmount <= 0) {
                if (withdrawAmount <= 0) {
                    System.out.println("Invalid Amount...");
                } else {
                    System.out.println("Insufficient Balance...");
                }
                System.out.print("Enter again: ");
                withdrawAmount = sc.nextDouble();
                sc.nextLine();
            }
            currentAccount.setAccountBalance(currentAccount.getAccountBalance() - withdrawAmount);
        }
    
        System.out.println();
        System.out.println("Successfully Withdrawn.");
        System.out.println("Updated Balance: " + currentAccount.getAccountBalance());
        currentAccount.addTransactionHistory("Withdrew amount: " + withdrawAmount);
        System.out.println();
    }
    
    
//                                                                      TRANSFER METHOD

    // public void transfer(Account currentAccount, Scanner sc, ArrayList<Account> accounts){
    //     System.out.println();
    //     System.out.println("Transferring...");
    //     System.out.print("Enter the Account ID you want to transfer amount to: ");
    //     int accountID = sc.nextInt();
    //     sc.nextLine();
        
    //     Account targetAccount = null;
        
    //     for(Account account: accounts){
    //         if(accountID == account.getAccountID()){
    //             targetAccount = account;
    //             break;
    //         }
    //     }

    //     if(targetAccount == null){
    //         System.out.println();
    //         System.out.println("Account not found.");
    //         System.out.println("Transferring canceled");
    //         return;
    //     }

    //     System.out.print("Enter amount to Transfer: "); 
    //     double transferAmount = sc.nextDouble();
    //     sc.nextLine();

    //     while(transferAmount > currentAccount.getAccountBalance() || transferAmount <=0){
    //         if(transferAmount > currentAccount.getAccountBalance()){
    //             System.out.println("Insufficient Balance (you are trying to transfer bigger ammount than your balance. )");
    //         }else{
    //             System.out.println("Invalid Ammount.");
    //         }
        
    //         System.out.print("Enter again: ");
    //         transferAmount = sc.nextDouble();
    //         sc.nextLine();
    //         }

    //         currentAccount.setAccountBalance(currentAccount.getAccountBalance()- transferAmount);
    //         targetAccount.setAccountBalance(targetAccount.getAccountBalance() + transferAmount);
    //         System.out.println();
    //         System.out.println("Transfer Successful!");
    //         currentAccount.addTransactionHistory("Transfered: " + transferAmount + " to " + targetAccount.getAccountName() + " Account ID:" + accountID  );
    //         System.out.println("Your Updated Balance: " + currentAccount.getAccountBalance());
    //         System.out.println(targetAccount.getAccountBalance() + " successfully recieved by " + targetAccount.getAccountName());
    // }
    public void transfer(Account currentAccount, Scanner sc, ArrayList<Account> accounts) {
        System.out.println();
        System.out.println("Transferring...");
        System.out.print("Enter the Account ID you want to transfer amount to: ");
        int accountID = sc.nextInt();
        sc.nextLine();
    
        Account targetAccount = null;
    
        for (Account account : accounts) {
            if (accountID == account.getAccountID()) {
                targetAccount = account;
                break;
            }
        }
    
        if (targetAccount == null) {
            System.out.println();
            System.out.println("Account not found.");
            System.out.println("Transferring canceled.");
            return;
        }
    
        System.out.print("Enter amount to Transfer: ");
        double transferAmount = sc.nextDouble();
        sc.nextLine();
    
        if (currentAccount instanceof CheckingAccount) {
            CheckingAccount checkingAccount = (CheckingAccount) currentAccount;
            double totalAvailable = currentAccount.getAccountBalance() + checkingAccount.getOverdraftLimit() - checkingAccount.getBorrowedAmount();
    
            while (transferAmount > totalAvailable || transferAmount <= 0) {
                if (transferAmount > totalAvailable) {
                    System.out.println("Insufficient funds (including overdraft limit).");
                } else {
                    System.out.println("Invalid Amount.");
                }
                System.out.print("Enter again: ");
                transferAmount = sc.nextDouble();
                sc.nextLine();
            }
    
            if (transferAmount > currentAccount.getAccountBalance()) {
                double overdraftUsed = transferAmount - currentAccount.getAccountBalance();
                checkingAccount.setBorrowedAmount(checkingAccount.getBorrowedAmount() + overdraftUsed);
                currentAccount.setAccountBalance(0);
            } else {
                currentAccount.setAccountBalance(currentAccount.getAccountBalance() - transferAmount);
            }

            
        } else {
            while (transferAmount > currentAccount.getAccountBalance() || transferAmount <= 0) {
                if (transferAmount > currentAccount.getAccountBalance()) {
                    System.out.println("Insufficient Balance.");
                } else {
                    System.out.println("Invalid Amount.");
                }
                System.out.print("Enter again: ");
                transferAmount = sc.nextDouble();
                sc.nextLine();
            }
            currentAccount.setAccountBalance(currentAccount.getAccountBalance() - transferAmount);
        }
    
        targetAccount.setAccountBalance(targetAccount.getAccountBalance() + transferAmount);
        System.out.println();
        System.out.println("Transfer Successful!");
        currentAccount.addTransactionHistory("Transferred: " + transferAmount + " to " + targetAccount.getAccountName() + " Account ID: " + accountID);
        System.out.println("Your Updated Balance: " + currentAccount.getAccountBalance());
        System.out.println(targetAccount.getAccountBalance() + " successfully received by " + targetAccount.getAccountName());
    }
    


//                                                                          ACCOUNT STATEMENT METHOD
    public void accountStatement(Account account){
        System.out.println();
        account.showAccountStatement();
        if (account instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.calculateInterest();

        } else if (account instanceof CheckingAccount) {
            CheckingAccount checkingAccount = (CheckingAccount) account;
            checkingAccount.getOverdraftLimit();
        }
    }

//                                                                          TRANSACTION HISTORY METHOD
    public void transactionHistory(Account account){
        System.out.println();
        System.out.println("Transaction History");
        System.out.println("*******************");
        account.getTransactionHistory();
    }

//                                                                          MANAGE ACCOUNT METHOD
    public void manageAccount(Account account, Scanner sc){
        System.out.println(     );
        System.out.println("Account Management");
        System.out.println("******************");
        System.out.println("Your current details: ");
        account.showAccountStatement();

        System.out.println("******************");
        System.out.println("Do you want to make some changes about your details?");
        System.out.print("If 'yes' press (Y) else press (N): ");
        char changes = sc.nextLine().charAt(0);

        switch (Character.toUpperCase(changes)) {
             
            case 'Y':
                System.out.println();
                System.out.println("Options to change: ");
                System.out.println("(a) Change your <Name>");
                System.out.println("(b) Change your <Email>");
                System.out.println("(c) Change your <Address>");
                System.out.println("(d) change your <Tel. number>");
                System.out.println("(e) change your <Password>");
                System.out.print("Enter here: ");
                char opt = sc.nextLine().charAt(0);

                switch (opt) {
                    case 'a':
                        System.out.println("Changing Name...");
                        System.out.print("Enter changes: ");
                        account.setAccountName(sc.nextLine());
                        break;
                
                    case 'b':
                        System.out.println("Changing Email...");
                        System.out.print("Enter changes: ");
                        account.setAccountEmail(sc.nextLine());
                        break;
                        
                    case 'c':
                        System.out.println("Changing Addres...");
                        System.out.print("Enter changes: ");
                        account.setAddress(sc.nextLine());
                        break;

                    case 'd':
                        System.out.println("Changing Tel. number...");
                        System.out.print("Enter changes: ");
                        account.setTelNumber(sc.nextLine());
                        break;

                    case 'e':
                        System.out.println("Changing password...");
                        System.out.print("Enter your old password: ");
                        String oldPassword = sc.nextLine();

                        while (!oldPassword.equals(account.getAccountPassword())) {
                            System.out.println("Incorrect old password");
                            System.out.print("Enter again: ");
                            oldPassword = sc.nextLine();
                        }

                        System.out.print("Enter new password: ");
                        String nPassword = sc.nextLine();

                        System.out.print("Confirm new password: ");
                        String cPassword = sc.nextLine();
                        
                        while(!nPassword.equals(cPassword)){
                            System.out.println("Password do not match.");
                            System.out.println("Try again: ");
                            cPassword = sc.nextLine();
                        }

                        account.setAccountPassword(cPassword);
                        System.out.println("Successfully Changed.");
                        break;

                    default:
                        System.out.println("Invalid option");
                        System.out.println();
                        break;
                }
                System.out.println();
                System.out.println("Updated account details: ");
                account.showAccountStatement();
                break;
        
            case 'N':
                return;
                
            default:
            System.out.println("Invalid option");
                break;
            
            
        }
    }
}

