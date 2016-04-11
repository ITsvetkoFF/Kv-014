package IlliaDoroshenkoHorse;

import java.util.Comparator;

/**
 * Created by Ilya on 07.04.2016.
 */
public class Node {
    private Node parent;
    private int i;
    private int j;

    private double g;  // g is distance from the source
    private double h;  // h is the heuristic of destination.
    private double f;  // f = g + h

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void calcF(Node finish) {
        h = Math.sqrt(Math.pow(finish.getI() - this.i,2)+ Math.pow(finish.getJ() - this.j,2))/3.0;/*Math.abs(finish.getI() - this.i)+ Math.abs(finish.getJ() - this.j);*/
        f = g + h;
    }

    public static class NodeComparator implements Comparator<Node> {
        public int compare(Node nodeFirst, Node nodeSecond) {
            if (nodeFirst.getF() > nodeSecond.getF()) return 1;
            if (nodeFirst.getF() < nodeSecond.getF()) return -1;
            return 0;
        }
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (i != node.i) return false;
        return j == node.j;

    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "Node{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }


}
