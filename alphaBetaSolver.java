import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class alphaBetaSolver extends Solver {
    final static int MIN = -1000000000, MAX = 1000000000;
    long timeStarted;
    long boundedTime = 9 * 1070;
    short depthLevelMax = 9;
    int failures = 0;

    @Override
    public Edge getBestMove(Game in) {
        timeStarted = System.currentTimeMillis();
        Bound out = this.depthFirstSearch(in.currentBoard, MIN, MAX, depthLevelMax);
        System.out.println("Utility:" + out.utility);
        System.out.println("Failures: " + failures);
        in.currentBoard.relativeScore = 0;
        return out.edge;
    }

    Bound depthFirstSearch(Board in, int alpha, int beta, int depthLeft) {
        boolean isMaximizingParent = in.isMyTurn;
        int movesLeft = in.allPossibleMoves.size();
        boolean outOfTime = (System.currentTimeMillis() - timeStarted >= boundedTime);
        failures = 0;



        if (depthLeft < 0 || (movesLeft == 0) || outOfTime) {
            if (outOfTime) ++failures;
            return new Bound(null, heuristicFunction(in));
        }             

        Collections.shuffle(in.allPossibleMoves);

        Bound boundOut = new Bound(null, in.isMyTurn ? MIN : MAX);
        boundOut.edge = in.allPossibleMoves.get(0);
        for (int i = 0; i < movesLeft; i++) {
            Board childBoard = in.clone(); // Given a child node
            childBoard.addMove(in.allPossibleMoves.get(i)); // With an advanced move from the board in

            if (childBoard.relativeScore == in.relativeScore) {// Given that the score has not increased in either
                                                               // direction the player changes
                childBoard.isMyTurn = !childBoard.isMyTurn;
            }
            
            if(depthLeft == 4) System.out.println(String.format("Parent Node| a: %d | b: %d",beta,alpha));

            System.out.println((childBoard.isMyTurn ? "MAXIMIZING" : "MINIMIZING") + ": [" + alpha + "," + beta + "]" + "| D:" + depthLeft);
            Bound childBound = depthFirstSearch(childBoard, alpha, beta, depthLeft - 1); // Perform further
                                                                                         // depthFirstSearch/ABP with
                                                                                         // remaining depth

            if (isMaximizingParent) { // Given this node is a maximizing node
                boundOut.utility = Math.max(boundOut.utility, childBound.utility);
                alpha = Math.max(alpha, boundOut.utility);
            } else { // Given this node is a minimizing node
                boundOut.utility = Math.min(boundOut.utility, childBound.utility);
                beta = Math.min(beta, boundOut.utility);
            }
            if (beta <= alpha) {
                boundOut.utility = childBound.utility;
                // System.out.println("Breaking Condition Met");
                break;// exit the loop when pruning conditions are met 
            }

        }

        return boundOut;
    }
}
