package question2;
import java.util.*;

public class CyclicTreeSerializer implements TreeSerializer {

    private Map<Node, Integer> nodeIds;
    private Map<Integer, Node> idToNodeMap;
    private int nextNodeId;

    @Override
    public String serialize(Node root) {
        nodeIds = new HashMap<>();
        idToNodeMap = new HashMap<>();
        nextNodeId = 1;
        return serializeHelper(root);
    }

    private String serializeHelper(Node node) {
        if (node == null) {
            return "#";
        }

        if (nodeIds.containsKey(node)) {
            return String.valueOf(nodeIds.get(node));
        }

        int nodeId = nextNodeId++;
        nodeIds.put(node, nodeId);
        idToNodeMap.put(nodeId, node);

        return nodeId + "," + serializeHelper(node.left) + "," + serializeHelper(node.right);
    }

    @Override
    public Node deserialize(String str) {
        throw new RuntimeException("Deserialization of cyclic trees is not supported.");
    }

    public static void main(String[] args) {
        // Usage example
        Node root = new Node();
        root.num = 1;
        root.left = new Node();
        root.left.num = 2;
        root.right = new Node();
        root.right.num = 3;
        root.left.left = root; // Creating a cyclic connection

        CyclicTreeSerializer serializer = new CyclicTreeSerializer();
        String serialized = serializer.serialize(root);
        System.out.println("Serialized: " + serialized);

        try {
            Node deserialized = serializer.deserialize(serialized);
            System.out.println("Deserialized root's value: " + deserialized.num);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
