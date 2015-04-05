package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.Player1
import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class BetterEvaluationSpec extends Specification {
  
  trait Context extends Scope with BetterEvaluation {
    val nrOfCols = 7
    val nrOfRows = 6
    val grid = Grid(nrOfCols, nrOfRows)
    val player = new HumanPlayer("human", Player1)
  }

  "BetterEvaluation spec" >> {
    "Evaluation of an empty grid" in new Context {
      0 ==== evaluate(grid, player)
    }

    "Evaluate most optimal first move (4th column)" in new Context {
      val newGrid = grid.drop(3, player.disc)
      18 ==== evaluate(newGrid, player)
    }

    "Evaluate 1st column first move" in new Context {
      val newGrid = grid.drop(0, player.disc)
      11 ==== evaluate(newGrid, player)
    }

    "Evaluate 2nd column first move" in new Context {
      val newGrid = grid.drop(1, player.disc)
      12 ==== evaluate(newGrid, player)
    }

    "Evaluate 3rd column first move" in new Context {
      val newGrid = grid.drop(2, player.disc)
      13 ==== evaluate(newGrid, player)
    }

    "Evaluate 5th column first move" in new Context {
      val newGrid = grid.drop(4, player.disc)
      13 ==== evaluate(newGrid, player)
    }

    "Evaluate 6th column first move" in new Context {
      val newGrid = grid.drop(5, player.disc)
      12 ==== evaluate(newGrid, player)
    }

    "Evaluate last column first move" in new Context {
      val newGrid = grid.drop(6, player.disc)
      11 ==== evaluate(newGrid, player)
    }
  }


}