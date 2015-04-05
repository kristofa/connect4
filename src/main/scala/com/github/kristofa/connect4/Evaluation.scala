package com.github.kristofa.connect4


/**
 * Used to evaluate game board in given state for given player.
 */
trait Evaluation {

  /**
   * Evaluate given grid for given player.
   * Returns a positive integer value (0 inclusive). The higher the value the better the situation
   * for the given player.
   *
   * @param grid Game grid.
   * @param player Player for who to evaluate board.
   */
  def evaluate(grid: Grid, player: Player): Int = 0
}