package com.github.kristofa.connect4

/**
 * This is a quite louzy evaluation function.
 */
trait DefaultEvaluation extends Evaluation {
  
  /**
    * Evaluate given board for given player.
    * Returns a positive integer value (0 inclusive). The higher the value the better the situation
    * for the given player.
    *  
    */
	override def evaluate(board:Board, player:Player): Int = {
	    
	  if (board.getBoardState == BoardState.FourInALine )
	  {
	    if (board.getWinningDisc == player.disc) 1000 else 0
	  }
	  else
	  {
	    500
	  }
	}
	
	

}