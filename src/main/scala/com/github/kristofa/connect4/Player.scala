package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.Disc

/**
 * Represents a Player.
 */
trait Player {

  val name:String
  val disc:Disc
  
  /**
   * Determines next move based on given Grid state.
   * 
   * @return Column in which to drop a disc.
   */
  def next(grid: Grid): Int

}