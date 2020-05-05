import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
public class User {
    /**
     * The First name of the User
     */
    private String firstName;
    /**
     * The Last name of the User
     */
    private String lastName;
    /**
     * The unique Universal id of the User
     */
    private String uuid;
    /**
     * The MD5 hash of the user's pin number
     */
    private byte pinHash[];
    /**
     *the list of Accounts of this user
     */
    private ArrayList<Account> accounts;


    public User(String firstName , String lastName , String pin, Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;
        // store the pin's MD5 hash , rather than the original value for
        // security issues
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // get a new Unique Universal ID
        this.uuid = theBank.getNewUserUUID();
        // create a new empty Arraylist of accounts for this user
        this.accounts = new ArrayList<Account>();
        // print log message
        System.out.printf(" New user %s , %s with ID %s created .\n", lastName ,firstName,this.uuid);
    }
    public void addAccount(Account account){
        this.accounts.add(account);
    }
    public String getUUID(){
        return uuid;
    }
    public boolean validatePin(String aPin){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash );
        }
        catch(NoSuchAlgorithmException e){
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void printAccountSummary(){
        System.out.printf("\n%s's accounts summary\n" , this.firstName);
        for(int i= 0 ; i < this.accounts.size() ; i++){
            System.out.printf("%d) %s \n", i+1,this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }
    public int numAccounts(){
        return this.accounts.size();
    }
    public void printTransHistory(int idx){
        this.accounts.get(idx).printTransHistory();
    }
    public double getAccBalance(int idx){
        return this.accounts.get(idx).getBalance();
    }
    public String getAccountUUID(int idx){
        return accounts.get(idx).getUUID();
    }
    public void addAcctTransaction(int accIdx , double amount , String memo){
        this.accounts.get(accIdx).addTransaction(amount , memo);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
