package com.github.kristofa.connect4

import com.github.kristofa.connect4.Discs.Disc
import com.github.kristofa.connect4.GridStates._

/**
 * Represents the game board grid.
 * The grid is immutable.
 * Every drop of a disc returns a new Grid instance.
 *
 * @param nrOfCols
 * @param nrOfRows
 * @param content
 * @param state
 * @param winningDisc
 */
case class Grid private(nrOfCols:Int,
                        nrOfRows:Int,
                        private val content:List[Option[Disc]],
                        state:GridState,
                        winningDisc:Option[Disc]) {

  def value(col:Int, row:Int): Option[Disc] = {
    if (validCol(col) && validRow(row))
      content(indexFor(col, row))
    else
      throw new IllegalArgumentException(s"Invalid col and/or row: col: $col, row: $row.")
  }

  def value(cell:Cell): Option[Disc] =
    value(cell.col, cell.row)

  def nrOfDiscs =
    content.filter(_ != None).length

  def dropPossible(col:Int):Boolean =
    validCol(col) && value(col, 0) == None && state != FourInALine

  def drop(col:Int, disc:Disc): Grid =
    if (dropPossible(col)) {
      drop(col, nrOfRows-1, disc) match {
        case (newContent, row) =>
          if (winner(newContent, col, row, disc))
            new Grid(nrOfCols, nrOfRows, newContent, FourInALine, Some(disc))
          else if (row == 0 && allColumnsFull(newContent))
            new Grid(nrOfCols, nrOfRows, newContent, Full, None)
          else
            new Grid(nrOfCols, nrOfRows, newContent, GameInProgress, None)
      }
    } else
      throw new IllegalStateException(s"Dropping disc at col $col not possible.")

  def cells : Seq[Cell] =
    for(
      col <- 0 until nrOfCols;
      row <- 0 until nrOfRows
    ) yield(Cell(col, row))

  private def winner(content:List[Option[Disc]], col:Int, row:Int, disc:Disc) : Boolean =
    winner(content, Grid.horizontal(col, row), disc) ||
    winner(content, Grid.vertical(col, row), disc) ||
    winner(content, Grid.diagonalTopLeftToBottomRight(col, row), disc) ||
    winner(content, Grid.diagonalBottomLeftToTopRight(col, row), disc)

  private def winner(content:List[Option[Disc]], cellArray:Array[Cell], disc:Disc):Boolean = {
    val cells = cellArray.foldLeft(List[Cell]())(
      (list, cell) =>
        if (list.length == 4)
          list
        else if (validCol(cell.col) && validRow(cell.row) && (content(indexFor(cell.col, cell.row)) == Some(disc)))
          cell :: list
        else
          List()
    )
    cells.size == 4
  }

  private def drop(col:Int, row:Int, disc:Disc): (List[Option[Disc]],Int) = {
    value(col, row) match {
      case None =>
        val newContent = content.updated(indexFor(col, row), Some(disc))
        newContent -> row
      case Some(value) => drop(col, row-1, disc)
    }
  }

  private def allColumnsFull(content:List[Option[Disc]]) : Boolean =
    (0 until nrOfCols).filter(col => content(indexFor(col, 0)) != None).length == nrOfCols

  private def validCol(col:Int) = col >= 0 && col < nrOfCols

  private def validRow(row:Int) = row >= 0 && row < nrOfRows

  private def indexFor(col:Int, row:Int) =
    col + (nrOfCols * row)

}

object Grid {

  def apply(nrOfCols:Int, nrOfRows:Int) = {
    val content = List.fill[Option[Disc]](nrOfCols * nrOfRows)(None)
    new Grid(nrOfCols, nrOfRows, content, GameInProgress, None)
  }

  /**
   * Return the cells for given col+row which need to be checked to verify if we
   * can have 4 in a row horizontally.
   *
   * @param col Column.
   * @param row Row.
   * @return Cells.
   */
  def horizontal(col: Int, row: Int): Array[Cell] =
    Array(
      Cell(col - 3, row), Cell(col - 2, row),
      Cell(col - 1, row), Cell(col, row),
      Cell(col + 1, row), Cell(col + 2, row), Cell(col + 3, row)
    )

  /**
   * Return the cells for given col+row which need to be checked to verify if we
   * can have 4 in a row vertically.
   *
   * @param col Column.
   * @param row Row.
   * @return Cells.
   */
  def vertical(col: Int, row: Int): Array[Cell] =
    Array(
      Cell(col, row - 3), Cell(col, row - 2),
      Cell(col, row - 1), Cell(col, row),
      Cell(col, row + 1), Cell(col, row + 2), Cell(col, row + 3)
    )

  /**
   * Return the cells for given col+row which need to be checked to verify if we
   * can have 4 in a row diagonally from top left to bottom right.
   *
   * @param col Column.
   * @param row Row.
   * @return Cells.
   */
  def diagonalTopLeftToBottomRight(col: Int, row: Int): Array[Cell] =
    Array(
      Cell(col - 3, row - 3), Cell(col - 2, row - 2),
      Cell(col - 1, row - 1), Cell(col, row),
      Cell(col + 1, row + 1), Cell(col + 2, row + 2), Cell(col + 3, row + 3)
    )

  /**
   * Return the cells for given col+row which need to be checked to verify if we
   * can have 4 in a row diagonally from bottom left to top right.
   *
   * @param col Column.
   * @param row Row.
   * @return Cells.
   */
  def diagonalBottomLeftToTopRight(col: Int, row: Int): Array[Cell] =
    Array(
      Cell(col - 3, row + 3), Cell(col - 2, row + 2),
      Cell(col - 1, row + 1), Cell(col, row),
      Cell(col + 1, row - 1), Cell(col + 2, row - 2), Cell(col + 3, row - 3)
    )
}