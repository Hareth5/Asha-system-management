package proj;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import static proj.Category.CategoryCounter;
import static proj.Product.productCounter;

public class CategoryHandler { // class for adding a new category and updating an existing category
    public boolean handler(String data[], boolean fromFile, Category category) {
        SimpleStringProperty id;

        id = new SimpleStringProperty(data[0]);
        SimpleStringProperty name = new SimpleStringProperty(data[1]);
        SimpleStringProperty description = new SimpleStringProperty(data[2]);

        try {
            if (category == null) {
                Category newCategory = new Category(id, name, description);
                Catalog.getCategories().addLast(newCategory);
                Catalog.getCategoryList().addLast(newCategory);
                CategoryCounter++;
                if (!fromFile)
                    MyAlert.alert("Success!", "Category added successfully", Alert.AlertType.INFORMATION);
                return true;

            } else {
                category.setName(name.get().trim());
                category.setDescription(description.get().trim());
                MyAlert.alert("Success!", "Category updated successfully", Alert.AlertType.INFORMATION);
                Main.setMain(new CategoryManagement().main());
                return false;
            }

        } catch (IllegalArgumentException e) {
            MyAlert.alert("Error", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
}
