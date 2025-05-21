package proj;

public class UpdateCategory extends AddCategory { // class for update an existing category
    private Category category;

    public UpdateCategory(Category category) { // initialize the scene components
        super(category);
        this.category = category;
        title.setText("Update Category");
        action.setText("Update");
        fill();
        action();
    }

    private void fill() { // fill the category data automatically
        name.setText(category.getName());
        description.setText(category.getDescription());
    }

    private void action() { // a method to handle all actions
        cancel.setOnAction(e-> Main.setMain(new CategoryManagement().main()));
    }
}
