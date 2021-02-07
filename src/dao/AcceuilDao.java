package dao;

import Model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcceuilDao {

    private db_connection conx = new db_connection();
    private Connection con;

    public AcceuilDao(){

        con = conx.get_Connection();
    }


    public boolean isUser(UserModel userModel){

        ResultSet rs ;
        String req = "select * from user where user = ? and pwd = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1,userModel.getUser());
            ps.setString(2,userModel.getPassword());
            rs = ps.executeQuery();
            System.out.println("here");
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error d authentification "+e.getMessage());
        }

        return false;
    }

    public String getType(String user){
        ResultSet rs ;
        String req = "select type from user where user = ? ";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1,user);
            rs = ps.executeQuery();

            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error Type "+e.getMessage());
        }
        return "any type";
    }
}
