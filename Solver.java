public abstract class Solver {

    protected int heuristicFunction(Game in){
        return 1;
    }

    public abstract Edge getBestMove(Game in);

}
