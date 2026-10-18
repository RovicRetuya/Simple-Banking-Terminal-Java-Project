package OOPPROJECT;

public class SavingsAccount extends Account {
    private double interestRate; 

    public SavingsAccount(String accountName, int accountID, String accountEmail,
                          String address, String telNumber, String accountPassword, 
                          double interestRate) {
        super(accountName, accountID, accountEmail, address, telNumber, accountPassword);
        this.interestRate = interestRate;
    }

    public SavingsAccount() {
        super();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void calculateInterest() {
        double interest = getAccountBalance() * (interestRate / 100);
        setAccountBalance(getAccountBalance() + interest);
        addTransactionHistory("Interest added: " + interest);
        System.out.println("Interest of " + interest + " added to your Savings Account.");
        System.out.println("Total of " + getAccountBalance());
        System.out.println();
    }
}
