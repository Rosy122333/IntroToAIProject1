import java.util.Scanner;

public class HumanSolver extends Solver{


    public Edge getBestMove(Game in){
        Scanner input = new Scanner(System.in);
        System.out.println("Input your N1 Coordinates one at a time");
        Node n1 = new Node(input.nextInt(),input.nextInt());
        System.out.println("Input your N2 Coordinates one at a time");
        Node n2 = new Node(input.nextInt(),input.nextInt());
        Edge output = new Edge(n1,n2);
        System.out.println("Edge Created : " + output);
        return output;
    }
}
