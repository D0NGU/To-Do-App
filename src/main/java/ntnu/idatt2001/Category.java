package ntnu.idatt2001;

import java.util.Objects;

public class Category implements java.io.Serializable{
    private String name;
    private String color;
    private boolean isShowing;

    public Category(String name, String color){
        this.name = name;
        this.color = color;
        isShowing = true;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category:\n" +
                "Name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isShowing=" + isShowing +
                '}';
    }
}
