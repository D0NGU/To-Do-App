package ntnu.idatt2001;

import java.util.Objects;

/**
 * Class for one category
 * A category has a name, color and a boolean that says whether the category is visible at the moment
 */
public class Category implements java.io.Serializable{
    private String name;
    private String color;
    private boolean isShowing;

    /**
     * constructor that creates a new category
     * @param name - the name of the category
     * @param color - the color of the category
     */
    public Category(String name, String color){
        this.name = name;
        this.color = color;
        isShowing = true;
    }

    /**
     * method to get the name of the given category
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * method to get the color of the given category
     * @return the color of the category
     */
    public String getColor() {
        return color;
    }

    /**
     * method that tells whether the category is visible at the moment
     * @return true if the category is visible, false if its hidden
     */
    public boolean isShowing() {
        return isShowing;
    }

    /**
     * method to change the name of a given category
     * @param name - the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method to change the visibility of the category
     * @param showing - true if the category should be showing, false if it should be hidden
     */
    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    /**
     * method that checks whether or not two category objects are the same. They are the same if either:
     * 1. the names are the same
     * 2. both the objects are the same
     * @param o - the other object you want to compare with
     * @return true if the category objects are the same or false if they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    /**
     * method that creates a unique id for a category object
     * @return the unique id for a category
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * method that returns all the information belonging to a given category
     * @return all the contents to the category
     */
    @Override
    public String toString() {
        return "Category:\n" +
                "Name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isShowing=" + isShowing +
                '}';
    }
}
