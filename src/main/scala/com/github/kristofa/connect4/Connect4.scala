package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.{Player2, Player1}
import com.github.kristofa.connect4.GridStates.{Full, GameInProgress, FourInALine}

/**
 * Main app. Entry point for application.
 */
object Connect4 extends App {

  val gridNrOfCols = 7
  val gridNrOfRows = 6
  val computerAlgoDepth = 7

  val grid = Grid(gridNrOfCols, gridNrOfRows)
  val player1 = new ComputerPlayer("Computer", Player1, computerAlgoDepth) with BetterEvaluation
  val player2 = new HumanPlayer("Human", Player2)

  val finalGrid = play(player1, player2, grid)
  finalGrid.state match {
    case FourInALine =>
      println("Four in a line")
      val winningPlayer = if (finalGrid.winningDisc == Some(player1.disc)) {
        player1.name
      } else {
        player2.name
      }
      println(s"Player $winningPlayer won.")
    case Full =>
      println("Draw. Board is full")
    case _ =>
      throw new IllegalStateException("Game ended in unexpected state.")
  }

  private def play(currentPlayer: Player, otherPlayer: Player, grid: Grid): Grid = {
    val next = currentPlayer.next(grid)
    val updatedGrid = grid.drop(next, currentPlayer.disc)
    draw(updatedGrid, player1, player2)
    if (updatedGrid.state == GameInProgress) {
      play(otherPlayer, currentPlayer, updatedGrid)
    }
    else
      updatedGrid
  }

  private def draw(grid: Grid, player1: Player, player2: Player): Unit = {
    println(s"""${player1.name} : ${player1.disc.asciiRepresentation}, ${player2.name} : ${player2.disc.asciiRepresentation}, No value: o""")
    for (row <- 0 until grid.nrOfRows) {
      for (col <- 0 until grid.nrOfCols) {
        grid.value(col, row) match {
          case Some(player) => print(player.asciiRepresentation)
          case None => print("o")
        }
      }
      println()
    }
  }
}