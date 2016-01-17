package lonetlogingenerator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.corba.se.impl.util.Version;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joern
 */
public class MySQL {

    private static Connection con = null;
    private static String dbHost = "localhost"; // Hostname
    private static String dbPort = "3306";      // Port -- Standard: 3306
    private String url;
    private String user;
    private String password;
    
    public MySQL( String dbName, String user, String password ) {
        this.url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        this.user = user;
        this.password = password;
    }

    public ArrayList<Student> getStudents(String mySqlStatement) {

        ArrayList<Student> students = new ArrayList<>();
        String statement = mySqlStatement;


        try {
            Connection con = ( Connection ) DriverManager.getConnection( url, user, password );
            PreparedStatement pst = con.prepareStatement( statement );
            ResultSet rs = pst.executeQuery();

            while( rs.next() ) {
                students.add( new Student(rs.getString(1), rs.getString(3), rs.getString(2) ) );
            }

            con.close();
            
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

        return students;
    }
    
}