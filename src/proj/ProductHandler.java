package proj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import static proj.Product.productCounter;

public class ProductHandler { // class for adding a new product and updating an existing product
    public boolean handler(String data[], Product product) {
        SimpleStringProperty id;

        id = new SimpleStringProperty(data[0]);
        SimpleStringProperty name = new SimpleStringProperty(data[1]);
        SimpleStringProperty category = new SimpleStringProperty(data[2]);
        SimpleStringProperty status = new SimpleStringProperty(data[3]);

        Category category2 = Catalog.getCategories().findCategory(String.valueOf(category.get()));

        try {
            if (product == null) {
                Product newProduct = new Product(id, name, status, new SimpleIntegerProperty(0), category2);
                Catalog.getCursorArray()[getName(newProduct)][getID(newProduct)].insertSortedByName(newProduct, 1);
                Catalog.getProducts().insertAtHead(newProduct, category2.cursorIndex);
                Catalog.getProductList().addLast(newProduct);
                productCounter++;
                return true;

            } else {
                Category oldCategory = product.getCategory();
                product.setName(name.get().trim());
                product.setCategory(category2);
                if (!product.getStatus().equals(status.get().trim())) {
                    if (product.getStatus().equals("Active")) {
                        Product.activeProducts--;
                        Product.inActiveProducts++;
                    }
                    else {
                        Product.activeProducts++;
                        Product.inActiveProducts--;
                    }
                }
                product.setStatus(status.get().trim());

                if (!oldCategory.getName().equals(category2.getName())) {
                    Catalog.getProducts().remove(product, oldCategory.cursorIndex);

                    int idIndex = ProductHandler.getID(product);
                    int nameIndex = ProductHandler.getName(product);
                    Catalog.getCursorArray()[nameIndex][idIndex].remove(product, oldCategory.cursorIndex);

                    handler(data, null);
                }
                MyAlert.alert("Success!", "Product updated successfully", Alert.AlertType.INFORMATION);
                Main.setMain(new ProductManagement().main());
                return false;
            }

        } catch (IllegalArgumentException e) {
            MyAlert.alert("Error", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public static int getID(Product product) { // a method to ger the product id
        return Integer.parseInt(product.getId()) % 10;
    }

    public static int getName(Product product) { // a method to ger the product name
        return product.getName().charAt(0) - 65;
    }
}
