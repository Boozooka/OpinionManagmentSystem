package dataConnect;

public class Opinion {
    private String value;
    private long id;
    private User owner;

    public Opinion(long id, String value, User owner){
        this.id = id;
        this.value = value;
        this.owner = owner;
    }

    public String getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }
}
