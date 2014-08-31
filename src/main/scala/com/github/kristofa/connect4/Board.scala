package com.github.kristofa.connect4

import Disc._
import BoardState._

/**
 * Connect4 game board.
 * 
 * @constructor Creates a new empty game board with a given number of columns and rows.
 * @param nrOfCols Number of columns for our board. A standard Connect4 board has 7 columns.
 * @param nrOfRows Number of rows for our board. A standard Connect4 board has 6 rows.
 */
class Board(val nrOfCols:Int, val nrOfRows:Int) {

  private val content = Array.ofDim[Disc](nrOfCols, nrOfRows)
  for(col <- 0 until nrOfCols; row <- 0 until nrOfRows)
  {
	  content(col)(row) = null 
  }
  
  private val rowNextDrop = new Array[Int](nrOfCols);
  for(col <- 0 until nrOfCols) rowNextDrop(col) = (nrOfRows-1)
  
  private var boardState = GameInProgress 
  private var winningDisc:Disc = null
  private var nrOfDiscs = 0

  /**
   * Copy constructor.
   * 
   * @constructor Creates a new board that will be a copy of given board.
   * @param board Board we want to copy.
   */
  def this(board: Board) {
    this(board.nrOfCols, board.nrOfRows)
    for(col <- 0 until board.nrOfCols; row <- 0 until board.nrOfRows)
    {
      content(col)(row) = board.content(col)(row)
    }
    
    for(col <- 0 until board.nrOfCols)
    {
      rowNextDrop(col) = board.rowNextDrop(col)
    }
    
    boardState  = board.boardState 
    winningDisc  = board.winningDisc 
    nrOfDiscs = board.nrOfDiscs 
  }
  
  /**
   * Get the current board state.
   */
  def getBoardState = boardState
  
  /**
   * In case board state is FourInALine you can use this to get winning disc.
   */
  def getWinningDisc = winningDisc 
  
  /**
   * Gets the nr of discs in the board.
   */
  def getNrOfDiscs = nrOfDiscs 
  
  /**
   * Gets board content value at given col and row.
   */
  def getBoardValue(col:Int, row:Int): Disc = { if (col >=0 && col < nrOfCols && row >=0 && row < nrOfRows) content(col)(row) else null } 
  
  /**
   * Checks if it is possible to drop disc at given column.
   * 
   * @param colNr Column. Index starts at 0 and ends at nrOfCols - 1.
   */
  def dropPossible(colNr:Int) : Boolean = { if (colNr >= 0 && colNr < nrOfCols && content(colNr)(0) == null && boardState == GameInProgress) true else false  }
  
  /**
   * Drop disc at given column.
   * 
   * @param colNr Column. Index starts at 0 and ends at nrOfCols - 1.
   * @param value Any valid Disc.
   */
  def drop(colNr:Int, value:Disc) : BoardState = {
    
    if (value == null)
    {
      throw new IllegalArgumentException("Can't drop value null.");
    }
    if (dropPossible(colNr))
    {
      val rowNr = rowNextDrop(colNr)
      content(colNr)(rowNr) = value
      rowNextDrop(colNr) = rowNextDrop(colNr) - 1
      nrOfDiscs  += 1
         
      if (fourInLine(colNr, rowNr))
      {
    	  boardState = FourInALine
    	  winningDisc = value
      } else if (full)
      {
    	  boardState = Full
      } 
      boardState 
    }
    else
    {
      throw new IllegalStateException("Unable to put value in column.")
    }
  }
  
  
  final override def hashCode = 17 * nrOfCols + 31 * nrOfRows
     
  
  final override def equals(other: Any) = {
    
    if (other != null && other.isInstanceOf[Board])
    {
    	val that = other.asInstanceOf[Board]
		if (nrOfCols  == that.nrOfCols  && nrOfRows  == that.nrOfRows )
		{
			var equal = true
			for(col <- 0 until nrOfCols if equal ) {
			  for(row <- 0 until nrOfRows if equal ) {
			    if (content(col)(row) != that.content(col)(row))
			    {
			      equal = false
			    }
			  }
			}
			equal
		}
		else
		{
		  false
		}
    }
    else
    {
    	false
    }

  }
  
  private def full : Boolean = {
    var boardFull = true
    for(nextDrop <- rowNextDrop if boardFull == true )
    {
      boardFull = nextDrop < 0
    }
    boardFull
  }
  
  private def fourInLine(col:Int, row:Int) : Boolean = {
    val value: Disc = content(col)(row)
    
    fourInLine(horizontal(col, row), value) || fourInLine(vertical(col, row), value) ||
    fourInLine(diagonal1(col, row), value) || fourInLine(diagonal2(col, row), value)    
    
  } 
   
  
  private def fourInLine(locations:Array[(Int, Int)], value:Disc):Boolean = {
    
    var nrInALine = 0
    for((col, row) <- locations if nrInALine < 4) {
      if (col>=0 && col<nrOfCols && row>=0 && row<nrOfRows && content(col)(row) == value) 
        nrInALine += 1 
      else 
        nrInALine=0    
    }
    nrInALine >= 4
  }
  
  private def horizontal(col:Int, row:Int): Array[(Int,Int)] =
  {
	  Array((col-3, row), (col-2, row), (col-1, row), (col, row), (col+1, row), (col+2, row), (col+3, row))
  }
  
  private def vertical(col:Int, row:Int): Array[(Int,Int)] =
  {
	  Array((col, row-3), (col, row-2), (col, row-1), (col, row), (col, row+1), (col, row+2), (col, row+3))
  }
  
  private def diagonal1(col:Int, row:Int): Array[(Int,Int)] =
  {
	  Array((col-3, row-3), (col-2, row-2), (col-1, row-1), (col, row), (col+1, row+1), (col+2, row+2), (col+3, row+3))
  }
  
  private def diagonal2(col:Int, row:Int): Array[(Int,Int)] =
  {
	  Array((col-3, row+3), (col-2, row+2), (col-1, row+1), (col, row), (col+1, row-1), (col+2, row-2), (col+3, row-3))
  }
    
}