package com.github.kristofa.connect4

import Disc._
import BoardState._

/**
 * Player instance that request changes from console (= from a human).
 * 
 * @constructor Creates a new Human Player instance.
 * @param name Name of player.
 * @param disc Disc type player plays with.
 */
class HumanPlayer(val name:String, val disc:Disc) extends Player {
  
  def next(board: Board): Integer = {
     if (board.getBoardState != GameInProgress)
     {
    	 throw new IllegalStateException("Expected game to be still in progress.")
	 }
     
     var choice:Int = 0
     do
     {
    	 printf("Column choice (0-"+(board.nrOfCols-1)+") : ")
    	 choice = readInt()
     
     } while(board.dropPossible(choice) == false)
     choice
    
  }

}