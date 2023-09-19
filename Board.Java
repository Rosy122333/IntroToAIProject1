import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    boolean isMyTurn;
    boolean[][] eVert;// Vertical Edges
    boolean[][] eHori;// Horizontal Edges
    short[][] boxes;

    public Board(int sizeX, int sizeY) {
        eVert = new boolean[sizeX - 1][sizeY];
        eHori = new boolean[sizeY - 1][sizeX];
        boxes = new short[sizeY - 1][sizeX - 1]; // short representing the amount of edges filled in at a box
        fill(eVert, false);
        fill(eHori, false);
        fill(boxes, (short) 0);
        isMyTurn = true;
    }

    public Board clone() {
        Board toReturn = new Board(Game.width, Game.height);
        toReturn.boxes = this.boxes.clone();
        toReturn.eVert = this.eVert.clone();
        toReturn.eHori = this.eHori.clone();
        toReturn.isMyTurn = isMyTurn;
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

}