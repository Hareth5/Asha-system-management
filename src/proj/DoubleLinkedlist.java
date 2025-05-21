package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class DoubleLinkedlist {

    private Node front, back;
    private int size;

    private class Node {
        private Object data;
        private Node next, prev;

        public Node(Object data, Node next, Node prev) {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }

        public Node(Object data) {
            this(data, null, null);
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    public DoubleLinkedlist() {
        front = back = null;
        size = 0;
    }

    public Node getFront() {
        return front;
    }

    public void setFront(Node front) {
        this.front = front;
    }

    public Node getBack() {
        return back;
    }

    public void setBack(Node back) {
        this.back = back;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }


    public Object getFirst() {
        if (getFront() == null)
            return null;
        else
            return getFront().getData();
    }

    public Object getLast() {
        if (getFront() == null)
            return null;
        else
            return getBack().getData();
    }

    public void addFirst(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        Node newNode = new Node(data);
        if (size == 0) {
            setFront(newNode);
            setBack(newNode);

        } else {
            newNode.setNext(getFront());
            getFront().setPrev(newNode);
            setFront(newNode);
        }

        size++;
    }

    public void addLast(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        Node newNode = new Node(data);
        if (size == 0) {
            setFront(newNode);
            setBack(newNode);

        } else {
            back.setNext(newNode);
            newNode.setPrev(getBack());
            setBack(newNode);
        }
        size++;
    }


    public void add(int index, Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0");
        }

        if (index == 0)
            addFirst(data);

        else if (index >= getSize() - 1)
            addLast(data);

        else {
            Node newNode = new Node(data);

            if (index <= getSize() / 2) {
                Node current = getFront();

                while (index-- > 1) {
                    current = current.getNext();
                }

                newNode.setNext(current.getNext());
                current.setNext(newNode);
                newNode.setPrev(current);
                newNode.getNext().setPrev(newNode);

            } else {
                Node current = getBack();
                index = getSize() - 1 - index;

                while (index-- > 1) {
                    current = current.getPrev();
                }

                newNode.setPrev(current.getPrev());
                current.setPrev(newNode);
                newNode.setNext(current);
                newNode.getPrev().setNext(newNode);
            }

            size++;
        }
    }

    public boolean removeFirst() {
        if (size == 0)
            return false;

        else if (getSize() == 1) {
            setFront(null);
            setBack(null);

        } else {
            setFront(getFront().getNext());
            getFront().setPrev(null);
        }

        size--;
        return true;
    }


    public boolean removeLast() {
        if (size == 0)
            return false;

        else if (getSize() == 1) {
            setFront(null);
            setBack(null);

        } else {
            setBack(getBack().getPrev());
            getBack().setNext(null);
        }

        size--;
        return true;
    }


    public boolean remove(int index) {
        if (size == 0)
            return false;

        if (index < 0)
            throw new IndexOutOfBoundsException("Index cannot be less than 0");

        if (index == 0)
            return removeFirst();

        else if (index >= getSize() - 1)
            return removeLast();

        else {

            Node current;

            if (index <= getSize() / 2) {
                current = getFront().getNext();

                while (index-- > 1) {
                    current = current.getNext();
                }

            } else {
                current = getBack();
                index = getSize() - 1 - index;

                while (index-- > 1) {
                    current = current.getPrev();
                }
            }

            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());

            size--;
            return true;
        }
    }

    public boolean remove(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (size == 0)
            return false;

        if (data.equals(getFront().getData()))
            return removeFirst();

        Node current = getFront();
        while (current.getNext() != null) {
            if (data.equals(current.getNext().getData())) {
                current.setNext(current.getNext().getNext());
                if (current.getNext() == null)
                    setBack(current);
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public Object get(int index) {
        if (size == 0)
            return null;

        if (index > getSize() - 1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of bound");

        Node current;

        if (index <= size / 2) {
            current = getFront();
            while (index-- > 0)
                current = current.getNext();


        } else {
            current = getBack();
            int reverseIndex = getSize() - 1 - index;
            while (reverseIndex-- > 0)
                current = current.getPrev();

        }

        return current.getData();
    }

    public void set(int index, Object data) {
        if (size == 0)
            throw new IllegalArgumentException("The list is empty");

        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (index > getSize() - 1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of bound");

        Node current = getFront();
        while (index-- > 0) {
            current = current.getNext();
        }
        current.setData(data);
    }


    public int searchName(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (size == 0)
            return -1;

        int count = 0;
        Node current = getFront();
        while (current != null) {
            if (current.getData() != null && current.getData().equals(data))
                return count;
            else {
                current = current.getNext();
                count++;
            }
        }
        return -1;
    }

    public boolean contains(Object data) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (size == 0)
            return false;

        Node current = getFront();
        while (current != null) {
            if (current.getData() != null && current.getData().equals(data))
                return true;
            else
                current = current.getNext();
        }
        return false;
    }

    public void clear() {
        setFront(null);
        setBack(null);
        size = 0;
    }

    public void printList() {
        System.out.print("[");
        print(getFront());
        System.out.println("]");
    }

    private void print(Node front) {
        if (front == null)
            return;

        else if (front.getNext() == null) {
            System.out.print(front.getData());
            print(front.getNext());

        } else {
            System.out.print(front.getData() + " <--> ");
            print(front.getNext());
        }
    }

    public boolean findID(String id) {
        if (size == 0)
            return false;

        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            if (((Category) current.getData()).getId().equals(id))
                return true;
            current = current.getNext();
        }
        return false;
    }

    public boolean findName(String name) {
        if (size == 0)
            return false;

        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            if (((Category) current.getData()).getName().equals(name))
                return true;
            current = current.getNext();
        }
        return false;
    }

    public Category findCategory(String category) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");

        if (size == 0)
            return null;

        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            if (((Category) current.getData()).getName().equals(category))
                return (Category) current.getData();
            current = current.getNext();
        }
        return null;
    }

    public ObservableList<Category> searchName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        if (size == 0)
            return null;

        ObservableList<Category> temp = FXCollections.observableArrayList();
        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            if (((Category) current.getData()).getName().toLowerCase().startsWith(name.toLowerCase()))
                temp.add((Category) current.getData());
            current = current.getNext();
        }
        return temp;
    }

    public Category getCategory(String name) {
        if (size == 0)
            return null;

        Node current = getFront();

        while (current != null) {
            if (((Category) current.getData()).getName().equals(name))
                return (Category) current.getData();
        }
        return null;
    }

    public ComboBox<String> getAllCategoriesString() {
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().add("Select all categories");

        if (size == 0)
            return categoryComboBox;

        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            categoryComboBox.getItems().add(((Category) current.getData()).getName());
            current = current.getNext();
        }
        return categoryComboBox;
    }

    public ComboBox<Category> getAllCategories() {
        if (size == 0)
            return null;

        ComboBox<Category> categoryComboBox = new ComboBox<>();
        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            categoryComboBox.getItems().add(((Category) current.getData()));
            current = current.getNext();
        }
        return categoryComboBox;
    }

    public ObservableList<Product> getAllProducts() {
        if (size == 0)
            return null;

        ObservableList<Product> productList = FXCollections.observableArrayList();
        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            productList.addAll(Catalog.getProducts().getAllProducts(((Category) current.getData()).cursorIndex));
            current = current.getNext();
        }
        return productList;
    }

    public ObservableList<Product> getAllActiveProducts() {
        if (size == 0)
            return null;

        ObservableList<Product> productList = FXCollections.observableArrayList();
        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            productList.addAll(Catalog.getProducts().getAllActiveProducts(((Category) current.getData()).cursorIndex));
            current = current.getNext();
        }
        return productList;
    }

    public ObservableList<Product> getAllInactiveProducts() {
        if (size == 0)
            return null;

        ObservableList<Product> productList = FXCollections.observableArrayList();
        Node current = getFront();
        if (!(current.getData() instanceof Category))
            throw new IllegalArgumentException("List should be category list");

        while (current != null) {
            productList.addAll(Catalog.getProducts().getAllInactiveProducts(((Category) current.getData()).cursorIndex));
            current = current.getNext();
        }
        return productList;
    }

    public String categoriesStatistics() {
        if (size == 0)
            return "There are no categories";

        StringBuilder stb = new StringBuilder();
        Node current = getFront();
        while (current != null) {
            if (((Category) current.getData()).getApprovedCategories() > 0) {
                stb.append(((Category) current.getData()).getName() + ": " + ((Category) current.getData()).getCanceledCategories() + '/'
                        + ((Category) current.getData()).getApprovedCategories() + " ("
                        + (double) ((Category) current.getData()).getCanceledCategories() /
                        (double) ((Category) current.getData()).getApprovedCategories() * 100 + "%)\n");
            }
            current = current.getNext();
        }
        if (stb.isEmpty())
            return "There are no canceled shipments";

        return stb.toString();
    }
}
