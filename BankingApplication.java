import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BankingApplication
{
    public static void main(String[]args)
    {
        BankAccount bank=new BankAccount();
        bank.showMenu();
        //new Con().con();
    }
}

class BankAccount
{
    int balance;
    int previousTransaction;
    String customerName=new String();
    String customerID=new String();
    Con con=new Con();

    void depositMoney(int amount,int i)
    {
        if(amount != 0)
        {
            if(amount > 0)
            {

                balance=con.checkBal(i);
                balance=balance+amount;
                con.balance(balance,i);
                previousTransaction=amount;
            }
            else
            {
                System.out.println("\nYou can't Deposit Negative Amount Of Money Such as "+amount);
            }
        }
        else
        {
            System.out.println("You can't Deposit Null Amount of Value Such as "+amount);
        }
    }

    void withdrawMoney(int amount,int i)
    {
        balance=con.checkBal(i);
        if(amount != 0)
        {
            if(amount > 0)
            {
                if(balance > amount)
                {
                    balance=balance-amount;
                    previousTransaction=-amount;
                    con.balance(balance,i);
                }
                else
                {
                    System.out.println("Your Remaing Balance is "+balance+". You Can't Withdraw More than that. e.g. "+amount);
                }
            }
            else
            {
                System.out.println("\nYou can't Withdraw Negative Amount Of Money Such as "+amount);
            }
        }
        else
        {
            System.out.println("You can't Withdraw Null Amount of Value Such as "+amount);
        }
    }

    void getPreviousTransactionDetails()
    {
        if(previousTransaction > 0)
        {
            System.out.println("Your Last Transaction Found Deposit: "+previousTransaction);
        }
        else
        {
            if(previousTransaction < 0)
            {
                System.out.println("Your Last Transaction Found Withdraw: "+Math.abs(previousTransaction));
            }
            else
            {
                System.out.println("No transaction occured");
            }
        }
    }

    void showMenu() {
        char option;
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWelcome To Our Branch :)\n");

        System.out.println("Please Enter Your Name: ");
        System.out.println("Press 1 to creating account: ");
        customerName = sc.nextLine();
        if(customerName.equals("1"))
        {
            con.register();
            showMenu();
        }

        System.out.println("\nPlease Enter Your Password: ");
        customerID = sc.nextLine();

        Con con = new Con();
        con.name = customerName;
        con.password = customerID;
        int i=con.insert();
        if (i!=0) {

            System.out.println("\nWelcome " + customerName + " Your Password is " + customerID);

            System.out.println("\nA. Check Balance");
            System.out.println("B. Deposit Money");
            System.out.println("C. Withdraw Money");
            System.out.println("D. Previous Transaction");
            System.out.println("E. Exit");
            System.out.println("\n");

            do {
                System.out.println("==========================================================================");
                System.out.println("Enter an Option");
                System.out.println("==========================================================================");
                option = sc.next().charAt(0);

                if (option == 'a') {
                    int a = 65;
                    option = (char) a;
                }
                if (option == 'b') {
                    int a = 66;
                    option = (char) a;
                }
                if (option == 'c') {
                    int a = 67;
                    option = (char) a;
                }
                if (option == 'd') {
                    int a = 68;
                    option = (char) a;
                }
                if (option == 'e') {
                    int a = 69;
                    option = (char) a;
                }

                System.out.println("\n");

                switch (option) {

                    case 'A':
                        System.out.println("==========================================================================");
                        int bal=con.checkBal(i);
                        System.out.println("Balance = " + bal);
                        System.out.println("==========================================================================");
                        System.out.println("\n");
                        break;

                    case 'B':
                        System.out.println("==========================================================================");
                        System.out.println("Enter an Amount to Deposit:");
                        System.out.println("==========================================================================");
                        int amount1 = sc.nextInt();
                        depositMoney(amount1,i);
                        System.out.println("\n");
                        break;

                    case 'C':
                        System.out.println("==========================================================================");
                        System.out.println("Enter an Amount to Withdraw:");
                        System.out.println("==========================================================================");
                        int amount2 = sc.nextInt();
                        withdrawMoney(amount2,i);
                        System.out.println("\n");
                        break;

                    case 'D':
                        System.out.println("==========================================================================");
                        getPreviousTransactionDetails();
                        System.out.println("==========================================================================");
                        System.out.println("\n");
                        break;

                    case 'E':
                        System.out.println("**************************************************************************");
                        break;

                    default:
                        System.out.println("Error Found 404! :( Please Try Again :)\n");
                        break;
                }

            } while (option != 'E');

            System.out.println("Thank you! For Using Our Branch Service :)");
            System.out.println("**************************************************************************");
        }
    }
}

class Con
{
    private String pass="";
    private String uname="root";
    //private String que="INSERT INTO `bank` (`id`, `name`, `password`, `amount`) VALUES (NULL, 'sayan', '123456', '10000');";
    private String url="jdbc:mysql://localhost:3308/test";
    public Connection connection;

    public Connection con()
    {
        try {
            System.out.println("Start");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Lode");
            connection = DriverManager.getConnection(url, uname, pass);
            System.out.println("Connected");

        }catch (Exception e)
        {
            System.out.println(e);
        }
        return connection;
    }

    String name;
    String password;
    int id;
    public int insert()
    {
        int i=0;
        try {

            String check="SELECT `id`,`name`, `password` FROM `bank` WHERE `name`= ? AND `password`= ? ;";
            BankAccount ba=new BankAccount();
            Connection con = con();
            PreparedStatement ps = con.prepareStatement(check);
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                System.out.println("Login success");
                i=1;
                id=rs.getInt("id");
                System.out.println(id);
                i=id;
                //Afterlogin al=new Afterlogin();
            }else{
                System.out.println("login failed");
                i=0;
            }
            return id;
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return i;
    }

    public void balance(int bal,int id)
    {
        int balance = 0;
        try {
            String bal1=String.valueOf(bal);
            System.out.println(id);
            String update = "UPDATE `bank` SET `amount` = ? WHERE `bank`.`id` = "+id+";";
            Connection con = con();
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, bal1);

            int rs = ps.executeUpdate();
            if (rs==1) {
                    System.out.println("Amount updated");
                //Afterlogin al=new Afterlogin();
            } else {
                System.out.println("failed");
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public int checkBal(int id) {
        int bal = 0;
        Connection con = con();
        String balance = "SELECT `amount` FROM `bank` WHERE `id` = " + id + ";";
        try {
            PreparedStatement ps = con.prepareStatement(balance);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                String fatchBal=rs.getString("amount");
                bal=Integer.parseInt(fatchBal);
            }else
            {
                System.out.println("fail");
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return bal;
    }

    public void register()
    {
        try {
            Connection con=con();
            String sql="INSERT INTO `bank` (`id`, `name`, `password`, `amount`) VALUES (NULL, ?, ?, '');";
            String name,pass;
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter your name");
            name=sc.next();
            System.out.println("Enter your password");
            pass=sc.next();
            if(name!=null && pass!= null)
            {
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1,name);
                ps.setString(2,pass);

                int rs=ps.executeUpdate();
                if(rs==1)
                {
                    System.out.println("Registion successful");
                }
                else
                    System.out.println("Registion fail");

            }

        }catch (Exception e)
        {
            System.out.println("User name should be unique. Try again!!!");
            register();
        }
    }
}
