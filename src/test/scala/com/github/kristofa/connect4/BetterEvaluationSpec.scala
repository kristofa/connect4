package com.github.kristofa.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class BetterEvaluationSpec extends Specification {
  
  trait Context extends Scope with BetterEvaluation {
    val nrOfCols = 7
    val nrOfRows = 6
    val board = new Board(nrOfCols, nrOfRows)
    val player = new HumanPlayer("human", Disc.Player1)
  }

  "BetterEvaluation spec" >> {
    "Evaluation of an empty board" in new Context {
      0 ==== evaluate(board, player)
    }

    "Evaluate most optimal first move (4th column)" in new Context {
      board.drop(3, player.disc)
      18 ==== evaluate(board, player)
    }

    "Evaluate 1st column first move" in new Context {
      board.drop(0, player.disc)
      11 ==== evaluate(board, player)
    }

    "Evaluate 2nd column first move" in new Context {
      board.drop(1, player.disc)
      12 ==== evaluate(board, player)
    }

    "Evaluate 3rd column first move" in new Context {
      board.drop(2, player.disc)
      13 ==== evaluate(board, player)
    }

    "Evaluate 5th column first move" in new Context {
      board.drop(4, player.disc)
      13 ==== evaluate(board, player)
    }

    "Evaluate 6th column first move" in new Context {
      board.drop(5, player.disc)
      12 ==== evaluate(board, player)
    }

    "Evaluate last column first move" in new Context {
      board.drop(6, player.disc)
      11 ==== evaluate(board, player)
    }
  }


}