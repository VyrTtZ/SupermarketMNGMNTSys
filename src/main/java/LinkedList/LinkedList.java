package LinkedList;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head; // first node

    public LinkedList() {
        head = null;
    }

    public void addNode(Node<T> n) {
        if (head == null) {
            head = n;
        } else {
            Node<T> current = head;
            while (current.next != null) current = current.next;
            current.next = n;
        }
    }

    public void add(T value) {
        addNode(new Node<>(value, null));
    }

    public boolean remove(T item) {
        if (head == null) return false;

        if (head.data.equals(item)) {
            head = head.next;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(item)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public T get(int i) {
        Node<T> current = head;
        int j = 0;

        while (current != null && j < i) {
            current = current.next;
            j++;
        }

        return (current != null) ? current.data : null;
    }

    public static <T> void removeAllInstances(LinkedList<T> list, T target) {
        if (list == null || list.isEmpty()) return;

        int i = 0;
        while (i < list.size()) {
            T item = list.get(i);
            if (item == target || (item != null && item.equals(target))) {
                list.remove(list.get(i)); // assume your LinkedList has a remove(index) method
            } else {
                i++;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.data;
                current = current.next;
                return value;
            }
        };
    }
}
