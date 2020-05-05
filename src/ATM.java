import java.util.Scanner;

public class ATM {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Bank theBank = new Bank("CIB");
        User newUser = theBank.addUser("Mohamed " , "Aly" , "1234" );
        Account newAccount  = new Account("Checking" , newUser , theBank);
        theBank.addAccount(newAccount);
        newUser.addAccount(newAccount);
        User currUser ;
        while(true){
            currUser = ATM.mainMenuPrompt(theBank , input);
            ATM.printUserMenu(currUser , input);

        }
    }


    public static User mainMenuPrompt(Bank theBank,Scanner input){
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\nWelcome to %s Bank\n ", theBank.getName());
            System.out.print("Enter user ID : ");
            userID = input.nextLine();
            System.out.print("\n Enter pin : ");
            pin = input.nextLine();
            authUser = theBank.userLog(userID,pin);
            if(authUser == null){
                System.out.println("Incorrect user ID/pin combination \n Please Try again");
            }
        }while(authUser == null);
        return authUser;
    }
    public static void printUserMenu(User theUser , Scanner input){
        theUser.printAccountSummary();
        int choice;
        do{
            System.out.printf("Welcome %s, What would you like to do ?" , theUser.getFirstName());
            System.out.println(" \n1) Show account transaction history ?");
            System.out.println(" 2) Withdrawl");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer ");
            System.out.println(" 5) Quit");
            System.out.print("Enter choice : ");
            choice = input.nextInt();
            if(choice < 1 || choice > 5){
                System.out.println("Please Enter a value from 1 - 5");
            }
        }while(choice < 1 || choice > 5);
        switch (choice){
            case 1 : ATM.showTransHistory(theUser,input);
            break;
            case 2 : ATM.WithdrawFund(theUser,input);
            break;
            case 3 : ATM.DepositFund(theUser,input);
            break;
            case 4 : ATM.TransferFund(theUser, input);
            break;
            case 5 : input.nextLine();
            break;
        }
        if(choice != 5){
            ATM.printUserMenu(theUser,input);
        }

    }
    public static void showTransHistory(User theUser,Scanner input){
        int anAcc;
        do{
            System.out.printf("Enter the number (1- %d) of the account whose transactions you want to see : "
            , theUser.numAccounts());
            anAcc = input.nextInt() -1 ;
            if(anAcc < 0 || anAcc >= theUser.numAccounts()){
                System.out.println("Invalid Account. Please try again");
            }

        }while(anAcc < 0 || anAcc >= theUser.numAccounts());
        theUser.printTransHistory(anAcc);
    }



    public static void TransferFund(User theUser, Scanner input){
        int fromAcc;
        int toAcc;
        double amount ;
        double accBal;
        do{
            System.out.printf(" Enter the number ( 1- %d) of the account to" +
                    " transfer from : ",theUser.numAccounts());
            fromAcc = input.nextInt() -1 ;
            if(fromAcc < 0 || fromAcc >= theUser.numAccounts()){
                System.out.println("Invalid Input , Please try again");
            }
        }while(fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(fromAcc);

        do{
            System.out.printf(" Enter the number ( 1- %d) of the account to" +
                    " transfer to : ",theUser.numAccounts());
            toAcc = input.nextInt() -1 ;
            if(toAcc < 0 || toAcc >= theUser.numAccounts()){
                System.out.println("Invalid Input , Please try again");
            }
        }while(toAcc < 0 || toAcc >= theUser.numAccounts());
        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accBal);
            amount = input.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount to transfer must be greater than zero");
            }
            if(amount > accBal){
                System.out.printf("Amount must not be greater than \n the balance " +
                        "of $ %.02f .\n" , accBal);
            }
        }while(amount < 0 || amount > accBal);
        theUser.addAcctTransaction(fromAcc , -1*amount , String.format(
                "Transfer to account %s",theUser.getAccountUUID(toAcc)));
        theUser.addAcctTransaction(toAcc, amount , String.format(
                "Transfer from account %s",theUser.getAccountUUID(fromAcc)));
    }
    public static void WithdrawFund(User theUser , Scanner input){
        int fromAcc;
        double amount ;
        double accBal;
        String memo;
        do{
            System.out.printf(" Enter the number ( 1- %d) of the account to" +
                    " withdraw from : ",theUser.numAccounts());
            fromAcc = input.nextInt() -1 ;
            if(fromAcc < 0 || fromAcc >= theUser.numAccounts()){
                System.out.println("Invalid Input , Please try again");
            }
        }while(fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(fromAcc);

        do{
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    accBal);
            amount = input.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount to withdraw must be greater than zero");
            }
            if(amount > accBal){
                System.out.printf("Amount must not be greater than \n the balance " +
                        "of $ %.02f .\n" , accBal);
            }
        }while(amount < 0 || amount > accBal);
        System.out.print( "Enter a memo (press Enter if you want to skip): ");
        input.nextLine();
        memo = input.nextLine();


        //do the withdraw
        theUser.addAcctTransaction(fromAcc , -1*amount , memo);

    }
    public static void DepositFund(User theUser,Scanner input){
        int toAcc;
        double amount ;
        double accBal;
        String memo;
        do{
            System.out.printf(" Enter the number ( 1- %d) of the account to" +
                    " deposit to : ",theUser.numAccounts());
            toAcc = input.nextInt() -1 ;
            if(toAcc < 0 || toAcc >= theUser.numAccounts()){
                System.out.println("Invalid Input , Please try again");
            }
        }while(toAcc < 0 || toAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(toAcc);

        do{
            System.out.print("Enter the amount to deposit :");
            amount = input.nextDouble();
            if(amount < 0 ){
                System.out.println("Amount to deposit must be greater than zero");
            }

        }while(amount < 0);
        System.out.print( "Enter a memo (press Enter if you want to skip) : ");
        input.nextLine();
        memo = input.nextLine();


        //do the Deposit
        theUser.addAcctTransaction(toAcc , amount , memo);

    }



}
