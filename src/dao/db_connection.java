package dao;

import java.sql.*;

public class db_connection implements Db_Source{

    static Connection con;
    static Statement stm;

    public static Statement getStatment(){

        try {
            return stm = get_Connection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stm;
    }

    public static Connection get_Connection() {

        if (con == null) {

            try {
                Class.forName(Driver);
                con = DriverManager.getConnection(url, user, pwd);
                System.out.println("connection successfull");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error "+e.getMessage());
            }
        }
        return con;
    }
}
