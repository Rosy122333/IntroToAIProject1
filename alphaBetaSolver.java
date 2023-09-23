import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class alphaBetaSolver extends Solver {

    @Override
    public Edge getBestMove(Game in) { 
        Edge returning = new Edge(new Node(1,1), new Node(1,2));
        return returning;

    }

   


}
