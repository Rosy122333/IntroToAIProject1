
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class miniSolver extends Solver {

    // private int minimax(Game currentGame, int depth, boolean max, List<Integer>
    // nextMove) {
    // if(depth<=0) {
    // return currentGame.scoreMax-currentGame.scoreMin;
    // }
    // int self = max?1:2;
    // int value = max?-50:50;
    // //possible moves
    //
    //
    //
    // for(int r = 0; r < board.length; r++) {
    // for(int c = 0; c < board.length; c++) {
    // if(r%2!=c%2 && board[r][c]==0) {
    // board[r][c] = self;
    // List<Integer> filled = fillWith(board,r,c,self);
    //
    // int v = minimax(board,depth-1,filled.isEmpty()?!max:max, null);
    //
    // if((max && v>value) || (!max && v<value)) {
    // value = v;
    // if(nextMove!=null) {
    // nextMove.clear();
    // nextMove.add(r);
    // nextMove.add(c);
    // }
    // }
    // unfill(board, filled);
    // board[r][c] = 0;
    // }
    // }
    // }
    // return value;
    // }
    //
    // private void unfill(int[][] board, List<Integer> filled) {
    //
    // }
    //
    //
    // private List<Integer> fillWith(int[][] board, int r, int c, int self) {
    // }
    // public int minimax(Board board, int depth, boolean max, Game game,
    // ArrayList<Edge> nextMove) {
    //
    // //check if board complete or at last depth
    // if (depth == 0 || boardComplete(board) == true) {
    // score = playerScore - enemyScore;
    // return score;
    // }
    //
    //
    // ArrayList<Edge> moves = getAvaliableMoves(board);
    // Collections.shuffle(moves); //shuffles all the moves I guess
    //
    // for (Edge i : moves) {
    // if (game.isOnBoard(i) == true && game.isAlrOnBoard(i) == false) {
    //
    // //we have chosen a move add it to the board
    //
    // int v;
    // //see if it completes a box
    // if (completeBox(key, board, hor)) {
    //
    // if (max == true) {
    // playerScore++;
    // //max takes another turn
    // v = minimax(board, depth - 1, max, game, nextMove);
    // } else {
    //
    // enemyScore++;
    // //min takes another turn
    // v = minimax(board, depth - 1, max, game, nextMove);
    // }
    // } else {
    // v = minimax(board, depth - 1, !max, game, nextMove);
    // }

    // if ((v>value && max )|| (!max && v < value)) {
    // bestScore = v;
    // ChosenList = nextMove;
    // }
    // call to minimax ??
    // see if largest scores for player or enemy
    // update value
    // cahnge what next move equals
    // unfill the board
    // return value

    // meaning you are maxing now
    // if (max == true) {}
    // else if (max == false) {//you are minimizing this }
    // }
    // return 2;
    // }

    private void gameTree(Board board) {
        for (int i = 0; i < board.allPossibleMoves.size(); i++) {
            Board newBoard = board.clone();
            newBoard.addMove(board.allPossibleMoves.get(i));
        }
    }

    @Override // unsure if we need the override
    public Edge getBestMove(Game in) {
        in.currentBoard.relativeScore = 0;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Edge> moves = in.currentBoard.allPossibleMoves;
        TreeNode root = new TreeNode(in.currentBoard, null, null, false);
        TreeNode levelNode = null;
        // boolean currPlayer;
        Board currBoard;
        int currScore;

        long oldTime = System.currentTimeMillis(); // added in
        long timeOut = 1000 * 10; // added in

        queue.add(root);
        queue.add(levelNode);
        // boolean referencePlayer = in.currentBoard.isMyTurn;
        do {
            if ((System.currentTimeMillis() - oldTime) > timeOut)
                break; // added in
            TreeNode currentNode = queue.remove();
            if (currentNode != levelNode) {
                stack.add(currentNode);
                currBoard = currentNode.getBoard();
                // currPlayer = currBoard.isMyTurn; //true if my turn, false if enemy turn :)
                currScore = currBoard.relativeScore;
                moves = currBoard.allPossibleMoves;
                Collections.shuffle(moves);

                for (Edge e : moves) {
                    Board child = currBoard.clone();
                    child.addMove(e);
                    int newScore = heuristicFunction(child);
                    if (newScore == currScore) {
                        queue.add(new TreeNode(child, currentNode, e, true));
                    } else {
                        queue.add(new TreeNode(child, currentNode, e, false));
                    }
                }
            } else {
                queue.add(levelNode);
            }
        } while (queue.size() != 0);
        while (queue.size() != 0) {
            TreeNode currNode = queue.remove();
            if (currNode != levelNode)
                stack.add(currNode);
        }
        do {
            TreeNode currNode = stack.removeLast();
            TreeNode parentNode = currNode.getParent();

            if (TreeNode.MIN == currNode.getUtility())
                currNode.setUtility(heuristicFunction(currNode.getBoard()));

            int currUtility = currNode.getUtility();

            if (parentNode.getPlayer()) {
                if (currUtility > parentNode.getUtility()) {
                    parentNode.setUtility(currUtility);
                    if (parentNode == root)
                        root.setEdge(currNode.getEdge());
                    // System.out.println("Set rootEdge to " + currNode.getEdge());
                    // System.out.println("The Utility of rootNode should now be" + currUtility);
                }
            } else {
                if (currUtility < parentNode.getUtility() || parentNode.getUtility() == TreeNode.MIN)
                    parentNode.setUtility(currUtility);
            }
        } while (stack.size() != 1);
        System.out.println("Utility of Root: " + root.getUtility());
        // in.takeMove(root.getEdge());
        return root.getEdge();
    }
}
