package LinkedList;

public class LinkedList <T>{
        private Node<T> node;
        private String name;

        public LinkedList(){

        }

        public void add(Node n){
            Node<T> prevNode;
            if(node == null)
                node = n;
            else{
                prevNode = node;
                while(node.next != null) {
                    prevNode = node.next;
                }
                prevNode.next = n;
            }

        }
        public boolean isEmpty(){
            if(node == null){
                return true;
            }
            else return false;
        }
}
