public abstract class Solver {

    protected int heuristicFunction(Board in){
        return in.relativeScore;
    }

    public abstract Edge getBestMove(Game in);

}
