import java.io.*;
// import java.util.ArrayList;

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


    // public boolean isAlrOnBoard(Edge e) {
    // for (Edge edge : edges) {
    // if (edge.equals(e)) {
    // return true;
    // }
    // }
    // return false;
    // }

    // public boolean isLegal(Edge e) {
    // if (isOnBoard(e) && !isAlrOnBoard(e))
    // return edges.add(e);
    // return false;
    // }
    // currently unused
    // public boolean isLegal(Edge e) {
    // if (isOnBoard(e) && !isAlrOnBoard(e))

    // want to add this correctly ***STOPPED HERE STILL NEED TO FIX THE MAIN AS WELL
    // return edges.add(e);
    // return false;
    // }

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
        int n1X = Integer.parseInt(moveData[1]);
        int n1Y = Integer.parseInt(moveData[2]);
        int n2X = Integer.parseInt(moveData[3]);
        int n2Y = Integer.parseInt(moveData[4]);
        if (n1X == 0 && n1Y == 0 & n2X == 0 && n2Y == 0) return; // In case of pass move -> do nothing
        // edges.add(new Edge(new Node(n1X, n1Y), new Node(n2X, n2Y)));
        
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeMove(Edge moveToTake) {
        // edges.add(moveToTake);
        writeToFile(moveToTake);

    }

    public void handleGo() {
        readMove();
        takeMove(s.getBestMove(this));
        sleepForRef();
    }

    public void handlePass() {
        // readMove();
        writeStringToFile(groupname + "0,0 0,0");
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
