package VadymHolubHorse;

import java.util.*;

public class VadymHolubHorse {
    public static int[][] compute(int[][] board) {
        int[][] result = new Board().findPath(board);

        // using board
        // return [[0,0], [1,2], ... ]
        return result;
    }
}
class Board {

    private Figure figure = new Horse();
    private int xLen;
    private int[][] matrix;
    private int[] start;
    private int[] finish;

    public int[][] findPath(int[][] matrix) {
        this.matrix = matrix;
        for (int i = 0; i < matrix.length; i++) {
            int[] y = matrix[0];
            for (int ii = 0; ii < y.length; ii++) {
                int coord = matrix[i][ii];
                if (coord == -2 || coord == -3) {
                    if (coord == -2) {
                        start = new int[]{i, ii};
                        //todo opt
                    } else {
                        finish = new int[]{i, ii};
                    }
                }
            }
        }
        xLen = matrix[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        int[][] moves;

        Map<ArrWrap, Node> path = new HashMap<>();
        Node head = new Node();
        head.add(start, null);
        path.put(new ArrWrap(start), head);

        while (!queue.isEmpty()) {
            List<int[]> aMoves = new ArrayList<>();
            int[] moveForAnalysis = queue.poll();

            Node node = path.get(new ArrWrap(moveForAnalysis));

            if (Arrays.equals(finish, moveForAnalysis)) {
                List<int[]> resultList = new LinkedList<>();
                resultList.add(moveForAnalysis);
//                //todo MARK
//                mark(moveForAnalysis);

                while (node.hasNext()) {
//                    //todo MARK
//                    mark(node.getParent().elem);
                    resultList.add(node.getParent().elem);
                    node = node.getParent();
                }
                int[][] result = new int[resultList.size()][];
                Collections.reverse(resultList);
                int yy = 0;
                for (int[] coord : resultList) {
                    result[yy++] = coord;
                }
                return result;
            }
            //
            figure.setPosition(moveForAnalysis);
            moves = figure.moves();
            for (int[] move : moves) {
                if (isAccessible(move)) {
                    if (!path.containsKey(new ArrWrap(move))) {
                        aMoves.add(move);
                        Node newNode = new Node();
                        newNode.add(move, node);
                        path.put(new ArrWrap(move), newNode);
                    }
                }
            }
            if (aMoves.size() != 0) {
                queue.addAll(aMoves);
            }
        }
        return null;
    }

    class ArrWrap {
        int[] arr;

        public ArrWrap(int[] arr) {
            this.arr = arr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ArrWrap arrWrap = (ArrWrap) o;

            return Arrays.equals(arr, arrWrap.arr);

        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }
    }


    class Node {
        private int[] elem;
        private Node prev;

        void add(int[] elem, Node prev) {
            this.prev = prev;
            this.elem = elem;
        }

        Node getParent() {
            return prev;
        }

        boolean hasNext() {
            if (prev != null) {
                return true;
            }
            return false;
        }
    }

    private boolean isAccessible(int[] move) {
        for (int point : move) {
            if (point < 0) {
                return false;
            }
        }
        if (move[1] >= xLen) {
            return false;
        }
        if (move[0] >= matrix.length) {
            return false;
        }

        int point = matrix[move[0]][move[1]];
        if (point == 0 || point == -2 || point == -3) {
            return true;
        }
        return false;
    }

    public void mark(int[] point) {
        matrix[point[0]][point[1]] = 4;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : matrix) {
            for (int column : row) {
                String cell = String.valueOf(column);
                if (cell.length() == 1) {
                    cell = " " + cell;
                }
                stringBuilder.append(cell);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}


interface Figure {
    void setPosition(int[] position);
    int[][] moves();
}
class Horse implements Figure {

    private int x;
    private int y;

    @Override
    public void setPosition(int[] position) {
        y = position[0];
        x = position[1];
    }

    //    0 1 0 2 0
    //    3 0 0 0 4
    //    0 0 x 0 0
    //    5 0 0 0 6
    //    0 7 0 8 0
    @Override
    public int[][] moves() {
        return new int [][]{{y - 2, x - 1},
                {y - 2, x + 1},
                {y - 1, x - 2},
                {y - 1, x + 2},
                {y + 1, x - 2},
                {y + 1, x + 2},
                {y + 2, x - 1},
                {y + 2, x + 1}};
    }
}
