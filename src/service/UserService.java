package service;

import connectDTB.ConnectionUtil;
import model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public String authorizeLogin(Users user) {
        String username = user.getUsername();
        String password = user.getPassword();

        String dbusername = "";
        String dbpassword = "";

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "123456", "login", "3306");

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("select * from users where username=? and password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dbusername = rs.getString("username");
                dbpassword = rs.getString("password");

                if (username.equals(dbusername) && password.equals(dbpassword)) {
                    return "SUCCESS LOGIN";
                }
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "WRONG USERNAME AND PASSWORD";
    }

    public String authorizeRegister(Users user) {
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "123456", "login", "3306");

            PreparedStatement pstmt = null;

            pstmt = con.prepareStatement("insert into user(firstname,lastname,username,password) values(?,?,?,md5(?))");
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "SUCCESS REGISTER";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL REGISTER";
    }
}
