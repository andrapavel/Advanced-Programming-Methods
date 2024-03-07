import java.util.Objects;

class Animal
{
    protected String colour;
    protected double weight;

    Animal(String col, double w)
    {
        this.colour = col;
        this.weight = w;
    }

    @Override
    public String toString()
    {
        return "Animal.";
    }

    protected final int f()
    {
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Animal(this.colour, this.weight);
    }

    private String greet()
    {
        return "Hello, I am an animal.";
    }
}

class Penguin extends Animal
{
    private String type;

    Penguin(String col, double w, String t)
    {
        super(col, w);
        this.type = t;
    }

    @Override
    public String toString() {
        return "Penguin{" +
                "colour='" + super.colour + "\' " +
                "weight=" + super.weight + " " +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penguin penguin = (Penguin) o;
        return type.equals(penguin.type) && colour.equals(penguin.colour) && weight == penguin.weight;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Penguin(this.colour, this.weight, this.type);
    }

//    @Override // ERROR
//    String greet()
//    {
//        return "a";
//    }
}

public class Inheritance {
    public static void main(String[] args) {
        Penguin p = new Penguin("black", 10, "Magellanic");
        System.out.println(p);

        Object p1 = new Penguin("black and white", 10, "Magellanic");
        Object p2 = new Penguin("black and white", 10, "Magellanic");
        System.out.println(p1.equals(p2));

        // covariant return type
        try {
            Animal p3 = new Penguin("white and black", 8, "Emperor");
            System.out.println(p3);
            Animal clone = (Penguin)p3.clone();
            System.out.println(clone);
        }
        catch (CloneNotSupportedException ex) {}
    }
}
