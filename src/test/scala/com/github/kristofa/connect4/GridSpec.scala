package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.{Player1, Player2}
import com.github.kristofa.connect4.GridStates.{FourInALine, GameInProgress}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class GridSpec extends Specification {

  trait Context extends Scope with BetterEvaluation {
    val nrOfCols = 7
    val nrOfRows = 6
    val grid = Grid(nrOfCols, nrOfRows)
  }

  "grid specifications" >> {
    "Drop not possible in case outside of lower range column nr" in new Context {
      grid.dropPossible(-1) must beFalse
    }

    "Drop not possible in case outside of upper range column nr" in new Context {
      grid.dropPossible(nrOfCols) must beFalse
    }

    "Repetitive drop in same column should disallow drop when column is full" in new Context {
      val newGrid = grid.drop(0, Player1).
        drop(0, Player2).
        drop(0, Player1).
        drop(0, Player2).
        drop(0, Player1).
        drop(0, Player2)
      newGrid.dropPossible(0) must beFalse
      newGrid.drop(0, Player1) must throwA[IllegalStateException]
    }

    "Counting nr of discs" in new Context {
      grid.nrOfDiscs ==== 0
      val newGrid = grid.drop(0, Player1)
      newGrid.nrOfDiscs ==== 1
      val newGrid2 = newGrid.drop(0, Player2)
      newGrid2.nrOfDiscs ==== 2
      newGrid.nrOfDiscs ==== 1
      grid.nrOfDiscs ==== 0
    }

    """
    Creates 4 in a line horizontal

    ++++

    """ in new Context {
      val newGrid = grid.drop(0, Player1).drop(1, Player1).drop(2, Player1).drop(3, Player1)
      newGrid.state ==== FourInALine
      newGrid.winningDisc ==== Some(Player1)
      newGrid.drop(4, Player2) must throwA[IllegalStateException]
    }

    """
    Creates 4 in a line vertical

    +
    +
    +
    +

    """ in new Context {
      val newGrid = grid.drop(3, Player2).drop(3, Player2).drop(3, Player2).drop(3, Player2)
      newGrid.state ==== FourInALine
      newGrid.winningDisc ==== Some(Player2)
      newGrid.drop(3, Player1) must throwA[IllegalStateException]
    }

    """
    Creates 4 in a line diagonally from top to bottom.

    +
    -
    +-+
    -+-
    +-+-

    """ in new Context {
      val newGrid = grid.drop(0, Player1).drop(0, Player2).drop(0, Player1).drop(0, Player2).
        drop(0, Player1).drop(1, Player2).drop(1, Player1).
        drop(1, Player2).
        drop(2, Player1).drop(2, Player2).drop(2, Player1).drop(3, Player2)
      newGrid.state ==== FourInALine
      newGrid.winningDisc ==== Some(Player2)
    }

    """
    Creates 4 in a line diagonally from bottom to top.

       +
       -
     +-+
     -+-
    -+-+

    """ in new Context {
      val newGrid = grid.drop(6, Player1).drop(6, Player2).
        drop(6, Player1).drop(6, Player2).
        drop(6, Player1).drop(5, Player2).
        drop(5, Player1).drop(5, Player2).
        drop(4, Player1).drop(4, Player2).
        drop(4, Player1).drop(3, Player2)
      newGrid.state ==== FourInALine
      newGrid.winningDisc ==== Some(Player2)
    }

    "Get grid values for an empty grid" in new Context {
      for (col <- 0 until nrOfCols) {
        for (row <- 0 until nrOfRows) {
          grid.value(col, row) ==== None
        }
      }
    }

    "Get grid values after drop" in new Context {
      val newGrid = grid.drop(0, Player1).drop(6, Player2)

      newGrid.value(0, 5) ==== Some(Player1)
      newGrid.value(0, 4) ==== None
      newGrid.value(1, 5) ==== None

      newGrid.value(6, 5) ==== Some(Player2)
      newGrid.value(6, 4) ==== None
      newGrid.value(5, 5) ==== None
    }

    "test cells" in new Context {
      val smallgrid = Grid(2, 1)
      val cells = smallgrid.cells
      smallgrid.nrOfCols * smallgrid.nrOfRows ==== cells.length
      cells.contains(Cell(0, 0)) must beTrue
      cells.contains(Cell(1, 0)) must beTrue
    }
  }
}
