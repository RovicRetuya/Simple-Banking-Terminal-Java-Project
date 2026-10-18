package OOPPROJECT;

// public class CheckingAccount extends Account {
//     private double overdraftLimit;

    
//     public CheckingAccount(String accountName, int accountID, String accountEmail, String address, String telNumber, String accountPassword, double overdraftLimit) {
//         super(accountName, accountID, accountEmail, address, telNumber, accountPassword);
//         this.overdraftLimit = overdraftLimit;
//     }
    
//     public void setOverdraftLimit(double overdraftLimit) {
//         this.overdraftLimit = overdraftLimit;
//     }
    
//     public double getOverdraftLimit() {
//         return overdraftLimit;
//     }
// }

public class CheckingAccount extends Account {
    private double overdraftLimit;
    private double borrowedAmount = 0;

    public CheckingAccount(String accountName, int accountID, String accountEmail, String address, String telNumber, 
    String accountPassword, double accountBalance, double overdraftLimit) {
        
        super(accountName, accountID, accountEmail, address, telNumber, accountPassword);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getBorrowedAmount() {
        return borrowedAmount;
    }

    public void setBorrowedAmount(double borrowedAmount) {
        this.borrowedAmount = borrowedAmount;
    }
}

