package structures.bag;

/**
 * This is a Marble class that defines a Marbles color,
 * material, and counter
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class Marble {

    private String color;
    private Material material;
    private int counter;

    /**
     * This function is my copy constructor
     * @param other is the marble to be copied over
     */
    public Marble(Marble other) { //copy constructor
        this.color = other.getColor();
        this.material = other.getMaterial();
        this.counter = other.getCounter();
    }

    /**
     * This is the Marble constructor
     * @param color is the color of the Marble
     * @param material is the material of the marble
     * @param counter is the quantity of this marble
     */
    public Marble(String color, Material material, int counter) {
        this.color = color;
        this.material = material;
        this.counter = counter;
    }

    /**
     * This is my color getter
     * @return returns the color
     */
    public String getColor() {
        return color;
    }

    /**
     * This is my color setter
     * @param color sets the Marble color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * This is my Material getter
     * @return returns the material type
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * This is my counter getter
     * @return returns the count of a Marble
     */
    public int getCounter() {
        return counter;
    }

    /**
     * This the that counter setter
     * @param counter sets the count of a marble (quantity)
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * This function compares the color and material type of a different marble
     * @param other is the marble being compared to
     * @return true if they're a match, false otherwise
     */
    public boolean equals(Marble other) {
        return this.color.equals(other.getColor()) && this.material.equals(other.getMaterial());
    }

    @Override
    public String toString() {
        return "Marble{" +
                "color='" + color + '\'' +
                ", material=" + material +
                ", counter=" + counter +
                '}';
    }
}
