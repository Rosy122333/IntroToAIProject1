public abstract class Solver {

    protected int heuristicFunction(Board in){
        //return in.relativeScore;
        return in.findClosings() + in.countChainControl() + in.countEdgeOwnership() + in.calculateEdgeOddOrEven() + in.calculateMobility(); 
    }

    public abstract Edge getBestMove(Game in);

}
