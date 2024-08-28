package dataConnect;

import java.sql.Statement;
import java.util.List;

public class User {
    private long id;
    private String name;
    private String password;
    List<String> opinions;
    Statement base;
    public User(long id, String name, String password, Statement base){
        this.id = id;
        this.name = name;
        this.password = password;
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
