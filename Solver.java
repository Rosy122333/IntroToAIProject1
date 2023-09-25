public abstract class Solver {

    protected int heuristicFunction(Board in){
        return in.relativeScore;
        // int score = 0;
        // if (in.countOpenEdges() % 2 == 0) {
        //     score++;
        // } else {
        //     score--;
        // }
        // return score;
    }

    public abstract Edge getBestMove(Game in);

}
