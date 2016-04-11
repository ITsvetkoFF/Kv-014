package SerhiiAlekseichenkoHorse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

class Map {

    private Node[][] nodes;
    private TreeSet<Node> closedList;
    private Node start;
    private Node finish;

    Map(int[][] matrix) {
        int width = matrix[0].length;
        int height = matrix.length;
        nodes = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[j][i] == 0) {
                    nodes[i][j] = new Node(i, j);
                } else if (matrix[j][i] == -2) {
                    nodes[i][j] = new Node(i, j);
                    start = nodes[i][j];
                } else if (matrix[j][i] == -3) {
                    nodes[i][j] = new Node(i, j);
                    finish = nodes[i][j];
                }
            }
        }
    }

    private Node getNode(int x, int y) {
        if (x < 0 || y < 0 || x >= nodes.length || y >= nodes[0].length) {
            return null;
        }
        return nodes[x][y];
    }

    final List<Node> findPath() {
        TreeSet<Node> openList = new TreeSet<>();
        closedList = new TreeSet<>();
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.pollFirst();
            if (current.equals(finish)) {
                return calcPath(start, current);
            }
            closedList.add(current);

            List<Node> surroundNodes = getSurroundNodes(current);
            for (Node surround : surroundNodes) {
                if (!openList.contains(surround)) { // node is not in openList
                    surround.setPrevious(current); // set current node as previous for this node
                    surround.sethCosts(finish); // set h costs of this node (estimated costs to goal)
                    surround.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(surround); // add node to openList
                } else if (surround.getgCosts() > surround.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                    surround.setPrevious(current); // set current node as previous for this node
                    surround.setgCosts(current); // set g costs of this node (costs from start to this node)
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Node> calcPath(Node start, Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        while (!goal.equals(start)) {
            path.addFirst(goal);
            goal = goal.getPrevious();
        }
        path.addFirst(start);
        return path;
    }

    private List<Node> getSurroundNodes(Node node) {
        List<Node> nodes = new LinkedList<>();

        Node temp = this.getNode((node.getX() + 1), (node.getY() - 2));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() + 2), (node.getY() - 1));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() + 1), (node.getY() + 2));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() + 2), (node.getY() + 1));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() - 1), (node.getY() - 2));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() - 2), (node.getY() - 1));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() - 1), (node.getY() + 2));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }

        temp = this.getNode((node.getX() - 2), (node.getY() + 1));
        if (temp != null && !closedList.contains(temp)) {
            nodes.add(temp);
        }
        return nodes;
    }

}
