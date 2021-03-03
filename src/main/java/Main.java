import com.octetstring.jdbcLdap.sql.JdbcLdapDriver;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        String url = "jdbc:ldap://192.168.0.3:1389/dc=cupr?SEARCH_SCOPE:=subTreeScope";
        String user = "cn=admin,dc=cupr";
        String password = "userCupr";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean isTrue = true;
        String sql_select;
        String sql_modify;
        int msisdn = 0;
        try {
            DriverManager.registerDriver(new JdbcLdapDriver());
            connection = DriverManager.getConnection(url,user,password);

            while(isTrue) {
                for (int i = 0; i < 10; i++) {
                    sql_select = "Select * FROM msisdn=1"+msisdn+",ou=subscribers,o=polkomtel,dc=cupr";
                    sql_modify = "Update msisdn=1"+msisdn+",ou=subscribers,o=polkomtel,dc=cupr SET imsi='12345'";
                    msisdn++;
                    if(msisdn == 50000)
                    {
                        msisdn = 0;
                    }
                    statement = connection.createStatement();
                    statement.executeUpdate(sql_modify);
                    resultSet = statement.executeQuery(sql_select);
                     ResultSetMetaData rsmd = resultSet.getMetaData();
                      int columnsNumber = rsmd.getColumnCount();
                       while (resultSet.next()) {
                           for (int j = 1; j <= columnsNumber; j++) {
                               if (j > 1) System.out.print("\n");
                               String columnValue = resultSet.getString(j);
                               System.out.print(rsmd.getColumnName(j) + ": " + columnValue);
                           }
                       }
                    statement.close();
                    resultSet.close();
                }
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (resultSet != null) resultSet.close(); } catch (SQLException throwables) {};
            try { if (statement != null) statement.close(); } catch (SQLException throwables) {};
            try { if (connection != null) connection.close(); } catch (SQLException throwables) {};
        }

    }
}
