package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.Disc
import com.github.kristofa.connect4.GridStates.GameInProgress

import scala.io.StdIn

/**
 * Player instance that request changes from console (= from a human).
 * 
 * @constructor Creates a new Human Player instance.
 * @param name Name of player.
 * @param disc Disc type player plays with.
 */
class HumanPlayer(val name:String, val disc:Disc) extends Player {
  
  def next(grid: Grid): Int = {
     if (grid.state != GameInProgress)
     {
    	 throw new IllegalStateException("Expected game to be still in progress.")
	   }
     var choice:Int = 0
     do
     {
    	 printf("Column choice (0-"+(grid.nrOfCols-1)+") : ")
    	 choice = StdIn.readInt()
     
     } while(grid.dropPossible(choice) == false)
     choice
  }

}