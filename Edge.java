
/**
 *
 */
public class Edge {

    boolean isHorizontal;
    int x;
    int y;

    public Edge(boolean isHorizontal, int x, int y) {
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
    }

    public Edge() {
        x = 0;
        y = 0;
        isHorizontal = true;
    }

    public String toString() { // Given the Move -> return the printable value
        return " " + (x + "," + y) + " " + (isHorizontal ? ((x + 1) + "," + y) : (x + "," + (y + 1)));
    }

    @Override
    public boolean equals(Object edge) {
        Edge e = (Edge) edge;
        return (e.x == x && e.y == y && e.isHorizontal == isHorizontal);
    }
}
