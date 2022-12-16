
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            Statement stUsarBase = con.createStatement();
            Statement st1crearTabla = con.createStatement();
            Statement stConsulta = con.createStatement();
            Statement stInsertar = con.createStatement();
            Statement stSelect = con.createStatement();

            //usamos la tabla instituto
            stUsarBase.execute("use instituto");

            String cadena = "create table NotasFinales("
                    + "mat varchar(10),"
                    + "cod tinyint(2),"
                    + "notamedia decimal(4,2),"
                    + "primary key(mat,cod))";
            //ver esta parte

            System.out.println("tabla no existe, se esta creando...");
            st1crearTabla.execute(cadena);

            cadena = "select * from notas";
            ResultSet rs = stConsulta.executeQuery(cadena);

            String mat;
            Double media;
            int codigo;

            while (rs.next()) {
                mat = rs.getString(1);//porque es el campo uno
                codigo = rs.getInt(2);
                media = (rs.getInt(3) + rs.getInt(4) + rs.getInt(5)) / 3.0;
                cadena = "INSERT INTO `notasfinales`(`mat`, `cod`, `notamedia`) VALUES ('" + mat + "'," + codigo + "," + media + ")";
                stInsertar.executeUpdate(cadena);
            }
            cadena = "select APEL_NOM,NOMBRE,NOTA1,NOTA2,NOTA3,notamedia "
                    + "from notas nt,alumnos al,asignaturas asig,notasfinales ntf "
                    + "where nt.mat=al.mat and nt.cod=asig.cod and ntf.mat=nt.mat "
                    + "and ntf.cod=nt.cod";
            rs = stSelect.executeQuery(cadena);
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2)
                        + " " + rs.getInt(3) + " " + rs.getInt(4) + " " + rs.getInt(5)
                        + " " + rs.getDouble(6));
            }

            st1crearTabla.close();
            stUsarBase.close();
            stConsulta.close();
            stInsertar.close();
            stSelect.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    //metodo para comprobar si tabla existe
    public static boolean tableExist(Connection con, String tableName) {

        boolean tExists = false;

        try {

            ResultSet rs = con.getMetaData().getTables(null, null, tableName, null);
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tExists;
    }

}
