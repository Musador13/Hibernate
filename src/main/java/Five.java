import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Five {

    public static void main(String[] args) throws InterruptedException {

        try (Connection connection = Main.getConnection();
                Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            statement.executeUpdate("update first_db.products set first_db.products.Price = 125000 where id = 17");
            new OtherTransaction().start();
            Thread.sleep(2000);
            connection.rollback();

        } catch (SQLException e) {
            System.err.println("Connection failed...");
            e.printStackTrace();
        }
    }

    /**
     * Создаем транзакцию в другом потоке.
     */
    static class OtherTransaction extends Thread {

        @Override
        public void run() {
            try (Connection connection = Main.getConnection();
                    Statement statement = connection.createStatement()) {

                connection.setAutoCommit(false);
                // Грязное чтение - вторая транзакция получила недействительные значения.
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

                ResultSet rs = statement.executeQuery("select * from products");

                while (rs.next()){
                    System.out.println(rs.getString("productName") + " " +
                            rs.getInt("price"));
                }

            } catch (SQLException e) {
                System.err.println("Connection failed...");
                e.printStackTrace();
            }
        }
    }
}
