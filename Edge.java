public class Edge {
    Node n1;
    Node n2;

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public String toString() {
        return " " + n1 + " " + n2; // ToString in form " x1,y1 x2,y2"
    }

    // Given an Edge returns a boolean value of whether or not the inputted edge is
    // equal to this one
    // This is needed due to the fact that inverse order of inputted nodes are the
    // same nodes but saved differently in the Edge Objects
    // Could be unneeded given a way to normalize inputted nodes into a normalized
    // storage structure
    public boolean equals(Edge in) {
        // given theoretically the nodes are not the same as that would be an illegal
        // move except for a pass
        return (n1.equals(in.n2) || n1.equals(in.n1)) && (n2.equals(in.n2) || n2.equals(in.n1))
        // && !(n1.equals(n2)) // This is theoretically not needed due to this -> should
        // also be the first term of the boolean statement if so to increase performance
        ;

    }

}
