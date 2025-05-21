package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cursor {
    private Node[] cursor;
    private static final int MAX_SIZE = 1000;

    private class Node {
        private Object data;
        private int next;

        private Node(Object data, int next) {
            this.data = data;
            this.next = next;
        }

        private Node(Object data) {
            this(data, 0);
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }
    }

    public Cursor() {
        cursor = new Node[MAX_SIZE];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < MAX_SIZE - 1; i++) {
            cursor[i] = new Node(null, i + 1);
        }
        cursor[MAX_SIZE - 1] = new Node(null, 0);
    }

    private int cursorAlloc() {
        int p = cursor[0].getNext();
        if (p == 0)
            throw new IllegalStateException("Out of space");

        cursor[0].setNext(cursor[p].getNext());
        return p;
    }

    private void freeList(int index) {
        cursor[index].setData(null);
        cursor[index].setNext(cursor[0].getNext());
        cursor[0].setNext(index);
    }

    private int findPrevious(Object data, int l) {
        if (isEmpty(l)) {
            return -1;
        }

        int prev = l;
        int current = cursor[l].getNext();
        while (current != 0) {
            if (((Product) cursor[current].getData()).getId().equals(((Product) data).getId())) {
                return prev;
            }
            prev = current;
            current = cursor[current].getNext();
        }
        return -1;
    }

    public boolean isEmpty(int l) {
        return l == 0 || cursor[l].getNext() == 0;
    }

    public boolean isLast(int p) {
        return cursor[p].getNext() == 0;
    }

    public int getSize(int l) {
        if (isEmpty(l))
            return 0;

        int size = 0;
        int current = cursor[l].getNext();
        while (current != 0) {
            size++;
            current = cursor[current].getNext();
        }
        return size;
    }

    public int createList() {
        int l = cursorAlloc();
        cursor[l] = new Node("-", 0);
        return l;
    }

    public void insertAtHead(Object data, int l) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        int p = cursorAlloc();
        if (p == 0)
            throw new IllegalStateException("Out of space");

        cursor[p] = new Node(data, cursor[l].getNext());
        cursor[l].setNext(p);
    }

    public void insertAtLast(Object data, int l) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        int p = cursorAlloc();
        if (p == 0)
            throw new IllegalStateException("Out of space");

        int current = l;
        while (cursor[current].getNext() != 0) {
            current = cursor[current].getNext();
        }
        cursor[p] = new Node(data, 0);
        cursor[current].setNext(p);
    }

    public void insert(Object data, int l, int index) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (isEmpty(l))
            throw new IllegalArgumentException("The list is empty");

        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative");

        if (index == 0) {
            insertAtHead(data, l);
            return;
        }

        int current = cursor[l].getNext();
        int i = 0;
        while (current != 0 && i < index - 1) {
            current = cursor[current].getNext();
            i++;
        }

        if (current == 0) {
            insertAtLast(data, l);

        } else {
            int p = cursorAlloc();
            if (p == 0)
                throw new IllegalStateException("Out of space");

            cursor[p] = new Node(data, cursor[current].getNext());
            cursor[current].setNext(p);
        }
    }

    public Object removeFirst(int l) {
        if (isEmpty(l))
            return null;

        int temp = cursor[l].getNext();
        Object data = cursor[temp].getData();
        cursor[l].setNext(cursor[temp].getNext());
        freeList(temp);
        return data;
    }

    public Object removeLast(int l) {
        if (isEmpty(l))
            return null;

        int prev = l;
        int current = cursor[l].getNext();
        while (cursor[current].getNext() != 0) {
            prev = current;
            current = cursor[current].getNext();
        }

        Object data = cursor[current].getData();
        cursor[prev].setNext(0);
        freeList(current);
        return data;
    }

    public Object remove(Object data, int l) {
        if (isEmpty(l))
            return null;

        int first = cursor[l].getNext();
        if (first != 0 && ((Product) cursor[first].getData()).getId().equals(((Product) data).getId()))
            return removeFirst(l);

        int prev = findPrevious(data, l);
        if (prev == -1)
            return null;

        int toRemove = cursor[prev].getNext();
        if (toRemove == 0)
            return null;

        Object removedData = cursor[toRemove].getData();
        cursor[prev].setNext(cursor[toRemove].getNext());
        freeList(toRemove);
        return removedData;
    }

    public boolean removeAll(Category category, int l) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");

        boolean removed = false;
        int current = cursor[l].getNext();
        int prev = l;

        while (current != 0) {
            Product product = (Product) cursor[current].getData();
            if (product.getCategory().equals(category)) {
                int next = cursor[current].getNext();
                cursor[prev].setNext(next);
                freeList(current);
                current = next;
                removed = true;
            } else {
                prev = current;
                current = cursor[current].getNext();
            }
        }

        return removed;
    }

    public Object[] clear(int l) {
        ObservableList<Product> list = FXCollections.observableArrayList();
        int active = 0, inActive = 0;

        if (isEmpty(l))
            return new Object[]{list, active, inActive};
        ;

        int current = l;
        while (current != 0) {
            int next = cursor[current].getNext();
            if (((Product) cursor[next].getData()).getStatus().equals("Active"))
                active++;
            else
                inActive++;
            freeList(current);
            current = next;
            if (current != l)
                list.add((Product) cursor[current].getData());
        }

        return new Object[]{list, active, inActive};
    }


    public Object get(int l, int index) {
        if (isEmpty(l))
            throw new IllegalArgumentException("The list is empty");

        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative");

        int p = cursor[l].getNext();
        int i = 0;

        while (p != 0 && i < index) {
            p = cursor[p].getNext();
            i++;
        }

        return cursor[p].getData();
    }

    public int find(Object data, int l) {
        if (isEmpty(l))
            return -1;

        int p = cursor[l].getNext();
        while (p != 0) {
            if (cursor[p].getData().equals(data))
                return p;
            p = cursor[p].getNext();
        }
        return -1;
    }

    public void updateCategory(Category oldCategory, Category newCategory, int l) {
        if (oldCategory == null || newCategory == null) {
            throw new IllegalArgumentException("Categories cannot be null");
        }

        int current = cursor[l].getNext();

        while (current != 0) {
            Product product = (Product) cursor[current].getData();
            if (product.getCategory().equals(oldCategory))
                product.setCategory(newCategory);
            current = cursor[current].getNext();
        }
    }

    public void insertSortedByName(Object data, int l) {
        if (data == null)
            throw new IllegalArgumentException("Data cannot be null");

        if (!(data instanceof Product))
            throw new IllegalArgumentException("Data should be a product type");

        if (isEmpty(l)) {
            insertAtHead(data, l);
            return;
        }

        int p = cursorAlloc();
        if (p == 0)
            throw new IllegalStateException("Out of space");

        int prev = l;
        int current = cursor[l].getNext();

        while (current != 0 && ((Product) cursor[current].getData()).compareTo((Product) data) < 0) {
            prev = current;
            current = cursor[current].getNext();
        }

        cursor[p] = new Node(data, current);
        cursor[prev].setNext(p);
    }

    public Product searchID(int l, String id) {
        if (isEmpty(l))
            return null;

        int p = cursor[l].getNext();
        while (p != 0) {
            Object current = cursor[p].getData();
            if (!(current instanceof Product))
                throw new IllegalArgumentException("List should be products list");

            Product product = (Product) current;
            if (product.getId().equals(id)) {
                return product;
            }
            p = cursor[p].getNext();
        }
        return null;
    }

    public ObservableList<Product> searchName(int l, String name) {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        if (isEmpty(l))
            return productsList;

        Object current = cursor[cursor[l].getNext()].getData();
        if (!(current instanceof Product))
            throw new IllegalArgumentException("List should be Product list");

        int p = cursor[l].getNext();
        while (p != 0) {
            if (((Product) cursor[p].getData()).getName().toLowerCase().startsWith(name.toLowerCase()))
                productsList.add((Product) cursor[p].getData());
            p = cursor[p].getNext();
        }
        return productsList;
    }

    public ObservableList<Product> getAllProducts(int l) {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        if (isEmpty(l))
            return productList;

        Object current = cursor[cursor[l].getNext()].getData();

        if (!(current instanceof Product))
            throw new IllegalArgumentException("List should be Product list");

        int p = cursor[l].getNext();
        while (p != 0) {
            productList.add(((Product) cursor[p].getData()));
            p = cursor[p].getNext();
        }
        return productList;
    }

    public ObservableList<Product> getAllActiveProducts(int l) {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        if (isEmpty(l))
            return productList;

        Object current = cursor[cursor[l].getNext()].getData();

        if (!(current instanceof Product))
            throw new IllegalArgumentException("List should be Product list");

        int p = cursor[l].getNext();
        while (p != 0) {
            if (((Product) cursor[p].getData()).getStatus().equals("Active"))
                productList.add(((Product) cursor[p].getData()));
            p = cursor[p].getNext();
        }
        return productList;
    }

    public ObservableList<Product> getAllInactiveProducts(int l) {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        if (isEmpty(l))
            return productList;

        Object current = cursor[cursor[l].getNext()].getData();

        if (!(current instanceof Product))
            throw new IllegalArgumentException("List should be Product list");

        int p = cursor[l].getNext();
        while (p != 0) {
            if (((Product) cursor[p].getData()).getStatus().equals("Inactive"))
                productList.add(((Product) cursor[p].getData()));
            p = cursor[p].getNext();
        }
        return productList;
    }

    public String productSummary(int l) {
        StringBuilder sb = new StringBuilder();
        if (isEmpty(l))
            return sb.toString();

        int current = cursor[l].getNext();
        while (current != 0) {
            Product product = (Product) cursor[current].getData();
            sb.append(product.getName() + ": " + product.getAmount() + " Units" + "\n");
            current = cursor[current].getNext();
        }
        return sb.toString();
    }


    public String countShipments(int l) {
        StringBuilder sb = new StringBuilder();
        if (isEmpty(l))
            return sb.toString();

        int current = cursor[l].getNext();
        while (current != 0) {
            Product product = (Product) cursor[current].getData();
            sb.append(product.getName() + ": " + product.getNumOfShipments() + " Shipments" + "\n");
            current = cursor[current].getNext();
        }
        return sb.toString();
    }
}