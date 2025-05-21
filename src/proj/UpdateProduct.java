package proj;

public class UpdateProduct extends AddProduct { // class for update an existing product
    private Product product;

    public UpdateProduct(Product product) { // initialize the scene components
        super(product);
        this.product = product;
        title.setText("Update Product");
        action.setText("Update");
        fill();
        action();
    }

    private void fill() { // fill the product data automatically
        id.setText(product.getId());
        name.setText(product.getName());
        status.setValue(product.getStatus());
        category.setValue(product.getCategory().getName());
    }

    private void action() { // a method to handle all actions
        cancel.setOnAction(e -> Main.setMain(new CategoryManagement().main()));
    }
}
