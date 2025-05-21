package proj;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveData { // a class to save data to file
    public SaveData() {
        saveData();
    }

    private void saveData() {
        try (PrintWriter pr = new PrintWriter(new File("C:\\Users\\harit\\OneDrive\\Desktop\\JAVA\\Smart Inventory Management\\src\\files\\savedActions.txt"))) {
            pr.print(Catalog.getUndo().print());
            MyAlert.alert("Success", "Data saved successfully", Alert.AlertType.INFORMATION);

        } catch (FileNotFoundException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
        }
    }
}
