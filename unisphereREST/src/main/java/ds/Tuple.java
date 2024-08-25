package ds;

public class Tuple {
    private int id;
    private String name;

    public Tuple(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "categoryTuple{id=" + id + ", name='" + name + "'}";
    }
}
