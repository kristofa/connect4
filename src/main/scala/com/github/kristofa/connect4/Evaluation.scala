package com.github.kristofa.connect4

import Disc._

/**
 * Used to evaluate game board in given state for given player.
 */
trait Evaluation {
	
   /**
    * Evaluate given board for given player.
    * Returns a positive integer value (0 inclusive). The higher the value the better the situation
    * for the given player.
    *  
    * @param board Game board
    * @param player Player for who to evaluate board.
    */
	def evaluate(board:Board, player:Player): Int = 0
}