package com.github.kristofa.connect4

import Disc._

/**
 * Better than DefaultEvaluation.
 * <p/>
 * It gives a score based on how many times we can still make 4 in a line for given player.
 * <p/>
 * It has shortcuts in case player has 4 in line, other player has 4 in line or board is full.
 * <p/>
 * It also favours early win situation by taking into account the nr of discs present in the board.
 * 
 */
trait BetterEvaluation extends Evaluation {
    
 
	 override def evaluate(board:Board, player:Player): Int = {
	    
	  if (board.getBoardState == BoardState.FourInALine )
	  {
	    if (board.getWinningDisc == player.disc) Integer.MAX_VALUE - board.getNrOfDiscs else Integer.MIN_VALUE
	  }
	  else if (board.getBoardState == BoardState.Full)
	  {
	    0
	  }
	  else
	  {
	    val value = board.cellIndices.foldLeft(0)( (curVal, cell) => {
	        if (board.getBoardValue(cell._1, cell._2) == player.disc)
	    	{
	           curVal + (calc(horizontal(cell._1, cell._2), board, player.disc) + 
	    		    calc(vertical(cell._1, cell._2), board, player.disc) + 
	    		    calc(diagonal1(cell._1, cell._2), board, player.disc) + 
	    		    calc(diagonal2(cell._1, cell._2), board, player.disc))
	    	}
	        else
	        {
	          curVal
	        }    
	    }
	    )
	    value - board.getNrOfDiscs
	   
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