import java.util.ArrayList;

public class Game {
    int width;
    int height;
    ArrayList<Edge> edges;

    public Game(int width, int height, ArrayList<Edge> edges) {
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

    public boolean isAlrOnBoard(Edge e) {
        for (Edge edge : edges) {
            if (edge.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLegal(Edge e) {
        if (isOnBoard(e) && !isAlrOnBoard(e))
            return edges.add(e);
        return false;
    }

}
