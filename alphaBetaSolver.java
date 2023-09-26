import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class alphaBetaSolver extends Solver {
    final static int MIN = -1000000000, MAX = 1000000000;
    long timeStarted;
    long boundedTime = 9 * 1070;
    short depthLevelMax = 2;

    private static final int TIME_LIMIT_MILLIS = 9000;
	private static final int EVALS_PER_SECOND = 100;
	private static final int emptyPosition = 0;
	private static final int aiPosition = 1;
	private static final int humanPosition = 2;
	private static final int winCutoff = 41; //idk what this was 
    boolean searchCutoff;








    // @Override
    // public Edge getBestMove(Game in) {
    //     timeStarted = System.currentTimeMillis();
    //     //while (time < 10 seconds) {
          

    //     }
    //     //return 
    

    // public Bound depthFirstSearch(Board in, int alpha, int beta, short depthLeft) {
        
    //         return new Bound(null, heuristicFunction(in));
    //     }
            
    




    //
	// chooseMove() scores each of the possible moves that can be made from the given AIGameState, and returns the move with the highest eval score
	//
    @Override
    public Edge getBestMove(Game in) {
		long startTime = System.currentTimeMillis();
        Board boardConfiguration = in.currentBoard;
		//boolean maximizing = in.currentBoard.isMyTurn;
		int maxScore = Integer.MIN_VALUE;
		Edge bestMove = null;
		
		ArrayList<Edge> moves = boardConfiguration.allPossibleMoves;
        int length = moves.size();
        int count = 0;
        //Collections.shuffle(moves);
		
		for (Edge move : moves) {
            count++;
            //System.out.println("HRERHERERER");
			//
			// Copy the current game state
			//
			Board newState = boardConfiguration.clone();
			
			newState.addMove(move);
            newState.switchTurn();
			
			//
			// Compute how long to spend looking at each move
			//
			long searchTimeLimit = ((TIME_LIMIT_MILLIS - 1000) / (moves.size()));
			
			int score = iterativeDeepeningSearch(newState, searchTimeLimit);
            System.out.println("result from interative deepening search"+ score+ " also the count "+count);
			
			//
			// If the search finds a winning move
            /*if (searchResult >= winCutoff) {
				return searchResult;
			} */
			//
            
			if ((System.currentTimeMillis()- startTime > 9000)) { //break out condition
                System.out.print("running out of time and sent "+ move.toString());
				return move; //not getting here
			} else if ((count == (length * (length-1) * (length-2)))) { //since depth is two
                System.out.print("went through all the moves"); //maybe should onlye me moves.length???
            }
			
			if (score > maxScore) { //what is score and maxScore
				maxScore = score;
				bestMove = move;
                System.out.println("bestMove score"+ score);
			}
		}
        System.out.println("maxscore"+maxScore);
		return bestMove;
	}
	
	//
	// Run an iterative deepening search on a game state, taking no longrer than the given time limit
	//
	private int iterativeDeepeningSearch(Board boardConfiguration, long timeLimit) {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeLimit;
		int depth = 3; //1 
		int score = 0;
		searchCutoff = false;
		
		while (true) {
			long currentTime = System.currentTimeMillis();
			
			if (currentTime >= endTime) {
				break;
			}
			
			int searchResult = search(boardConfiguration, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, currentTime, endTime - currentTime);
			System.out.println("search results are as follows "+ searchResult);
			//
			// If the search finds a winning move, stop searching
			//searchResult >= winCutoff
			if (System.currentTimeMillis()- startTime > 9000) {
				return searchResult;
			}
			
            //!searchCutoff
			if (!searchCutoff) {
				score = searchResult; //HHHHH???
			}
			
			depth++;
		}
		
		return score;
	}
	
	//
	// search() will perform minimax search with alpha-beta pruning on a game state, and will cut off if the given time
	// limit has elapsed since the beginning of the search
	//
	private int search(Board boardConfiguration, int depth, int alpha, int beta, long startTime, long  timeLimit) {
		ArrayList<Edge> moves = boardConfiguration.allPossibleMoves;
        //ArrayList<AIGameMove> moves = state.validMoves();
		boolean maximizing = boardConfiguration.isMyTurn;
		int savedScore = (maximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		///int score = Evaluator.eval(boardConfiguration);
        int score = boardConfiguration.relativeScore;
		long currentTime = System.currentTimeMillis();
		long elapsedTime = (currentTime - startTime);
		
        if (System.currentTimeMillis()-startTime > 9000) { //maybe this statement
		//if (elapsedTime >= timeLimit) {
			searchCutoff = true;
		}
		
		//
		// If this is a terminal node or a win for either player, abort the search
		//
		if (searchCutoff || (depth == 0) || (moves.size() == 0)) {
            //System.out.println("relative score "+score);
			return score;
		}
		
		if (boardConfiguration.isMyTurn) {
			for (Edge move : moves) {
				Board childState = boardConfiguration.clone();
				childState.addMove(move);
                childState.switchTurn();

				alpha = Math.max(alpha, search(childState, depth - 1, alpha, beta, startTime, timeLimit));
				
				if (beta <= alpha) {
					break;
				}
			}
			
			return alpha;
		} else {
			for (Edge move : moves) {
				Board childState = boardConfiguration.clone();
				childState.addMove(move);
                childState.switchTurn();
				
				beta = Math.min(beta, search(childState, depth - 1, alpha, beta, startTime, timeLimit));
					
				if (beta <= alpha) {
					break;
				}
			}
			
			return beta;
            
		}
	}
}