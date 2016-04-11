package IlliaDoroshenkoHorse;

import java.util.*;

public class Main {
    private static final int DISTANCE = 1;
    public static final int START = -2;
    public static final int FINISH = -3;

    private int[][] field = {
            {0, 0, -1, 0, 0, 0},
            {0, -2, -1, 0, 0, 0},
            {0, 0, -1, 0, -1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, -3, 0},
    };

    private int[][] field2 = new int[1000][1000];

    private Queue<Node> openList;
    private Set<Node> openSet;
    private Set<Node> closedSet;

    public Main() {

        openList = new PriorityQueue<>(new Node.NodeComparator());
        closedSet = new HashSet<>();
        openSet = new HashSet<>();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int[][] arr = XLSParser.parse(args[0]);
        long time2 = System.currentTimeMillis();
        Main m = new Main();
        /*m.field2[0][0] = -2;
        m.field2[999][999] = -3;

        System.out.println("Overall prog: " + (System.currentTimeMillis() - time1));
        System.out.println("Pathfind alg: " + (System.currentTimeMillis() - time2));
        System.out.println(path.size());
        System.out.println(m.listToArray(path));*/
        List<Node> path = m.findPath(arr);
        XLSParser.writeToFile(args[0], path, System.currentTimeMillis() - time1, System.currentTimeMillis() - time2);
    }
    public int[][] getPath(int[][] field) {
        return listToArray(findPath(field));
    }

    private int[][] listToArray(List<Node> lst) {
        int[][] res = new int[lst.size()][2];
        int i = 0;
        for (Node n : lst) {
            res[i][0] = n.getI();
            res[i][1] = n.getJ();
        }
        return res;
    }

    private Node getStart(int[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == START) return new Node(i, j);
            }
        }
        return null;
    }

    private Node getFinish(int[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == FINISH) return new Node(i, j);
            }
        }
        return null;
    }

    private List<Node> findPath(int[][] field) {
        Node start = getStart(field);
        start.setG(0);
        Node finish = getFinish(field);
        //Node finish = new Node(119, 108);
        openList.add(start);
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedSet.remove(current);
            if (current.equals(finish)) return buildPath(current); //todo

            int i = current.getI();
            int j = current.getJ();

            processNewNode(i - 1, j + 2, field, current, finish);
            processNewNode(i + 1, j + 2, field, current, finish);

            processNewNode(i + 2, j - 1, field, current, finish);
            processNewNode(i + 2, j + 1, field, current, finish);

            processNewNode(i - 1, j - 2, field, current, finish);
            processNewNode(i + 1, j - 2, field, current, finish);

            processNewNode(i - 2, j - 1, field, current, finish);
            processNewNode(i - 2, j + 1, field, current, finish);

            closedSet.add(current);
        }

        return null;
    }

    private List<Node> buildPath(Node current) {
        List<Node> lst = new ArrayList<>();
        while (current != null) {
            lst.add(current);
            current = current.getParent();
        }
        Collections.reverse(lst);
        return lst;
    }

    private void processNewNode(int i, int j, int[][] field, Node current, Node finish) {
        if (i >= 0 && j >= 0 && i < field.length && j < field[i].length) {
            if (((field[i][j] == 0) || (field[i][j] == -3)) && !closedSet.contains(new Node(i, j))) {

                Node nodeInList = getNodeByIndexes(i, j);
                if (nodeInList != null) {
                    if (nodeInList.getG() + DISTANCE < current.getG()) {
                        current.setParent(nodeInList);
                        current.setG(nodeInList.getG() + DISTANCE);
                        current.calcF(finish);
                    }
                } else {
                    Node newNode = new Node(i, j);
                    newNode.setParent(current);
                    newNode.setG(current.getG() + DISTANCE);
                    newNode.calcF(finish);
                    openList.add(newNode);
                    openSet.add(newNode);

                }
            }
        }

    }

    private Node getNodeByIndexes(int i, int j) {
        if (openSet.contains(new Node(i, j))) {
            for (Node n : openList) {
                if (n.getI() == i && n.getJ() == j)
                    return n;
            }
        }
        return null;
    }
}



