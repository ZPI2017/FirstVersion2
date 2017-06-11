package zpi.lignarski.janusz;

/**
 * Created by Janusz on 2017-05-14.
 */

public class Category {
    private String id;
    private String name;

    public Category() {}

    public Category(String i, String n) {
        setId(i); setName(n);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
