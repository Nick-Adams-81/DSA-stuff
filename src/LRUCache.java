import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    // Setting up our hash map and cache capacity.
    Map<Integer, Node> map;
    int cacheCapacity;

    // building a node class that has key, value, prev and next pointers.
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
    }

    // building a doubly linked list class that handles the head and tail nodes, and add and remove methods for the doubly linked list.
    class DLL {

        // creating empty head and tail nodes for reference to the front and end of the list.
        Node head = new Node();
        Node tail = new Node();

        // method that handles adding nodes to the list.
        public void add(Node node) {
            // storing the next pointer to the head node, so we can move it to point at the new node.
            Node headNext = head.next;
            // setting the next pointer of the head node to point at the new node.
            head.next = node;
            // setting the prev pointer of the new node to point at the head node.
            node.prev = head;
            // setting the next pointer of the new node to point at the previously stored value of headNext.
            node.next = headNext;
            // setting the prev pointer of the previously stored headNext node to point at the new node.
            headNext.prev = node;
        }

        // method that handles removing nodes from the list.
        public void remove(Node node) {
            // storing the next pointer of the node we are removing.
            Node nextNode = node.next;
            // storing the prev pointer of the node we are removing.
            Node prevNode = node.prev;
            // moving the prev pointer of nextNode to point at the prevNode.
            nextNode.prev = prevNode;
            // moving the next pointer of the prevNode to point at the nextNode.
            prevNode.next = nextNode;
        }
    }

    DLL dll = new DLL();

    public LRUCache(int capacity) {
        map = new HashMap(capacity);
        this.cacheCapacity = capacity;
        dll.head.next = dll.tail;
        dll.tail.prev = dll.head;
    }

    public int get(int key) {
        int result = -1;
        Node node = map.get(key);
        if(node != null) {
            result = node.val;
            dll.remove(node);
            dll.add(node);
        }
        return result;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if(node != null) {
            dll.remove(node);
            node.val = value;
            dll.add(node);
        } else {
            if(map.size() == cacheCapacity) {
                map.remove(dll.tail.prev.key);
                dll.remove(dll.tail.prev);
            }
            Node newNode = new Node();
            newNode.key = key;
            newNode.val = value;

            map.put(key, newNode);
            dll.add(newNode);
        }
    }

}
