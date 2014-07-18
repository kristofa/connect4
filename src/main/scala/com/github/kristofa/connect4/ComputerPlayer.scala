package com.github.kristofa.connect4

import Disc._
import BoardState._

/**
 * Computer player that uses <a href="http://en.wikipedia.org/wiki/Minimax">Minimax</a>
 * 
 * @constructor
 * @param name Player name
 * @param disc Disc player will use.
 * @param algoDepth How many steps need to computer player need to look forward.
 * @param evaluation Evaluation 'function' that gives a score to a given state of board.
 */
class ComputerPlayer(val name:String, val disc:Disc, val algoDepth:Int) extends Player with BetterEvaluation {
  
  private val otherPlayerDisc = { if (disc == Player1) Player2 else Player1 }
  
  /**
   * Select next move. Determine column in which to drop disc.
   */
  def next(board: Board): Integer = {
		  if (board.getBoardState != GameInProgress)
		  {
		    throw new IllegalStateException("Expected game to be still in progress.")
		  }
		  var alpha = Integer.MIN_VALUE
		  var beta = Integer.MAX_VALUE
		  var choice = 0
		  for(col <- 0 until board.nrOfCols)
		  {
			  if (board.dropPossible(col))
			  {
		    	val boardCopy = new Board(board)
		    	boardCopy.drop(col, disc)
		    	val value = minimax(boardCopy, algoDepth-1, false, alpha, beta)
		   		if (value > alpha)
		   		{
		   			alpha = value
		   			choice = col
		   		}
		   	 }
		 }
		 choice
  }
  
  
  private def minimax(board:Board, depth: Int, max:Boolean, alpha:Int, beta:Int) : Int = {
  	  
		  if (depth==0 || board.getBoardState != GameInProgress)
		  {
		     evaluate(board, this)
		  }
		  else if (max)
		  {
		    var prune = false
		    var alphaCopy = alpha
		    for(col <- 0 until board.nrOfCols if prune == false)
		    {
		        if (board.dropPossible(col))
		    	{
		    		val boardCopy = new Board(board)
		    		boardCopy.drop(col, disc)
		    		alphaCopy = math.max(alphaCopy, minimax(boardCopy, depth-1, false, alphaCopy, beta))
		    		if (beta <= alphaCopy) prune = true
		    	}
		    }
		    alphaCopy
		  }
		  else
		  {
		    var prune = false
		    var betaCopy = beta
		     for(col <- 0 until board.nrOfCols if prune == false)
		     {
		    	if (board.dropPossible(col))
		    	{
		    		val boardCopy = new Board(board)
		    		boardCopy.drop(col, otherPlayerDisc)
		    		betaCopy = math.min(betaCopy, minimax(boardCopy, depth-1, true, alpha, betaCopy))
		    		if (betaCopy <= alpha) prune = true
		    	}
		    }
		    betaCopy
		  }  
  	} 	

}