package pb;
import java.sql.*;
import java.util.Scanner;

public class PhoneBook {
    static{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException{

        String dburl = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            dburl = "jdbc:oracle:thin:@localhost:1521:xe";
            con = DriverManager.getConnection(dburl, "bookmaster", "book");

            Scanner in = new Scanner(System.in);
            int menu;
            boolean sw=true;

            while(sw){
                System.out.println("=====================");
                System.out.println("| 1. search number   |");
                System.out.println("| 2. input number    |");
                System.out.println("| 3. exit            |");
                System.out.println("=====================");
                System.out.println("Choose number : ");
                menu = Integer.parseInt(in.nextLine());

                switch(menu) {
                    case 1:
                    pstmt = con.prepareStatement("SELECT * FROM book");
                    rs = pstmt.executeQuery();

                    System.out.println("name \t phone \t age");

                    while(rs.next()){
                        System.out.print(rs.getString("name")+"\t");
                        System.out.print(rs.getString("phone_number")+"\t");
                        System.out.print(rs.getString("age")+"\t");
                        System.out.println();
                    }
                    break;

                    case 2:
                    String name,phone_number;
                    int age;
                    System.out.println("input your name ");
                    name = in.nextLine();

                    System.out.println("input your phone number ");
                    phone_number = in.nextLine();

                    System.out.println("input your age ");
                    age = Integer.parseInt(in.nextLine());

                    pstmt = con.prepareStatement("INSERT INTO book VALUES(?,?,?)");
                    pstmt.setString(1, name);
                    pstmt.setString(2, phone_number);
                    pstmt.setInt(3, age);
                    pstmt.executeUpdate();
                    break;

                    case 3:
                    sw = false;
                    break;
                }
            }
        }catch (IllegalArgumentException e) {
            System.out.println("Check your input type ");
        }catch(SQLException e) {
            e.printStackTrace();
        }finally{
            if(con!= null){try{con.close();}catch (Exception e) {}}
            if(pstmt != null){try{pstmt.close();}catch (Exception e){}}
            if(rs != null){try{rs.close();}catch (Exception e) {}}
        }
    }
}