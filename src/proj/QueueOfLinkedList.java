package proj;

public class QueueOfLinkedList {
    private Node front, rear;
    private int size;

    private class Node {
        private Object data;
        private Node next;

        public Node(Object data, Node next) {
            this.next = next;
            this.data = data;
        }

        public Node(Object data) {
            this(data, null);
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
    }

    public QueueOfLinkedList() {
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int getSize() {
        return size;
    }

    public void enqueue(Object data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        Node newNode = new Node(data);
        if (isEmpty())
            front = rear = newNode;

        else {
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;
    }

    public Object dequeue() {
        if (isEmpty())
            throw new IllegalArgumentException("The queue is empty");

        Object data = front.getData();
        front = front.getNext();
        if (front == null)
            rear = null;

        size--;
        return data;
    }

    public Object peek() {
        if (isEmpty())
            throw new IllegalArgumentException("The list is empty");

        return front.getData();
    }

    public void clear() {
        front = rear = null;
        size = 0;
    }

    private void printHelper(int s) {
        if (s == 0)
            return;

        else {
            Object temp = dequeue();
            System.out.println(temp + " ");
            enqueue(temp);
            printHelper(--s);
        }
    }
}
