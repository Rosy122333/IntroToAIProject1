public class Bound implements Comparable<Bound>{
    int utility ;
    Edge edge;

    Bound(Edge edge, int utility) {
        this.edge = edge ;
        this.utility = utility ;
    }

    @Override
    public int compareTo(Bound in) {
        return this.utility - in.utility;
    }
}
