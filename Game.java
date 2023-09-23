import java.io.*;
// import java.util.ArrayList;
import java.util.Iterator;

// import static java.lang.Thread.sleep;

public class Game {
    String groupname;
    static int width;
    static int height;
    Board currentBoard;
    Solver s;
    int scoreMax;
    int scoreMin;

    public Game(int width, int height, String groupname) {
        Game.width = width;
        Game.height = height;
        currentBoard = new Board(width, height);
        this.groupname = groupname;
    }

    public void setSolver(Solver in) {
        s = in;
    }


    public void readMove() {
        String move = "";
        File moveFile = new File("move_file");
        try (BufferedReader br = new BufferedReader(new FileReader(moveFile));) {
            move = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

        if (move == null)
            return;
        String[] moveData = move.split("([ ,])+");
        // GroupName would be moveData[0]
        if (moveData[0] == groupname)
            return;
        int n1X = Integer.parseInt(moveData[1]);
        int n1Y = Integer.parseInt(moveData[2]);
        int n2X = Integer.parseInt(moveData[3]);
        int n2Y = Integer.parseInt(moveData[4]);
        if (n1X == 0 && n1Y == 0 & n2X == 0 && n2Y == 0)
            return; // In case of pass move -> do nothing
        Edge madeMove = Board.makeEdge(n1X, n1Y, n2X, n2Y);
        System.out.println("Reading: " + madeMove);
        currentBoard.addMove(madeMove);

    }

    public void writeToFile(Edge moveToTake) {
        String toWrite = groupname + moveToTake;
        writeStringToFile(toWrite);
    }

    public void writeStringToFile(String toWrite) {
        File moveFile = new File("move_file");
        try (FileWriter moveWriter = new FileWriter(moveFile, false);) {
            moveWriter.write(toWrite);
            moveWriter.close();
            System.out.println("Writing: " + toWrite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(currentBoard.allPossibleMoves.toString());
    }

    public void takeMove(Edge moveToTake) {

        currentBoard.addMove(moveToTake);
        writeToFile(moveToTake);

    }

    public void handleGo() {
        readMove();
        takeMove(s.getBestMove(this)); //
        sleepForRef();
    }

    public void handlePass() {
        readMove();
        writeStringToFile(groupname + " 0,0 0,0");
        sleepForRef();
    }

    protected void sleepForRef() {
        try {
            Thread.sleep(105);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
