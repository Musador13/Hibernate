import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class Four {

    public static void main(String[] args) {

        try(Connection connection = Main.getConnection();
                Statement statement = connection.createStatement()){

            String createTable = "create table Fruit (name varchar(15) not null,"
                    + "amount integer, price double not null, primary key(name))";

            String command1 = "insert into Fruit (name, amount, price) values "
                    + "('Apple', 200, 3.5)";
            String command2 = "insert into Fruit (name, amount, price) values "
                    + "('Orange', 45, 4.5)";
            String command3 = "insert into Fruit (name, amount, price) values "
                    + "('Pineapple', 20, 7.5)";
            String command4 = "insert into Fruit (name, amount, price) values "
                    + "('Lemon', 100, 2.5)";


//            /*
//            Выполнение транзакции создания и заполнения таблицы
//             */
//            try {
//                connection.setAutoCommit(false);
//                statement.executeUpdate(createTable);
//                statement.executeUpdate(command1);
//                Savepoint savepoint = connection.setSavepoint();
//                statement.executeUpdate(command2);
//                statement.executeUpdate(command3);
//                statement.executeUpdate(command4);
////                connection.commit();
//                connection.rollback(savepoint);
//                connection.commit();
//                connection.releaseSavepoint(savepoint);
//
//            } catch (SQLException e) {
//                System.err.println("Transaction failed...");
//                e.printStackTrace();
//            }

            /*
            Групповой запрос
             */

            System.out.println(connection.getTransactionIsolation());

//            connection.setAutoCommit(true);
//            statement.addBatch(createTable);
//            statement.addBatch(command1);
//            statement.addBatch(command2);
//            statement.addBatch(command3);
//            statement.addBatch(command4);
//            statement.executeBatch();




        } catch (SQLException e) {
            System.err.println("Connection failed...");
            e.printStackTrace();
        }
    }

}
