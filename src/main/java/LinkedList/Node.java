package LinkedList;

public class Node<T> {
    T data;
    Node<T> next;

    public Node(T d, Node<T> n){
        data = d;
        next = n;
    }

}
