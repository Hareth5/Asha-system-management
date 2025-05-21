package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static proj.Styling.setSideButtonsStyle;

public class SideButtons { // a class for side buttons
    private Button home, newShipment, report, load, save, exit;
    private Stage stage;

    public SideButtons(Stage stage) {
        this.stage = stage;
        initializeSideButtons();
        actions();
    }

    private void initializeSideButtons() { // a method to initialize side buttons
        home = new Button();
        home.setGraphic(new ImageView("home.png"));

        newShipment = new Button();
        newShipment.setGraphic(new ImageView("product.png"));

        report = new Button();
        report.setGraphic(new ImageView("stat.png"));

        load = new Button();
        load.setGraphic(new ImageView("save.png"));

        save = new Button();
        save.setGraphic(new ImageView("load.png"));

        exit = new Button();
        exit.setGraphic(new ImageView("exit.png"));

        setSideButtonsStyle(home);
        setSideButtonsStyle(newShipment);
        setSideButtonsStyle(report);
        setSideButtonsStyle(load);
        setSideButtonsStyle(save);
        setSideButtonsStyle(exit);
    }

    public VBox getSideButtons() { // a method to assemble side buttons together
        VBox btns = new VBox(10, home, newShipment, report, load, save, exit);
        btns.setAlignment(Pos.TOP_LEFT);
        btns.setPadding(new Insets(5));

        return btns;
    }

    private void actions() { // a method to handle all action
        home.setOnAction(e -> Main.setMain(new ProductManagement().main()));
        newShipment.setOnAction(e -> Main.setMain(new NewShipment().main()));

        load.setOnAction(e -> { // load data from files

        });
        exit.setOnAction(e -> stage.close());

        save.setOnAction(e -> { // sava data to file

        });

//        report.setOnAction(e -> Main.setMain(new Report().main()));
    }
}
