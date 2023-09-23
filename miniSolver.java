
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class miniSolver extends Solver {

    //        private int minimax(Game currentGame, int depth, boolean max, List<Integer> nextMove) {
//            if(depth<=0) {
//                return currentGame.scoreMax-currentGame.scoreMin;
//            }
//            int self = max?1:2;
//            int value = max?-50:50;
//            //possible moves
//
//
//
//            for(int r = 0; r < board.length; r++) {
//                for(int c = 0; c < board.length; c++) {
//                    if(r%2!=c%2 && board[r][c]==0) {
//                        board[r][c] = self;
//                        List<Integer> filled = fillWith(board,r,c,self);
//
//                        int v = minimax(board,depth-1,filled.isEmpty()?!max:max, null);
//
//                        if((max && v>value) || (!max && v<value)) {
//                            value = v;
//                            if(nextMove!=null) {
//                                nextMove.clear();
//                                nextMove.add(r);
//                                nextMove.add(c);
//                            }
//                        }
//                        unfill(board, filled);
//                        board[r][c] = 0;
//                    }
//                }
//            }
//            return value;
//        }
//
//        private void unfill(int[][] board, List<Integer> filled) {
//
//        }
//
//
//        private List<Integer> fillWith(int[][] board, int r, int c, int self) {
//        }
//    public int minimax(Board board, int depth, boolean max, Game game, ArrayList<Edge> nextMove) {
//
//        //check if board complete or at last depth
//        if (depth == 0 || boardComplete(board) == true) {
//            score = playerScore - enemyScore;
//            return score;
//        }
//
//
//        ArrayList<Edge> moves = getAvaliableMoves(board);
//        Collections.shuffle(moves); //shuffles all the moves I guess
//
//        for (Edge i : moves) {
//            if (game.isOnBoard(i) == true && game.isAlrOnBoard(i) == false) {
//
//                //we have chosen a move add it to the board
//
//                int v;
//                //see if it completes a box
//                if (completeBox(key, board, hor)) {
//
//                    if (max == true) {
//                        playerScore++;
//                        //max takes another turn
//                        v = minimax(board, depth - 1, max, game, nextMove);
//                    } else {
//
//                        enemyScore++;
//                        //min takes another turn
//                        v = minimax(board, depth - 1, max, game, nextMove);
//                    }
//                } else {
//                    v = minimax(board, depth - 1, !max, game, nextMove);
//                }

                //if ((v>value && max )|| (!max && v < value)) {
                //    bestScore = v;
                //    ChosenList = nextMove;
                //}
                //call to minimax ??
                //see if largest scores for player or enemy
                //update value
                //cahnge what next move equals
                //unfill the board
                //return value

                //meaning you are maxing now
                //if (max == true)  {}
                //else if  (max == false) {//you are minimizing this }
//        }
//        return 2;
//    }

    private void gameTree(Board board) {
        for(int i = 0; i < board.allPossibleMoves.size(); i++) {
            Board newBoard = board.clone();
            newBoard.addMove(board.allPossibleMoves.get(i));
        }
    }

    @Override //unsure if we need the override
    public Edge getBestMove(Game in) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Edge> moves = in.currentBoard.allPossibleMoves;
        TreeNode root = new TreeNode(in.currentBoard, null, in.currentBoard.isMyTurn, null);
        TreeNode levelNode = null;
        boolean currPlayer;
        Board currBoard;
        int currScore;

        long oldTime = System.nanoTime(); //added in 
        long timeOut = 999999999; //added in

        queue.add(root);
        queue.add(levelNode);
        boolean referencePlayer = in.currentBoard.isMyTurn;
        do {
            if((System.nanoTime() - oldTime) > timeOut) break; //added in
            TreeNode currentNode = queue.remove();
            if (currentNode != levelNode) {
                stack.add(currentNode);
                currBoard = currentNode.getBoard();
                currPlayer = currBoard.isMyTurn; //true if my turn, false if enemy turn :)
                currScore = currBoard.relativeScore;
                moves = currBoard.allPossibleMoves;
                Collections.shuffle(moves);

                for (Edge e : moves) {
                    Board child = currBoard.clone();
                    child.addMove(e);
                    int newScore = child.relativeScore;
                    if (newScore == currScore) {
                        child.isMyTurn = !currPlayer;
                        queue.add(new TreeNode(child, currentNode, !currPlayer, e));
                    } else{
                    queue.add(new TreeNode(child, currentNode, currPlayer, e));
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
                int currUtility = currNode.getUtility();

                if (TreeNode.MIN == currUtility)
                    currNode.setUtility(heuristicFunction(currNode.getBoard()));
                currUtility = currNode.getUtility();
                if (parentNode.getPlayer() == referencePlayer) {
                    if (currUtility > parentNode.getUtility()) {
                        parentNode.setUtility(currUtility);
                        if (parentNode == root)
                            root.setEdge(currNode.getEdge());
                        }
                    } else {
                        if (currUtility < parentNode.getUtility())
                            parentNode.setUtility(currUtility);
                    }
            } while (stack.size() != 1);
            return root.getEdge();
        }
}
