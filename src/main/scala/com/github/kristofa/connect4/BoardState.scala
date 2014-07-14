package com.github.kristofa.connect4

/**
 * Represents the different states of the game board.
 */
object BoardState extends Enumeration {
  
  type BoardState = Value
  val GameInProgress, FourInALine, Full = Value

}