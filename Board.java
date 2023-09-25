import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    String[][] eVert;// Vertical Edges
    String[][] eHori;// Horizontal Edges
    short[][] boxes;
    int meScore;
    int youScore;
    ArrayList<Edge> allPossibleMoves;
    boolean isMyTurn;

    public Board(int sizeX, int sizeY) {
        eHori = new String[sizeX - 1][sizeY];
        eVert = new String[sizeX][sizeY - 1];
        boxes = new short[sizeY - 1][sizeX - 1]; // short representing the amount of edges filled in at a box
        fill(eVert, "");
        fill(eHori, "");
        fill(boxes, (short) 0);
        isMyTurn = true;
        meScore = 0;
        youScore = 0;
        allPossibleMoves = availableMoves(this);
    }

    public Board clone() {
        Board toReturn = new Board(Game.width, Game.height);
        toReturn.boxes = this.boxes.clone();
        toReturn.eVert = this.eVert.clone();
        toReturn.eHori = this.eHori.clone();
        toReturn.isMyTurn = isMyTurn;
        toReturn.meScore = meScore;
        toReturn.youScore = youScore;
        return toReturn;
    }

    private void fill(short[][] array, short val) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = val;
    }

    private void fill(String[][] array, String val) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = val;
    }

    public void addMove(Edge move) {
        int x = move.x;
        int y = move.y;
        allPossibleMoves.remove(move);
        if (move.isHorizontal) {
            if (isMyTurn)
                eHori[x][y] = "me";
            else
                eHori[x][y] = "you";
            if ((y != (Game.height - 1)) && ++boxes[x][y] == 4) {
                addScore();
            }
            if (y - 1 >= 0 && ++boxes[x][y - 1] == 4) {
                addScore();
            }
        } else {
            if (isMyTurn)
                eVert[x][y] = "me";
            else
                eVert[x][y] = "you";
            if ((x != (Game.width - 1)) && ++boxes[x][y] == 4) {
                addScore();
            }
            if ((x - 1 >= 0) && ++boxes[x - 1][y] == 4) {
                addScore();
            }
            ;
        }
    }

    public static Edge makeEdge(int x1, int y1, int x2, int y2) {
        Edge toReturn;
        boolean hori = (y1 == y2);
        if (hori) {
            toReturn = new Edge(hori, (x1 < x2 ? x1 : x2), y1);
        } else {
            toReturn = new Edge(hori, x1, (y1 < y2 ? y1 : y2));
        }
        return toReturn;
    }

    // public void incrementRelativeScore() {
    //     relativeScore += isMyTurn ? 1 : -1;
    // }

    public void addScore() {
        if (isMyTurn)
            meScore++;
        else
            youScore++;
    }

    public ArrayList<Edge> availableMovesHelper(String[][] board, boolean isHorizontal) {
        ArrayList<Edge> moves = new ArrayList<Edge>();
        for (int i = 0; i < board.length; i++) { // rows or eVert
            for (int j = 0; j < board[i].length; j++) { // number of colmns at row i
                if (board[i][j] == "") {
                    moves.add(new Edge(isHorizontal, i, j));
                }
            }
        }
        return moves;
    }

    public ArrayList<Edge> availableMoves(Board board) {
        ArrayList<Edge> moves = new ArrayList<Edge>();
        ArrayList<Edge> movesHori = availableMovesHelper(board.eHori, true);
        ArrayList<Edge> movesVert = availableMovesHelper(board.eVert, false);
        moves.addAll(movesHori);
        moves.addAll(movesVert);
        return moves;
    }

    public int countChains() {
        int count = 0;
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                if (boxes[i][j] == 3) {
                    count++;
                }
            }
        }
        return count;
    }

// counts number of immediate closings available to the  player
    public int findClosings() {
        int count = 0;
        int rows = eHori.length;
        int cols = eVert.length;
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (boxes[i][j] == 3) {
                    count++;
                }
            }
        }
        return count;
     }
