import java.util.Scanner;

public class HumanSolver extends Solver {

    public Edge getBestMove(Game in) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input your N1 Coordinates one at a time");
        int x1 = input.nextInt();
        int y1 = input.nextInt();
        System.out.println("Input your N2 Coordinates one at a time");
        int x2 = input.nextInt();
        int y2 = input.nextInt();
        Edge output = Board.makeEdge(x1,y1,x2,y2);
        System.out.println("Edge Created : " + output);
        input.close();
        return output;
    }
}
