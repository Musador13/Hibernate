import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class Three {

    public static void main(String[] args) throws SQLException {

        ResultSet rs = getResultSet();

        CachedRowSet rowSet = (CachedRowSet) rs;
        rowSet.setCommand("select * from products where price > ? order by price");
        rowSet.setInt(1, 4000);

        rowSet.setUrl(DB_Properties.getUrl());
        rowSet.setUsername(DB_Properties.getUserName());
        rowSet.setPassword(DB_Properties.getPassword());
        rowSet.execute();

        while (rowSet.next()) {
            String name = rowSet.getString("productName");
            int price = rowSet.getInt("price");
            System.out.println(name + " : " + price);
        }


        Connection connection = Main.getConnection();
        connection.setAutoCommit(false);
        rowSet.rollback();


    }


    public static ResultSet getResultSet() throws SQLException {
        try (Connection connection = Main.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from products");

            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.populate(rs);
            return cachedRowSet;
        }
    }
}
