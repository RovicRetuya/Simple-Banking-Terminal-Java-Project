package OOPPROJECT;

import java.util.ArrayList;
import java.util.Scanner;

public interface AccountOperations {

    void deposit(Account account, Scanner sc);
    void transfer(Account account, Scanner sc, ArrayList<Account> accounts);
    void withdraw(Account account, Scanner sc);
    void accountStatement(Account account);
    void transactionHistory(Account account);
    
}
