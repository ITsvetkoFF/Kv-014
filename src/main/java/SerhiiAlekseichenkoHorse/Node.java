package SerhiiAlekseichenkoHorse;

class Node implements Comparable<Node> {

    private final static int BASIC_MOVEMENT_COST = 10;

    private int x;
    private int y;
    private Node previous;
    private int gCosts;
    private int hCosts;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int getfCosts() {
        return gCosts + hCosts;
    }

    private void setgCosts(Node previousNode, int basicCost) {
        setgCosts(previousNode.getgCosts() + basicCost);
    }

    void setgCosts(Node previousNode) {
        setgCosts(previousNode, BASIC_MOVEMENT_COST);
    }

    int calculategCosts(Node previousNode) {
        return (previousNode.getgCosts() + BASIC_MOVEMENT_COST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return x == node.x && y == node.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Node getPrevious() {
        return previous;
    }

    void setPrevious(Node previous) {
        this.previous = previous;
    }

    int getgCosts() {
        return gCosts;
    }

    private void setgCosts(int gCosts) {
        this.gCosts = gCosts;
    }

    private void sethCosts(int hCosts) {
        this.hCosts = hCosts;
    }

    void sethCosts(Node endNode) {
        this.sethCosts((this.getX() - endNode.getX())
                + (this.getY() - endNode.getY()));
    }

    @Override
    public int compareTo(Node o) {
        if (this.getfCosts() - o.getfCosts() != 0) {
            return this.getfCosts() - o.getfCosts();
        }
        if (this.getX() - o.getX() != 0) {
            return this.getX() - o.getX();
        } else return this.getY() - o.getY();
    }
}
