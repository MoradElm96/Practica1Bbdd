
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alumno
 */
public class Main {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/";
            String user = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, user, password);
            
            Statement st= con.createStatement();
            st.execute("use instituto");
            
            
            String crearTabla="CREATE TABLE `instituto`.`notasfinales` ( `MAT` VARCHAR(20) NOT NULL , `cod` INT(10) NOT NULL , `NotaMedia` INT(10) NOT NULL ) ENGINE = InnoDB;";
            
            PreparedStatement st1 = con.prepareStatement(crearTabla);
            st1.execute();
            st.close();
            

           
            
            
            
        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
