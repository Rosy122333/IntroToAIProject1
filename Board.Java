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
        eVert = new boolean[sizeX - 1][sizeY];
        eHori = new boolean[sizeY - 1][sizeX];
        boxes = new short[sizeY - 1][sizeX - 1]; // short representing the amount of edges filled in at a box
        fill(eVert, false);
        fill(eHori, false);
        fill(boxes, (short) 0);
        isMyTurn = true;
        relativeScore =0;
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

    public void addMove(Edge in){
        int x = in.x;
        int y = in.y;
            if(in.isHorizontal){
                eHori[x][y] = true;
                boxes[x][y]+=1;
                if(y-1 >=0){
                    boxes[x][y-1]+=1;
                }
            }
            else{
                eVert[x][y] = true;
                if(++boxes[x][y] == 4)
                    relativeScore += ();


                if(x-1 >=0){
                    boxes[x-1][y]+=1;
                }
            }

    }

    public void addMove(Edge move) {
        if (move.isHorizontal) {
            eHori[move.x][move.y] = true;
            boxes[move.x][move.y] += 1;
            if (move.y - 1 >= 0) {
                boxes[move.x][move.y - 1] += 1;
            }
        } else {
            eVert[move.x][move.y] = true;
            if (++boxes[move.x][move.y] == 4) {
                relativeScore++;
            }
    }


    public ArrayList<Edge> availableMovesHelper(boolean[][] board) {
        ArrayList<Edge> moves = new ArrayList<Edge>();
        for (int i = 0; i < board.length; i++) { //rows or eVert
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == false) {
                    moves.add(new Edge(true,i,j));
                }
            }
        }
        for (int i = 0; i < board[0].length; i++) { //rows or eVert
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == false) {
                    moves.add(new Edge(false,i,j));
                }
            }
        }
        return moves;
    }

    public ArrayList<Edge> availableMoves(Board board) {
        ArrayList<Edge> moves = new ArrayList<Edge>();
        ArrayList<Edge> movesHori = availableMovesHelper(board.eHori);
        ArrayList<Edge> movesVert = availableMovesHelper(board.eVert);
        moves.addAll(movesHori);
        moves.addAll(movesVert);
        return moves;
    }

}