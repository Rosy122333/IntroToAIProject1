import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    boolean isMyTurn;
    boolean[][] eVert;// Vertical Edges
    boolean[][] eHori;// Horizontal Edges
    short[][] boxes;
    int relativeScore;
    ArrayList<Edge> allPossibleMoves;

    public Board(int sizeX, int sizeY) {
        eHori = new boolean[sizeX - 1][sizeY];
        eVert = new boolean[sizeX][sizeY - 1];
        boxes = new short[sizeY - 1][sizeX - 1]; // short representing the amount of edges filled in at a box
        fill(eVert, false);
        fill(eHori, false);
        fill(boxes, (short) 0);
        isMyTurn = true;
        relativeScore = 0;
        allPossibleMoves = availableMoves(this);
    }

    public Board clone() {
        Board toReturn = new Board(Game.width, Game.height);
        toReturn.boxes = this.boxes.clone();
        toReturn.eVert = this.eVert.clone();
        toReturn.eHori = this.eHori.clone();
        toReturn.isMyTurn = isMyTurn;
        toReturn.relativeScore = relativeScore;
        return toReturn;
    }

    private void fill(short[][] array, short val) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = val;
    }

    private void fill(boolean[][] array, boolean val) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = val;
    }

    public void addMove(Edge move) {
        int x = move.x;
        int y = move.y;
        allPossibleMoves.remove(move);
        if (move.isHorizontal) {
            eHori[x][y] = true;
            if ((y != (Game.height - 1)) && ++boxes[x][y] == 4) {
                incrementRelativeScore();
            }
            if (y - 1 >= 0 && ++boxes[x][y - 1] == 4) {
                incrementRelativeScore();
            }
        } else {
            eVert[x][y] = true;
            if ((x != (Game.width - 1)) && ++boxes[x][y] == 4) {
                incrementRelativeScore();
            }
            if ((x - 1 >= 0) && ++boxes[x - 1][y] == 4) {
                incrementRelativeScore();
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

    public void incrementRelativeScore() {
        relativeScore += isMyTurn ? 1 : -1;
    }

    public ArrayList<Edge> availableMovesHelper(boolean[][] board, boolean isHorizontal) {
        ArrayList<Edge> moves = new ArrayList<Edge>();
        for (int i = 0; i < board.length; i++) { // rows or eVert
            for (int j = 0; j < board[i].length; j++) { // number of colmns at row i
                if (board[i][j] == false) {
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
    public int chainControl() {
        int rows = eHori.length;
        int cols = eVert.length;
        int count = 0;
            return count;
    }


    public int calculateEdgeParity(Game currentGame) {
        return (currentGame.scoreMax - currentGame.scoreMin) % 2 == 0 ? 1 : -1;
    }

    public int calculateMobility(Game currentGame) {
        return allPossibleMoves.size();
    }

    
 }



