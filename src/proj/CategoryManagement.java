package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import static proj.Catalog.getCategoryList;
import static proj.Tableview.getCategoriesTable;
import static proj.Tableview.getCategoryTable;
import static proj.Styling.setSearchTxtStyle;
import static proj.Styling.setSmallButtonsStyle;

public class CategoryManagement { // class for category management
    protected Button cancel, update, remove;
    protected TextField search;
    private Category category;

    public CategoryManagement() {
        initialize();
        actions();
    }

    private void initialize() { // a method to initialize Main interface components
        cancel = new Button();
        update = new Button("Update");
        remove = new Button("Remove");

        cancel.setDisable(true);
        setButtonsStatus(true);

        search = new TextField();
        search.setPromptText("Search");
    }

    protected void setButtonsStatus(boolean disable) {
        update.setDisable(disable);
        remove.setDisable(disable);
    }

    protected HBox search() { // a method for search and cancel search button
        setSearchTxtStyle(search);

        cancel.setGraphic(new ImageView("cancel.png"));
        setSmallButtonsStyle(cancel);

        HBox searching = new HBox(7, cancel, search);
        searching.setPadding(new Insets(0, 0, 20, 0));
        searching.setAlignment(Pos.TOP_RIGHT);
        return searching;
    }


    protected TableView<Category> center() { // a method to get the table view
        return getCategoryTable();
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(20, update, remove);
        btns.setAlignment(Pos.BOTTOM_LEFT);
        btns.setPadding(new Insets(0, 0, 20, 20));
        return btns;
    }

    public BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setRight(search());
        main.setCenter(center());
        main.setBottom(bottom());
        BorderPane.setMargin(main.getCenter(), new Insets(20));
        BorderPane.setMargin(main.getRight(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        search.setOnKeyPressed(e -> { // searching
            if (e.getCode() == KeyCode.ENTER) {
                String searchBy = search.getText().trim();
                if (searchBy.equals("") || !searchBy.matches("^[a-zA-Z ]+$")) {
                    MyAlert.alert("Warning", "You should enter a valid name to search", Alert.AlertType.WARNING);
                    return;
                }

                cancel.setDisable(false);
                remove.setDisable(false);
                update.setDisable(false);
            }
        });


        cancel.setOnAction(e -> { // cancel search button
            search.setText("");
            getCategoriesTable().setItems(getCategoryList());
            cancel.setDisable(true);
        });


        update.setOnAction(e -> update());

        remove.setOnAction(e -> remove());
    }

    private void update() { // a method to update an existing category info
        if (category == null) {
            MyAlert.alert("Error", "You should search for category to update", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }

        Main.setMain(new UpdateCategory(category).main());
    }

    private void remove() { // a method to remove an existing category
        if (category == null) {
            MyAlert.alert("Error", "You should search for category to update", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }

        Main.setMain(new CategoryManagement().main());
    }
}