// counts the number of chains that are available to the player
    public int countChainControl() {
        int count = 0;
        for (int i = 0; i < eHori.length; i++) {
            for (int j = 0; j < eHori[i].length; j++) {
                if (eHori[i][j] == "") {
                    if (isChainControlled(eHori, i, j)) {
                        count++;
                    }
                }
            }
        }
        for (int i = 0; i < eVert.length; i++) {
            for (int j = 0; j < eVert[i].length; j++) {
                if (eVert[i][j] == "") {
                    if (isChainControlled(eVert, i, j)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean isChainControlled(String[][] edges, int x, int y) {
        if (isMyTurn)
            edges[x][y] = "me";
        else
            edges[x][y] = "you";
        boolean isControlled = false;
        if (x > 0 && edges[x - 1][y] != "" && edges[x + 1][y] != "" && edges[x][y - 1] != "" && edges[x][y + 1] != "") {
            isControlled = true;
        }
        edges[x][y] = "";
        return isControlled;
    }

    public int countEdgeOwnership() {
        int meCount = 0;
        int youCount = 0;
        for (int i = 0; i < eHori.length; i++) {
            for (int j = 0; j < eHori[i].length; j++) {
                if (eHori[i][j] == "me") {
                    meCount++;
                } else if (eHori[i][j] == "you") {
                    youCount++;
                }
            }
        }
        for (int i = 0; i < eVert.length; i++) {
            for (int j = 0; j < eVert[i].length; j++) {
                if (eVert[i][j] == "me") {
                    meCount++;
                } else if (eVert[i][j] == "you") {
                    youCount++;
                }
            }
        }
        if (isMyTurn) {
            return meCount - youCount;
        } else {
            return youCount - meCount;
        }
    }

    // determining whether the current player is closer to creating an odd or even number of boxes
    public int calculateEdgeOddOrEven() {
        int meCount = 0;
        int youCount = 0;
        for (int i = 0; i < eHori.length; i++) {
            for (int j = 0; j < eHori[i].length; j++) {
                if (getBoxOwner(i, j, true) == "me") {
                    meCount++;
                } else if (getBoxOwner(i, j, true) != "") {
                    youCount++;
                }
            }
        }
        for (int i = 0; i < eVert.length; i++) {
            for (int j = 0; j < eVert[i].length; j++) {
                if (getBoxOwner(i, j, false) == "me") {
                    meCount++;
                } else if (getBoxOwner(i, j, false) != "") {
                    youCount++;
                }
            }
        }
        if (isMyTurn) {
            return (meCount - youCount) % 2 == 0 ? 1 : -1;
        } else {
            return (youCount - meCount) % 2 == 0 ? 1 : -1;
        }
    }

    private String getBoxOwner(int x, int y, boolean isHori) {
        String topLeft;
        String topRight;
        String bottomLeft;
        String bottomRight;
        if (isHori) {
            topLeft = eHori[x][y];
            topRight = eHori[x + 1][y];
            bottomLeft = eHori[x][y + 1];
            bottomRight = eHori[x + 1][y + 1];
        } else {
            topLeft = eVert[x][y];
            topRight = eVert[x + 1][y];
            bottomLeft = eVert[x][y + 1];
            bottomRight = eVert[x + 1][y + 1];
        }
        if (topLeft == topRight && topLeft == bottomLeft && topLeft == bottomRight) {
            return topLeft;
        } else {
            return "";
        }
    }

    // calculating number of moves available to the player
    public int calculateMobility() {
        int meCount = 0;
        int youCount = 0;
        String player;
        if (isMyTurn) {
            player = "me";
        } else {
            player = "you";
        }
        for (int i = 0; i < eHori.length; i++) {
            for (int j = 0; j < eHori[i].length; j++) {
                if (eHori[i][j] == "") {
                    eHori[i][j] = player;
                    if (getBoxOwner(i, j, true) != "") {
                        if (isMyTurn) 
                            meCount++;
                        else
                            youCount++;
                    }
                } else {
                    if (isMyTurn)
                        youCount++;
                    else
                        meCount++;
                }
                eHori[i][j] = "";
            }
        }
        for (int i = 0; i < eVert.length; i++) {
            for (int j = 0; j < eVert[i].length; j++) {
                if (eVert[i][j] == "") {
                    eVert[i][j] = player;
                    if (getBoxOwner(i, j, false) != "") {
                        if (isMyTurn)
                            meCount++;
                        else
                            youCount++;
                    }
                } else {
                    if (isMyTurn)
                        youCount++;
                    else
                        meCount++;
                }
                eVert[i][j] = "";
            }
        }
        if (isMyTurn) {
            return meCount - youCount;
        } else {
            return youCount - meCount;
        }
    }
    
 }



