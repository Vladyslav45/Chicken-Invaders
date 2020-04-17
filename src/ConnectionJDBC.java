import java.sql.*;

public class ConnectionJDBC {
    private static Connection connection;

    public static void con(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "postgres");
            showRanking();
        } catch (ClassNotFoundException e) {
            System.out.println("bledny sterownik do bazy danych: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Blad polaczenie z baza danych: " + e.getMessage());
        }
    }

    public static void save(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ranking(nickname, score) VALUES(?,?)");
            preparedStatement.setString(1, StartWidok.nickname);
            preparedStatement.setInt(2, GameWidok.score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showRanking(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nickname, score FROM ranking");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String nickname = resultSet.getString("nickname");
                long score = resultSet.getLong("score");
                StartWidok.rankingMap.put(nickname, (int) score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
