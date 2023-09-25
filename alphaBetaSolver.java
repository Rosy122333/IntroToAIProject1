import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class alphaBetaSolver extends Solver {
    final static int MIN = -1000000000, MAX = 1000000000;
    long timeStarted;
    long boundedTime = 9 * 1070;
    short depthLevelMax = 4;

    @Override
    public Edge getBestMove(Game in) {
        timeStarted = System.currentTimeMillis();
        Bound out = this.depthFirstSearch(in.currentBoard, MIN, MAX, depthLevelMax);
        System.out.println("Utility:" + out.utility);
        return out.edge;
    }

    public Bound depthFirstSearch(Board in, int alpha, int beta, int depthLeft) {
        System.out.println("[" + alpha + "," + beta + "]");
        int movesLeft = in.allPossibleMoves.size();
        System.out.println("Depth:" + depthLeft);
        boolean outOfTime = (System.currentTimeMillis() - timeStarted >= boundedTime);
        if (depthLeft < 0 || outOfTime || (movesLeft == 0)) {
            System.out.println("DFS hit bounds" + heuristicFunction(in));
            return new Bound(null, heuristicFunction(in));
        }

        Collections.shuffle(in.allPossibleMoves);

        Bound boundOut = new Bound(null, in.isMyTurn ? MIN : MAX);

        for (int i = 0; i < movesLeft; i++) {
            Board workingBoard = in.clone();
            workingBoard.addMove(in.allPossibleMoves.get(i));

            boolean flag = false;
            if (workingBoard.relativeScore == in.relativeScore) {
                workingBoard.isMyTurn = !workingBoard.isMyTurn;
                flag = true;
            }
            Bound workingBound = depthFirstSearch(workingBoard, alpha, beta, (depthLeft - 1));

            int workingUtility = workingBound.utility;

            if (in.isMyTurn ? boundOut.utility < workingUtility : boundOut.utility > workingUtility) {
                boundOut.utility = workingUtility;
                boundOut.edge = in.allPossibleMoves.get(i);
            }

            System.out.println("Above is a " + (in.isMyTurn ? "Maximizing" : "Minimizing")
                    + "Node| Child Utility is " + workingUtility);
            System.out.println("[" + alpha + "," + beta + "]");

            if (flag) {

                if (in.isMyTurn ? workingUtility > beta : workingUtility < alpha)
                    return boundOut;
            }
            if (in.isMyTurn) {
                System.out.println(
                        "Changing Alpha : Initial Alpha:" + alpha + " | Boundout.utility: " + boundOut.utility);
                alpha = Math.max(alpha, boundOut.utility);
            } else {
                beta = Math.min(beta, boundOut.utility);
                System.out.println("Changing Beta: Initial Beta:" + alpha + " | Boundout.utility: " + boundOut.utility);
            }

        }

        return new Bound(null, heuristicFunction(in));
    }

}
