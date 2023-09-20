import java.util.ArrayList;

public class TreeNode {
    private Board board;
    private TreeNode parent;
    private Edge move;
    private int utility;
    private boolean player;
    static int MIN = -10000000;

    public TreeNode(Board board, TreeNode parent, boolean player, Edge move) {
        this.board = board;
        this.parent = parent;
        this.player = player;
        this.move = move;
        this.utility = MIN;
         }

    public Board getBoard() {
        return board;
    }
    public TreeNode getParent() {
        return parent;
    }
    public boolean getPlayer() {
        return player;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility ;
    }

    public Edge getEdge() {
        return this.move ;
    }

    public void setEdge(Edge edge) {
        this.move = edge ;
    }
}
