package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.{Player2, Player1, Disc}
import com.github.kristofa.connect4.GridStates.GameInProgress

/**
 * Computer player that uses <a href="http://en.wikipedia.org/wiki/Minimax">Minimax</a>
 *
 * @constructor
 * @param name Player name
 * @param disc Disc player will use.
 * @param algoDepth How many steps will the computer player calculate upfront to determine best next move.
 */
class ComputerPlayer(val name: String, val disc: Disc, val algoDepth: Int) extends Player with Evaluation {

  private val otherPlayerDisc = {
    if (disc == Player1) Player2 else Player1
  }

  /**
   * Select next move. Determine column in which to drop disc.
   */
  def next(grid: Grid): Int = {
    if (grid.state != GameInProgress) {
      throw new IllegalStateException("Expected game to be still in progress.")
    }
    var alpha = Integer.MIN_VALUE
    var beta = Integer.MAX_VALUE
    var choice = 0
    for (col <- 0 until grid.nrOfCols) {
      if (grid.dropPossible(col)) {
        val newGrid = grid.drop(col, disc)
        val value = minimax(newGrid, algoDepth - 1, false, alpha, beta)
        if (value > alpha) {
          alpha = value
          choice = col
        }
      }
    }
    choice
  }


  private def minimax(grid: Grid, depth: Int, max: Boolean, alpha: Int, beta: Int): Int = {
    if (depth == 0 || grid.state != GameInProgress) {
      evaluate(grid, this)
    }
    else if (max) {
      var prune = false
      var alphaCopy = alpha
      for (col <- 0 until grid.nrOfCols if prune == false) {
        if (grid.dropPossible(col)) {
          val newGrid = grid.drop(col, disc)
          alphaCopy = math.max(alphaCopy, minimax(newGrid, depth - 1, false, alphaCopy, beta))
          if (beta <= alphaCopy) prune = true
        }
      }
      alphaCopy
    }
    else {
      var prune = false
      var betaCopy = beta
      for (col <- 0 until grid.nrOfCols if prune == false) {
        if (grid.dropPossible(col)) {
          val newGrid = grid.drop(col, otherPlayerDisc)
          betaCopy = math.min(betaCopy, minimax(newGrid, depth - 1, true, alpha, betaCopy))
          if (betaCopy <= alpha) prune = true
        }
      }
      betaCopy
    }
  }

}