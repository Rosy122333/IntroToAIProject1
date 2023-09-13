import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Game {
    static Edge passEdge = new Edge(new Node(0,0),new Node(0,0));
    String groupname;
    int width;
    int height;
    ArrayList<Edge> edges;
    Solver s;
    public Game(int width, int height, ArrayList<Edge> edges, String groupname) {
        this.width = width;
        this.height = height;
        this.edges = edges;
        this.groupname = groupname;
    }

    public void setSolver(Solver in){
        s = in;
    }
    public boolean isOnBoard(Node n) {
        return n.getX() >= 0 && n.getX() < width && n.getY() >= 0 && n.getY() < height;
    }

    public boolean isOnBoard(Edge e) {
        return isOnBoard(e.getN1()) && isOnBoard(e.getN2());
    }

    public boolean isAlrOnBoard(Edge e) {
        for (Edge edge : edges) {
            if (edge.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLegal(Edge e) {
        if (isOnBoard(e) && !isAlrOnBoard(e))
            return edges.add(e);
        return false;
    }

    public void readMove() {
        String move  = "";
        File moveFile = new File("movefile");
        try(BufferedReader br = new BufferedReader(new FileReader(moveFile));){
            move = br.readLine();
        }
        catch(Exception e){e.printStackTrace();};


        String[] moveData = move.split(" ,");
        // GroupName would be moveData[0]
        int n1X = Integer.parseInt(moveData[1]);
        int n1Y = Integer.parseInt(moveData[2]);
        int n2X = Integer.parseInt(moveData[3]);
        int n2Y = Integer.parseInt(moveData[4]);
        edges.add(new Edge(new Node(n1X, n1Y), new Node(n2X, n2Y)));
    }

    public void writeToFile(Edge moveToTake) {
        String toWrite = groupname + moveToTake;
        // URL pathToMoveFile = main.class.getResource("movefile");
        File moveFile = new File("movefile");
        try (FileWriter moveWriter = new FileWriter(moveFile, false);) {
            moveWriter.write(toWrite);
            moveWriter.close();
        } catch (Exception e) {e.printStackTrace();}

    }

    public void takeMove(Edge moveToTake) {
        edges.add(moveToTake);
        writeToFile(moveToTake);

    }

    public void handleGo(){
        readMove();
        takeMove(s.getBestMove(this));
        sleepForRef();
    }


    public void handlePass(){

        readMove();
        writeToFile(passEdge);
        sleepForRef();
    }

    protected void sleepForRef(){
        try {
            Thread.sleep(105);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
