import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Second {

    public static void main(String[] args) {

        try (Connection connection = Main.getConnection();
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)) {
            System.out.println("DataBase connection is success!");

            ResultSet rs = null;

            try {
                rs = statement.executeQuery("select * from first_db.products");

                System.out.println("Query is success!");

//                while (rs.next()) {
//                    int id = rs.getInt(1);
//                    int price = rs.getInt(3);
//                    if (id == 7) {
//                        rs.updateString("ProductName","iPhone 11");
//                        rs.updateInt(3, price + 20000);
//                        rs.updateRow();
//                    }
//                }

//                if (rs.absolute(4)) {
//                    System.out.println(rs.getString("ProductName"));
//                }
//                if (rs.previous()) {
//                    System.out.println(rs.getString("ProductName"));
//                }
//                if (rs.last()){
//                    System.out.println(rs.getString("ProductName"));
//                }

                if (rs.relative(1)){
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()){
                        for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                            String name = rsmd.getColumnName(i);
                            String value = rs.getString(name);
                            System.out.println(name + " - " + value);
                        }
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                } else {
                    System.err.println("Query is`t success...");
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection is`t success...");
        }
    }

}
