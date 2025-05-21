package proj;

public class StackOfLinkedList {
    private Node front;

    private class Node {
        private Object data;
        private Node next;

        private Node(Object data, Node next) {
            this.next = next;
            this.data = data;
        }

        private Node(Object data) {
            this(data, null);
        }

        private Object getData() {
            return data;
        }

        private void setData(Object data) {
            this.data = data;
        }

        private Node getNext() {
            return next;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }

    public StackOfLinkedList() {
    }

    private Node getFront() {
        return front;
    }

    private void setFront(Node front) {
        this.front = front;
    }

    public boolean isEmpty() {
        return getFront() == null;
    }

    public void push(Object data) {
        front = new Node(data, getFront());
    }

    public Object pop() {
        if (!isEmpty()) {
            Object temp = getFront().getData();
            setFront(getFront().getNext());
            return temp;
        }
        return null;
    }

    public Object peek() {
        if (!isEmpty())
            return getFront().getData();
        return null;
    }

    public String print() {
        if (isEmpty())
            return "There are no data";

        StringBuilder stb = new StringBuilder();
        return printHelper(stb).toString();
    }

    private StringBuilder printHelper(StringBuilder stb) {
        if (isEmpty())
            return stb;
        Record temp = (Record) pop();
        StringBuilder stb2 = printHelper(stb);
        stb.append("Shipment ID: " + temp.getShipment().getId() + ", ");
        stb.append("Product ID: " + temp.getShipment().getProduct().getId() + ", ");
        stb.append("Product Name: " + temp.getShipment().getProduct().getName() + ", ");
        stb.append("Date: " + temp.getShipment().getDate() + ", ");
        stb.append("Amount: " + temp.getShipment().getAmount() + ", ");
        stb.append("Status: " + temp.getStatus() + "\n\n");
        push(temp);
        return stb2;
    }
}
