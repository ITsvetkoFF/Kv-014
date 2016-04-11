package SerhiiAlekseichenkoHorse;

import java.util.List;

public class SerhiiAlekseichenkoHorse {
    public static int[][] compute(int[][] board) {
        Map map = new Map(board);
        List<Node> path = map.findPath();
        int[][] result = new int[path.size()][2];
        for (Node node : path) {
            result[path.indexOf(node)][0] = node.getX();
            result[path.indexOf(node)][1] = node.getY();
        }
        return result;
    }
}
