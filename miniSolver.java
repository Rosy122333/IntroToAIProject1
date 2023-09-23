
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class miniSolver extends Solver {

    /*
     * getBestMove(Game in)
     * Given an inputted Game object
     * This function will create the nodes of a minimax tree for (n) seconds
     * Until at which point it will compute the heuristics of the leaf nodes
     * And compute the minima and maxima for each non-leaf node
     * until it collapses into the root node
     * following which the best move the root node has seen will be returned
     */
    @Override
    public Edge getBestMove(Game in) {
        in.currentBoard.relativeScore = 0;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>(); // list of nodes that should be expanded in order ->
                                                                 // Used for expanding the tree until time runs out
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // List of all nodes that have been created -> Used for
                                                                 // evaluating the tree created in the previous step
        ArrayList<Edge> moves = in.currentBoard.allPossibleMoves;
        TreeNode root = new TreeNode(in.currentBoard, null, null, false);
        TreeNode levelNode = null;
        Board currBoard;
        int currScore;

        long oldTime = System.currentTimeMillis();
        long timeOut = 1000 * 10;

        queue.add(root); // Add the root node to begin filling out the children
        queue.add(levelNode); // This is helpful for debug purposes as it allows for the programmer to see
                              // when layers have been reached (Seperated by moves taken/ nodes expanded to
                              // this point)
        do {
            if ((System.currentTimeMillis() - oldTime) > timeOut) // This breaks us out of the loop if the time limit is
                                                                  // reached -> goes to evaulating the tree we have so
                                                                  // far
                break;

            // For each node on the front of the queue
            TreeNode currentNode = queue.remove();

            // Given it is not the levelNode
            if (currentNode != levelNode) {
                // Add it to the stack
                stack.add(currentNode);

                // Take all the available moves from it
                currBoard = currentNode.getBoard();
                currScore = currBoard.relativeScore;
                moves = currBoard.allPossibleMoves;
                Collections.shuffle(moves);

                // And for every move create a child board
                for (Edge e : moves) {
                    Board child = currBoard.clone();
                    child.addMove(e);
                    int newScore = heuristicFunction(child);

                    // If the score has gone up inbetween these nodes then change then dont change
                    // the player turn
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
            // For every node still in queue that hasnt been evaluated
            TreeNode currNode = queue.remove();
            if (currNode != levelNode) // If it isnt the level node - then just add it to the stack (No longer
                                       // expanding these nodes)
                stack.add(currNode);
        }

        do { // For every node on the stack
            TreeNode currNode = stack.removeLast();
            TreeNode parentNode = currNode.getParent();

            // if the TreeNode is the default value then set it to the
            // currentUtility/HeuristicEvaluation
            // Only the leaves will have this done to them as they will later in the program
            // move their values to their parents
            // Meaning their parents will never have TreeNode.MIN as their value
            if (TreeNode.MIN == currNode.getUtility())
                currNode.setUtility(heuristicFunction(currNode.getBoard()));

            int currUtility = currNode.getUtility();
            // If the parentNode has it as our turn then it is a MAXIMIZING node which means
            // we will check if the nodes given is greater than the one held in the parent
            // node
            if (parentNode.getPlayer()) {
                if (currUtility > parentNode.getUtility()) {
                    parentNode.setUtility(currUtility);
                    if (parentNode == root)
                        root.setEdge(currNode.getEdge());
                    // System.out.println("Set rootEdge to " + currNode.getEdge());
                    // System.out.println("The Utility of rootNode should now be" + currUtility);
                }
            } else { // Else it is a MINIMIZING NODE which means we will check if the nodes given is
                     // less than the one held in the parent node
                if (currUtility < parentNode.getUtility() || parentNode.getUtility() == TreeNode.MIN)
                    parentNode.setUtility(currUtility);
            }
        } while (stack.size() != 1);
        System.out.println("Utility of Root: " + root.getUtility());
        return root.getEdge();
    }
}
