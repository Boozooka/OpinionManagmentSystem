package dataConnect;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    Connection connection;
    Statement mainBase;
    Statement twinkBase;
    public Connector (String url, String name, String password){
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException ex){
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, name, password);
            mainBase = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            twinkBase = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public User getUserById (long id){
        try (ResultSet set = twinkBase.executeQuery("SELECT * FROM users WHERE id = " + id)){
            set.next();
            User result = new User(set.getInt("id"), set.getString("username"),
                    set.getString("password"), this.twinkBase);
            return result;
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
    }

    public User getUserByName (String username) {
        try (ResultSet userSet = twinkBase.executeQuery("SELECT * FROM users WHERE username = '" +
                username + "'")){

            userSet.next();
            User result = new User(userSet.getInt("id"),
                    userSet.getString("username"),
                    userSet.getString("password"),
                    this.twinkBase);
            return result;
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public List<User> getUsers (){
        try (ResultSet idSet = mainBase.executeQuery("SELECT id FROM users")){

            List<User> result = new ArrayList<>();

            do {
                idSet.next();
                User currentUser = getUserById(idSet.getInt("id"));
                result.add(currentUser);
            } while (!idSet.isClosed());
            return result;
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    public boolean connectUser(String name, String password){
        try (ResultSet set = mainBase.executeQuery("SELECT * FROM users WHERE username = '" + name + "' AND password = '" +
                password + "'")){
            if (set.next()){
                return true;
            } else {
                set.close();
                return false;
            }
        } catch (SQLException ex){
            //TODO: реализовать обработку исключений (Неверный пароль)
            return false;
        }
        //System.out.println(true);
    }

    public boolean registerUser(String name, String password){
        try (ResultSet set = mainBase.executeQuery("SELECT * FROM users WHERE username = '" + name + "'")){

            if (!set.isBeforeFirst()){
                mainBase.executeUpdate("INSERT INTO users (username, password) values ('"+ name + "', '" + password
                        + "')");
                return true;
            } else {
                throw new SQLException("Имя уже занято");
            }
        } catch (SQLException ex){
            return false;
        }
    }
    public void putOpinion (User user, String opinion){
        try{
            mainBase.executeUpdate("INSERT INTO opinions (user_id, value) values (" + user.getId()
                            + ", '" + opinion + "')");
        } catch (SQLException ex){
            //TODO: реализовать обработку исключений (+ имя уже занято)
        }
    }

    public long getLastOpinionId (){
        try {
            ResultSet resultSet = mainBase.executeQuery("SELECT * FROM opinions");

            resultSet.last();

            long result = resultSet.getLong("opinion_id");

            return result;
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public Opinion getOpinionById (long id){
        try {
            ResultSet resultSet = mainBase.executeQuery("SELECT * FROM opinions WHERE opinion_id = " + id);
            resultSet.next();

            User owner = getUserById(resultSet.getLong("user_id"));
            String value = resultSet.getString("value");

            Opinion result = new Opinion(id, value, owner);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Opinion> getOpinionsOfUser (User user){
        try(ResultSet opinionSet = mainBase.executeQuery("SELECT * FROM opinions WHERE user_id = '" +
                user.getId() + "'")){

            List<Opinion> result = new ArrayList<>();
            boolean isLast;
            do {
                opinionSet.next();
                isLast = opinionSet.isLast();

                String value = opinionSet.getString("value");
                long opinionId = opinionSet.getInt("opinion_id");
                long userId = opinionSet.getInt("user_id");
                User owner = getUserById(userId);
                Opinion currentOpinion = new Opinion(opinionId, value, owner);

                result.add(currentOpinion);
                /*System.out.println(opinionSet.isBeforeFirst());
                System.out.println(opinionSet.isFirst());
                System.out.println(opinionSet.isLast());
                System.out.println(opinionSet.isAfterLast());*/
            } while (!isLast);
            return result;

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
}
//CREATE TABLE opinions(opinion_id INT PRIMARY KEY AUTO_INCREMENT, user_id INT, value VARCHAR(500), FOREIGN KEY (user_id) REFERENCES users (id));