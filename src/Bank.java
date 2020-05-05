import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;


    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();

    }

    public String getNewUserUUID(){
        String uuid;
        boolean nonUnique;
        Random rand = new Random();
        int len = 6;
        do {
            uuid = "";
            for(int i = 0 ; i <len ; i++){
                uuid += ((Integer)rand.nextInt(10)).toString();
            }
            nonUnique = false;
            for(User user : this.users){
                if(user.getUUID().compareTo(uuid) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);


        return uuid;
    }

    public String getNewAccountUUID(){
        String uuid;
        boolean nonUnique;
        Random rand = new Random();
        int len = 9;
        do {
            uuid = "";
            for(int i = 0 ; i <len ; i++){
                uuid += ((Integer)rand.nextInt(10)).toString();
            }
            nonUnique = false;
            for(Account account : this.accounts){
                if(account.getUUID().compareTo(uuid) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);


        return uuid;
    }
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName , String lastName , String pin ) {
        // Creating a new User Object
        User newUser = new User(firstName , lastName , pin, this);
        this.users.add(newUser);
        // Creating a Saving Account for the new user and add the new Account into
        // newUser's accounts list
        Account newAccount = new Account("Savings" ,newUser,this);
        this.addAccount(newAccount);
        newUser.addAccount(newAccount);
        return newUser ;
    }
    public User userLog(String userID , String pin){
        for(User u : users){
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
    public String getName(){
        return this.name;
    }


}
