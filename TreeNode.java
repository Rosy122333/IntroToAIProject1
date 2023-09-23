import java.util.ArrayList;

public class TreeNode {
    private Board board;
    private TreeNode parent;
    private Edge move;
    private int utility;
    static int MIN = -10000000;

    public TreeNode(Board board, TreeNode parent, Edge move, boolean changeTurn) {
        this.board = board;
        this.parent = parent;
        this.move = move;
        this.utility = MIN;
        board.isMyTurn = changeTurn ? !board.isMyTurn : board.isMyTurn;
    }

    public Board getBoard() {
        return board;
    }

    public TreeNode getParent() {
        return parent;
    }

    public boolean getPlayer() {
        return board.isMyTurn;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public Edge getEdge() {
        return this.move;
    }

    public void setEdge(Edge edge) {
        this.move = edge;
    }
}
