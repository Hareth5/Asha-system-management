package proj;

import javafx.beans.property.SimpleStringProperty;

import static proj.Catalog.getProducts;

public class Category { // a class for category
    private SimpleStringProperty id, name, description;
    public static int CategoryCounter = 1;
    public int cursorIndex = getProducts().createList();
    private int approvedCategories = 0;
    private int canceledCategories = 0;

    public Category(SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty description) throws IllegalArgumentException {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();

        setId(id.get());
        setName(name.get());
        setDescription(description.get());
    }

    // getters and setters for category data

    public String getId() {
        return id.get();
    }

    public void setId(String id) throws IllegalArgumentException {
        if (isNull(id))
            throw new IllegalArgumentException("ID cannot be null");

        if (!validation(id, "[0-9]+"))
            throw new IllegalArgumentException("ID must contain only numbers");

        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) throws IllegalArgumentException {
        if (isNull(name))
            throw new IllegalArgumentException("Name cannot be empty");

        if (!validation(name, "^[a-zA-Z ]+$"))
            throw new IllegalArgumentException("Name must contain only letters and spaces");

        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (isNull(description))
            throw new IllegalArgumentException("Description cannot be empty");

        this.description.set(description);
    }

    public int getApprovedCategories() {
        return approvedCategories;
    }

    public void setApprovedCategories(int approvedCategories) {
        this.approvedCategories = approvedCategories;
    }

    public int getCanceledCategories() {
        return canceledCategories;
    }

    public void setCanceledCategories(int canceledCategories) {
        this.canceledCategories = canceledCategories;
    }

    private boolean isNull(String value) { // a method to check if the input is null
        return value == null || value.trim().isEmpty();
    }

    private boolean validation(String value, String regex) { // a method to check if the input has a valid expression
        return value.matches(regex);
    }

    @Override
    public String toString() { // toString method
        return getName();
    }
}