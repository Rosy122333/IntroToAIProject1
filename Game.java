public class Game {
    int width;
    int height;

    Edge[] edges;

    public Game(int width, int height, Edge[] edges) {
        this.width = width;
        this.height = height;
        this.edges = edges;
    }

    public boolean isOnBoard(Node n) {
        return n.getX() >= 0 && n.getX() < width && n.getY() >= 0 && n.getY() < height;
    }

    public boolean isOnBoard(Edge e) {
        return isOnBoard(e.getN1()) && isOnBoard(e.getN2());
    }

    public boolean isOnBoard(int x, int y) {
        return isOnBoard(new Node(x, y));
    }

    public boolean isOnBoard(int x1, int y1, int x2, int y2) {
        return isOnBoard(new Edge(new Node(x1, y1), new Node(x2, y2)));
    }

    public boolean isAlrOnBoard(Edge e) {
        for (Edge edge : edges) {
            if (edge.equals(e)) {
                return true;
            }
        }
        return false;
    }
}
