import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Main {


    public static void main(String[] args) {
        connectionToDB();
    }

    private static void connectionToDB() {

        try (Connection connection = getConnection()) {
            System.out.println("Connection success");

            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = connection.prepareStatement(
                        "insert into products (ProductName, Price) VALUES (?, ?)");

                preparedStatement.setString(1, "Xiaomi Mi 9");
                preparedStatement.setInt(2, 50000);
//                preparedStatement.execute();

                ResultSet rs = null;
                try {
                    rs = preparedStatement.executeQuery("select * from products");

                    while (rs.next()) {
                        int id = rs.getInt("Id");
                        String name = rs.getString("ProductName");
                        int price = rs.getInt("Price");
                        System.out.printf("%d. %s - %d\n", id, name, price);
                    }
                } catch (SQLException e) {
                    System.err.println("Reading database error...");
                } finally {
                    assert rs != null;
                    rs.close();
                }

            } catch (SQLException e) {
                System.err.println("SQL query is failed...");
                e.printStackTrace();

            } finally {
                assert preparedStatement != null;
                preparedStatement.close();
            }

            CallableStatement callableStatement = null;

            try {
                callableStatement = connection.prepareCall("{call productCount(?)}");
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.execute();
                System.out.println("Count of table entries: " + callableStatement.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                assert callableStatement != null;
                callableStatement.close();
            }

        } catch (SQLException e) {
            System.err.println("Connection failed...");
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {

        String url = DB_Properties.getUrl();
        String username = DB_Properties.getUserName();
        String password = DB_Properties.getPassword();

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
