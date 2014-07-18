package com.github.kristofa.connect4

/**
 * Better than DefaultEvaluation.
 * <p/>
 * It gives a score based on how many times we can still make 4 in a line for given player.
 * <p/>
 * It has shortcuts in case player has 4 in line, other player has 4 in line or board is full.
 * 
 */
trait BetterEvaluation extends Evaluation {
    import Disc._
 
	def evaluate(board:Board, player:Player): Int = {
	    
	  if (board.getBoardState == BoardState.FourInALine )
	  {
	    if (board.getWinningDisc == player.disc) Integer.MAX_VALUE else 0
	  }
	  else if (board.getBoardState == BoardState.Full)
	  {
	    0
	  }
	  else
	  {
	    var value = 0
	    for(col <- 0 until board.nrOfCols ) {
	      for (row <- 0 until board.nrOfRows ) {
	    	  if (board.getBoardValue(col, row) == player.disc)
	    	  {
	    		value += (calc(horizontal(col, row), board, player.disc) + 
	    		    calc(vertical(col, row), board, player.disc) + 
	    		    calc(diagonal1(col, row), board, player.disc) + 
	    		    calc(diagonal2(col, row), board, player.disc))
	    	  }
	      }
	    }
	    value
	  }
	}
    
   private def calc(seq : Array[(Int,Int)], board : Board, disc: Disc) : Int = {
     val value = nrSubSequent(seq, board, disc)
	 if (value >= 4)
	 {
		 value
	 }
	 else
	 {
		 0
	 }
   }
	
	private def nrSubSequent(seq : Array[(Int,Int)], board : Board, disc: Disc): Int =
	{
	  var maxSubseq = 0
	  var subseq = 0
	  for( (col, row) <- seq)
	  {
	    if (col >= 0 && col < board.nrOfCols  && row >=0 && row < board.nrOfRows )
	    {
	      if (board.getBoardValue(col, row) == disc || board.getBoardValue(col, row) == null)
	      {
	    	subseq += 1  
	        maxSubseq = math.max(maxSubseq, subseq)
	      }
	      else
	      {
	        subseq = 0
	      }
	    }
	  }
	  maxSubseq
	}
	
	private def horizontal(col:Int, row:Int): Array[(Int,Int)] =
	{
	  Array((col-3, row), (col-2, row), (col-1, row), (col, row), (col+1, row), (col+2, row), (col+3, row))
	}
  
	private def vertical(col:Int, row:Int): Array[(Int,Int)] =
	{
	  Array((col, row-3), (col, row-2), (col, row-1), (col, row), (col, row+1), (col, row+2), (col, row+3))
	}
  
	private def diagonal1(col:Int, row:Int): Array[(Int,Int)] =
	{
	  Array((col-3, row-3), (col-2, row-2), (col-1, row-1), (col, row), (col+1, row+1), (col+2, row+2), (col+3, row+3))
	}
  
	private def diagonal2(col:Int, row:Int): Array[(Int,Int)] =
	{
	  Array((col-3, row+3), (col-2, row+2), (col-1, row+1), (col, row), (col+1, row-1), (col+2, row-2), (col+3, row-3))
	}
	

}