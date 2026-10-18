package OOPPROJECT;

import java.util.ArrayList;

public class Account {
    private String accountName;
    private String address;
    private String accountEmail;
    private int accountID;
    private String telNumber;
    private String accountPassword;
    private double accountBalance;
    private ArrayList<String> transactionHistory;


    Account(String accountName, int accountID, String accountEmail,
     String address, String telNumber, String accountPassword){

        this.accountName = accountName;
        this.accountID = accountID;
        this.accountEmail = accountEmail;
        this.address = address;
        this.telNumber = telNumber;
        this.accountPassword = accountPassword;
        this.transactionHistory = new ArrayList<>(); 

    }
    public Account() {
        this.transactionHistory = new ArrayList<>(); 
    }

    public String getAccountName() {
        return accountName;
    }
    public int getAccountID() {
        return accountID;
    }
    public String getAddress(){
        return address;
    }
    public String getAccountEmail(){
        return accountEmail;
    }
    public String getTelNumber(){
        return telNumber;
    }
    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setAccountEmail(String accountEmail){
        this.accountEmail = accountEmail;
    }
    public void setTelNumber(String telNumber){
        this.telNumber = telNumber;
    }
    public void setAccountID(int accountID){
        this.accountID = accountID;
    }
    public void setAccountBalance(double accountBalance){
        this.accountBalance = accountBalance;
    }
    public void setAccountPassword(String accountPassword){
        this.accountPassword = accountPassword;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public void showAccountStatement(){
        System.out.println("      ACCOUNT STATEMENT       ");
        System.out.println("Name           :" + accountName);
        System.out.println("Account ID     :" + accountID);
        System.out.println("Email          :" + accountEmail);
        System.out.println("Address        :" + address);
        System.out.println("Tel. Number    :" + telNumber);
    }

    public void addTransactionHistory(String transactionHistory){
        this.transactionHistory.add(transactionHistory);
    }

    public void getTransactionHistory(){
        if(transactionHistory.isEmpty()){
            System.out.println("No Transaction History.");
        }else{
            for(String trasaction: transactionHistory){
                System.out.println(trasaction);
            }
        }
    }
}
