package proj;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadData { // a class for loading data from files

    private Stage stage;

    public LoadData(Stage stage) {
        this.stage = stage;
        loadCategories();
        loadProducts();
        loadShipments();
    }

    private void loadCategories() { // a method to load categories file
        try (Scanner scanner = new Scanner(new File("C:\\Users\\harit\\OneDrive\\Desktop\\JAVA\\Smart Inventory Management\\src\\files\\categories.txt"))) {
            while (scanner.hasNextLine()) {
                String arr[] = scanner.nextLine().split(",");

                if (arr.length != 3)
                    continue;

                if (checkCategoryID(arr[0].trim()))
                    new CategoryHandler().handler(arr, true, null);
            }

        } catch (FileNotFoundException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
        }
    }

    private void loadProducts() { // a method to load products file
        try (Scanner scanner = new Scanner(new File("C:\\Users\\harit\\OneDrive\\Desktop\\JAVA\\Smart Inventory Management\\src\\files\\products.txt"))) {
            while (scanner.hasNextLine()) {
                String arr[] = scanner.nextLine().split(",");

                if (arr.length != 4)
                    continue;

                if (checkProductID(arr[0].trim()) && checkCategoryName(arr[2].trim()))
                    new ProductHandler().handler(arr, null);

            }

        } catch (FileNotFoundException ex) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
        }
    }

    private void loadShipments() { // a method to load shipments file
        File file = loadFile();
        if (file == null)
            return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String arr[] = scanner.nextLine().split(",");

                if (arr.length != 4)
                    continue;

                if (!checkShipmentID(arr[0].trim())) {
                    Product product = getProduct(arr[1].trim());
                    if (product != null) {
                        String data[] = {arr[0].trim(), arr[2].trim()};
                        new ShipmentHandler().handler(data, product, arr[3].trim(), true);
                    }
                }

                if (checkProductID(arr[0].trim()) && checkCategoryName(arr[2].trim()))
                    new ProductHandler().handler(arr, null);
            }

        } catch (FileNotFoundException ex) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
        }
    }

    private File loadFile() { // load from FileChooser
        FileChooser fc = new FileChooser();
        fc.setTitle("my files");
        fc.setInitialDirectory(new File("C:\\Users\\harit\\OneDrive\\Desktop\\JAVA\\Smart Inventory Management\\src\\files"));
        File file = fc.showOpenDialog(stage);
        return file;
    }

    private boolean checkCategoryID(String id) { // check if the category id is existed or not
        return !Catalog.getCategories().findID(id);
    }

    private boolean checkProductID(String id) { // check if the product id is existed or not
        int index = 0;
        try {
            index = Integer.parseInt(id) % 10;
        } catch (NumberFormatException e) {
            return false;
        }

        for (int i = 0; i < 26; i++) {
            Product product = Catalog.getCursorArray()[i][index].searchID(1, id);
            if (product != null)
                return false;
        }
        return true;
    }

    private boolean checkCategoryName(String name) { // check if the category name is existed or not
        return Catalog.getCategories().findName(name);
    }

    private boolean checkShipmentID(String id) { // check if the shipment id is existed or not
        int size = Catalog.getShipments().getSize();
        boolean found = false;

        while (size-- > 0) {
            Shipment shipment = (Shipment) Catalog.getShipments().dequeue();
            if (shipment.getId().equals(id))
                found = true;

            Catalog.getShipments().enqueue(shipment);
        }
        return found;
    }

    private Product getProduct(String id) { // get a specific product by it's id
        int index = 0;
        try {
            index = Integer.parseInt(id) % 10;
        } catch (NumberFormatException e) {
            return null;
        }

        for (int i = 0; i < 26; i++) {
            Product product = Catalog.getCursorArray()[i][index].searchID(1, id);
            if (product != null)
                return product;
        }
        return null;
    }
}
