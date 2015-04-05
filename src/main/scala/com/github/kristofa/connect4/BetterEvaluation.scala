package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.Disc
import com.github.kristofa.connect4.GridStates.{GameInProgress, Full, FourInALine}

/**
 * Better than DefaultEvaluation.
 * <p/>
 * It gives a score based on how many times we can still make 4 in a line for given player.
 * <p/>
 * It has shortcuts in case player has 4 in line, other player has 4 in line or grid is full.
 * <p/>
 * It also favours early win situation by taking into account the nr of discs present in the grid.
 *
 */
trait BetterEvaluation extends Evaluation {

  case class CurrentAndMax(current:Int, max:Int)

  override def evaluate(grid: Grid, player: Player): Int = {
    grid.state match {
      case GameInProgress => calcInProgress(grid, player)
      case FourInALine => calc4InALine(grid, player)
      case Full => 0
    }
  }

  private def calc4InALine(grid: Grid, player: Player) =
    if (grid.winningDisc == Some(player.disc))
      Integer.MAX_VALUE - grid.nrOfDiscs
    else
      Integer.MIN_VALUE

  private def calcInProgress(grid: Grid, player: Player): Int = {
    val value = grid.cells.foldLeft(0)(
      (curVal, cell) =>
        if (grid.value(cell) == Some(player.disc)) {
          curVal + (calc(Grid.horizontal(cell.col, cell.row), grid, player.disc) +
          calc(Grid.vertical(cell.col, cell.row), grid, player.disc) +
          calc(Grid.diagonalTopLeftToBottomRight(cell.col, cell.row), grid, player.disc) +
          calc(Grid.diagonalBottomLeftToTopRight(cell.col, cell.row), grid, player.disc))
        }
        else curVal
    )
    value - grid.nrOfDiscs
  }

  private def calc(seq: Array[Cell], grid: Grid, disc: Disc): Int = {
    val value = seq.foldLeft(CurrentAndMax(0, 0))(
      (currentAndMax, cell) =>
        if (validCellForGrid(cell, grid) && (grid.value(cell) == Some(disc) || grid.value(cell) == None)) {
          val current = currentAndMax.current + 1
          CurrentAndMax(current, math.max(current, currentAndMax.max))
        }
        else
          CurrentAndMax(0, currentAndMax.max)
    )
    if (value.max >= 4)
      value.max
    else
      0
  }

  private def validCellForGrid(cell:Cell, grid:Grid): Boolean =
    (cell.col >= 0) &&
      (cell.col < grid.nrOfCols) &&
      (cell.row >= 0) &&
      (cell.row < grid.nrOfRows)


}