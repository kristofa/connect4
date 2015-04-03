package com.github.kristofa.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class BoardSpec extends Specification {

  trait Context extends Scope with BetterEvaluation {
    val nrOfCols = 7
    val nrOfRows = 6
    val board = new Board(nrOfCols, nrOfRows)
  }

  "Board specifications" >> {
    "Drop not possible in case outside of lower range column nr" in new Context {
      board.dropPossible(-1) must beFalse
    }

    "Drop not possible in case outside of upper range column nr" in new Context {
      board.dropPossible(nrOfCols) must beFalse
    }

    "Drop invalid value should throw exception" in new Context {
      board.drop(1, null) must throwA[IllegalArgumentException]
    }

    "Repetitive drop in same column should disallow drop when column is full" in new Context {
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player1)
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player2)
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player1)
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player2)
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player1)
      board.dropPossible(0) must beTrue
      board.drop(0, Disc.Player2)
      board.dropPossible(0) must beFalse
      board.drop(0, Disc.Player1) must throwA[IllegalStateException]
    }

    "Counting nr of discs" in new Context {
      board.getNrOfDiscs ==== 0
      board.drop(0, Disc.Player1)
      board.getNrOfDiscs ==== 1
      board.drop(0, Disc.Player2)
      board.getNrOfDiscs ==== 2
    }

    """
    Creates 4 in a line horizontal

    ++++

    """ in new Context {
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(1, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(2, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player1) ==== BoardState.FourInALine
      board.getWinningDisc ==== Disc.Player1

      board.drop(4, Disc.Player2) must throwA[IllegalStateException]
    }

    """
    Creates 4 in a line vertical

    +
    +
    +
    +

    """ in new Context {
      board.drop(3, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player2) ==== BoardState.FourInALine
      board.getWinningDisc ==== Disc.Player2

      board.drop(3, Disc.Player1) must throwA[IllegalStateException]
    }

    """
    Creates 4 in a line diagonally from top to bottom.

    +
    -
    +-+
    -+-
    +-+-

    """ in new Context {
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(0, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(0, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(1, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(1, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(1, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(2, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(2, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(2, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player2) ==== BoardState.FourInALine
      board.getWinningDisc ==== Disc.Player2
    }

    """
    Creates 4 in a line diagonally from bottom to top.

       +
       -
     +-+
     -+-
    -+-+

    """ in new Context {
      board.drop(6, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(5, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(5, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(5, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(4, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(4, Disc.Player2) ==== BoardState.GameInProgress
      board.drop(4, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(3, Disc.Player2) ==== BoardState.FourInALine
      board.getWinningDisc ==== Disc.Player2
    }

    "Get board values for an empty board" in new Context {
      for (col <- 0 until nrOfCols) {
        for (row <- 0 until nrOfRows) {
          board.getBoardValue(col, row) must beNull
        }
      }
    }

    "Get board values after drop" in new Context {
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player2) ==== BoardState.GameInProgress

      board.getBoardValue(0, 5) ==== Disc.Player1
      board.getBoardValue(0, 4) must beNull
      board.getBoardValue(1, 5) must beNull

      board.getBoardValue(6, 5) ==== Disc.Player2
      board.getBoardValue(6, 4) must beNull
      board.getBoardValue(5, 5) must beNull
    }

    "Test copy constructor" in new Context {
      board.drop(0, Disc.Player1) ==== BoardState.GameInProgress
      board.drop(6, Disc.Player2) ==== BoardState.GameInProgress

      val boardCopy = new Board(board)

      boardCopy.getBoardValue(0, 5) ==== Disc.Player1
      boardCopy.getBoardValue(0, 4) must beNull
      boardCopy.getBoardValue(1, 5) must beNull

      boardCopy.getBoardValue(6, 5) ==== Disc.Player2
      boardCopy.getBoardValue(6, 4) must beNull
      boardCopy.getBoardValue(5, 5) must beNull
    }

    "test hashcode" in new Context {
      board.drop(0, Disc.Player1)
      val boardCopy = new Board(board)
      board.hashCode ==== boardCopy.hashCode
    }

    "test equals" in new Context {
      board.equals(null) must beFalse
      board.equals(new String()) must beFalse
      board.equals(board) must beTrue

      val other = new Board(7, 6)
      other.drop(0, Disc.Player1)
      board.equals(other) must beFalse

      val copy = new Board(other)
      other.equals(copy) must beTrue
      copy.equals(other) must beTrue
    }

    "test cell indices" in new Context {
      val smallBoard = new Board(2, 1)
      val indices = smallBoard.cellIndices
      smallBoard.nrOfCols * smallBoard.nrOfRows ==== indices.length
      indices.contains((0, 0)) must beTrue
      indices.contains((1, 0)) must beTrue
    }
  }
}