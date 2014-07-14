package com.github.kristofa.connect4

/**
 * Represents a Player.
 */
trait Player {
  
  import Disc._
  
  val name:String
  val disc:Disc
  
  /**
   * Determines next move based on given board state.
   * 
   * @return Column in which to drop a disc.
   */
  def next(board: Board): Integer;

}