package Practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Graph {

    public static void main(String[] args) {
        ArrayList<Integer> x_values = new ArrayList<>();
        int size = 21;
        for (int i = 0; i < size; i++) {
            x_values.add(i);
        }
        Collections.shuffle(x_values);
        ArrayList<Node> nodes = new ArrayList<>();
        // generate 20 nodes
        for (int i = 0; i < size; i++) {
            nodes.add(new Node(x_values.get(i)));
        }
        // link them randomly
        linking(nodes);

        long start = System.currentTimeMillis();
        String firstTime = java.time.LocalTime.now().toString();

        Node finalNode = processing(nodes);

        System.out.println("Time start: " + firstTime);
        System.out.println("Time end: " + java.time.LocalTime.now());
        System.out.println("Total time taken for execution: "
                + (System.currentTimeMillis() - start) + " milli second");

        System.out.println("node " + finalNode.getNumber() + " was selected.");
        System.out.println(finalNode.connection());

    }

    private static Node processing(ArrayList<Node> nodes) {

        Random rand = new Random();
        String out = "";
        /*Node t = getRandomNode(rand, nodes);
        t.setState(false);*/
        setFirstNode(nodes);
        
        
        ArrayList<String> result = new ArrayList<>();
        System.out.println("First List: ");
        System.out.println("");
        for (Node node : nodes) {
            out += node.toString() + System.lineSeparator();
        }
        
        System.out.println(out);
        Node finalNode = null;
        do {
            //result.add(out);
            // 2, pick a random node
            out = "";
            Node n = getRandomNode(rand, nodes);

            for (Node node : nodes) {
                out += node.toString() + System.lineSeparator();
            }

            // do comparing
            n.comparing(nodes);
            finalNode = n;
        } while (isAllTrue(nodes) == false);

        /*for (String string : result) {
            System.out.println(string + System.lineSeparator());
        }*/
        System.out.println("");
        System.out.println("Final List before stopping:");
        System.out.println("");
        System.out.println(out);
        return finalNode;
    }

    private static void setFirstNode(ArrayList<Node> nodes){
        System.out.println("Enter a number from 0 to " + (nodes.size() - 1)
                + " to set it to false");
        Scanner scanner = new Scanner(System.in);
        int num;
        do {            
            num = scanner.nextInt();
        } while (num < 0 || num >= nodes.size());
        Node t = nodes.get(num);
        t.setState(false);
    }
    
    public static boolean isAllTrue(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            if (node.getState() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * linking nodes together
     *
     * @param nodes
     */
    private static void linking(ArrayList<Node> nodes) {
        // one node never connect to itself 
        int size = nodes.size() / 4;
        if (size > 5) {
            size = 5;
        }
        Random rand = new Random();
        // for each node
        for (Node node : nodes) {
            // get random number of node to link to this node
            // a node must has 1 connection at least
            int time = (int) (Math.random() * size) + 1;

            // clone the original list 
            // so that we could remove item from the cloned one 
            // without any worrying
            ArrayList<Node> tmp = (ArrayList<Node>) nodes.clone();
            // a node shouldn't link to itself
            tmp.remove(node.getNumber());

            // get reference to list of neighbor of this node
            ArrayList<Node> neighbor = node.getNeighbor();

            for (int i = 0; i < time && tmp.isEmpty() == false; i++) {
                int randomIndex = rand.nextInt(tmp.size());
                Node n = tmp.get(randomIndex);
                neighbor.add(n);
                tmp.remove(randomIndex);
            }
        }
    }

    private static Node getRandomNode(Random rand, ArrayList<Node> nodes) {
        int randomIndex = rand.nextInt(nodes.size());
        Node n = nodes.get(randomIndex);
        return n;
    }
}

class Node {

    private int number;
    private int x_value;
    private boolean state;
    private ArrayList<Node> neighbor;

    private static int counter = 0;

    public Node(int x_value) {
        state = true;
        this.x_value = x_value;
        number = counter;
        neighbor = new ArrayList<>();
        counter++;
    }

    public int getNumber() {
        return number;
    }

    public int getX_value() {
        return x_value;
    }

    public boolean getState() {
        return state;
    }

    public ArrayList<Node> getNeighbor() {
        return neighbor;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * When comparing, if the neighbor x value is smaller than the node then add
     * 1 and keep the default as true if greater than add 10 then flip from what
     * it is currently to the opposite sign. Ultimately what really matter is
     * when the false node flip from false to true and that only happen when
     * that node x value is greater than the rest of it neighbor.
     */
    public void comparing(ArrayList<Node> nodes) {

        boolean notFound = true;

        for (Node node : neighbor) {
            if (node.x_value > this.x_value) {

                this.x_value += 10;
                state = !state;
                notFound = false;

                break;
            }
        }
        if (notFound) {
            this.state = true;
            x_value += 1;
        }

    }

    public String connection() {
        String result = "node " + number;
        result += " { ";

        result += neighbor.get(0).number;
        for (int i = 1; i < neighbor.size(); i++) {
            Node get = neighbor.get(i);
            result += ", " + get.number;
        }

        result += " } --> " + state + System.lineSeparator();
        result += "the new x value for node "
                + number + ": ";
        result += neighbor.get(0).x_value;
        for (int i = 1; i < neighbor.size(); i++) {
            Node get = neighbor.get(i);
            result += ", " + get.x_value;
        }
        result += " " + x_value;
        return result;
    }

    /**
     *
     * @return description of the node with its neighbor
     */
    @Override
    public String toString() {
        String result = "Node:  -> ";
        result += " { ";

        result += neighbor.get(0).number;
        for (int i = 1; i < neighbor.size(); i++) {
            Node get = neighbor.get(i);
            result += ", " + get.number;
        }

        result += " } -> " + number + " { " + state + " }"
                + System.lineSeparator();

        result += "X-value: ";
        result += neighbor.get(0).x_value;
        for (int i = 1; i < neighbor.size(); i++) {
            Node get = neighbor.get(i);
            result += ", " + get.x_value;
        }
        result += " " + x_value;

        return result;
    }

}
