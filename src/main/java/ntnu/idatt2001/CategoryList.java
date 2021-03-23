package ntnu.idatt2001;

import java.util.ArrayList;

public class CategoryList {

    private ArrayList<Category> categories;

    public CategoryList(){
        categories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category){
        if (!categories.contains(category)){
            categories.add(category);
            return true;
        }
        return false;
    }
}
