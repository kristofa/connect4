package com.github.kristofa.connect4

import com.github.kristofa.connect4.GridStates.FourInALine

/**
 * This is a simple but un-effective evaluation function.
 */
trait DefaultEvaluation extends Evaluation {

  override def evaluate(board: Grid, player: Player): Int = {
    if (board.state == FourInALine)
      if (board.winningDisc == Some(player.disc))
        1000
      else
        0
    else
      500
  }


}