package question2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Node {
    Node left;
    Node right;
    int num;
}

interface TreeSerializer {
    String serialize(Node root);
    Node deserialize(String str);
}

public class BinarySearchTreeSerializer implements TreeSerializer {

    @Override
    public String serialize(Node root) {
        if (root == null) {
            return "#";
        }
        return root.num + "," + serialize(root.left) + "," + serialize(root.right);
    }

    @Override
    public Node deserialize(String str) {
        Queue<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
        return deserializeHelper(nodes);
    }

    private Node deserializeHelper(Queue<String> nodes) {
        String val = nodes.poll();
        if (val.equals("#")) {
            return null;
        }
        Node root = new Node();
        root.num = Integer.parseInt(val);
        root.left = deserializeHelper(nodes);
        root.right = deserializeHelper(nodes);
        return root;
    }

    public static void main(String[] args) {
        // Usage example
        Node root = new Node();
        root.num = 1;
        root.left = new Node();
        root.left.num = 2;
        root.right = new Node();
        root.right.num = 3;

        BinarySearchTreeSerializer serializer = new BinarySearchTreeSerializer();
        String serialized = serializer.serialize(root);
        System.out.println("Serialized: " + serialized);

        Node deserialized = serializer.deserialize(serialized);
        System.out.println("Deserialized root's value: " + deserialized.num);
    }
}
