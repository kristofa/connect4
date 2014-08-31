package com.github.kristofa.connect4

import org.junit._
import Assert._

@Test
class BoardTest {
  
  private val nrOfCols = 7
  private val nrOfRows = 6
  
  var testBoard : Board = _
  
  @Before
  def setup
  {
	  testBoard = new Board(nrOfCols, nrOfRows)
  }
  
  @Test
  def dropNotPossibleIfOutOfLowerRange = assertFalse(testBoard.dropPossible(-1))
  
  @Test
  def dropNotPossibleIfOutOfUpperRange = assertFalse(testBoard.dropPossible(nrOfCols))
  
  @Test
  def dropInvalidValue {
      try
	  {
		  testBoard.drop(1, null );
		  fail("Expected exception.")
	  }
	  catch {
	    case e: IllegalArgumentException => // expected
	  }
  }
  
  @Test
  def dropRepetitiveTest {
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player1 )
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player2 )
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player1 )
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player2 )
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player1 )
	  assertTrue(testBoard.dropPossible(0))
	  testBoard.drop(0, Disc.Player2 )
	  
	  assertFalse("Expect column to be full.", testBoard.dropPossible(0))
	  
	  try
	  {
	  testBoard.drop(0, Disc.Player1 )
	  fail("Expected exception");
	  }
	  catch {
	      case e : IllegalStateException => // Expected 
	  }
	  
  }
  
  @Test
  def nrOfDiscsTest {
	  assertEquals(0, testBoard.getNrOfDiscs)
	  testBoard.drop(0, Disc.Player1 )
	  assertEquals(1, testBoard.getNrOfDiscs)
	  testBoard.drop(0, Disc.Player2 )
	  assertEquals(2, testBoard.getNrOfDiscs)
  }
  
  /**
   * Creates 4 in a line horizontal:
   * 
   * ++++
   * 
   */
  @Test
  def dropHorizontalFourInALine {
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(1, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player1 ))
	  assertEquals(BoardState.FourInALine, testBoard.drop(3, Disc.Player1 ))
	  assertEquals(Disc.Player1 , testBoard.getWinningDisc);
	 
	  try
	  {
	    // Dropping disc when game is not 'in progress' should not be allowed.
	    testBoard.drop(4, Disc.Player2 )
	  }
	  catch {
	    case e : IllegalStateException => // Expected.
	  }
	  
  }
  
  /**
   * Creates 4 in a line vertical:
   * 
   * +
   * +
   * +
   * +
   */
  @Test
  def dropVerticalFourInALine {
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player2 ))
	  assertEquals(BoardState.FourInALine, testBoard.drop(2, Disc.Player2 ))
	  assertEquals(Disc.Player2 , testBoard.getWinningDisc)
	  try
	  {
	    // Dropping disc when game is not 'in progress' should not be allowed.
	    testBoard.drop(2, Disc.Player1 )
	  }
	  catch {
	    case e : IllegalStateException => // Expected.
	  }
  }
  
  /**
   * Creates 4 in a line diagonal
   * 
   * +
   * -
   * +-+
   * -+-
   * +-+-
   * 
   */
  @Test
  def dropFourInALineDiagonalTopBottom {
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  
	  assertEquals(BoardState.GameInProgress, testBoard.drop(1, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(1, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(1, Disc.Player2 ))
	   
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player1 )) 
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player2 )) 
	  assertEquals(BoardState.GameInProgress, testBoard.drop(2, Disc.Player1 )) 
	  
	  assertEquals(BoardState.FourInALine, testBoard.drop(3, Disc.Player2 ))
	  assertEquals(Disc.Player2 , testBoard.getWinningDisc)
	  
  }
  
   /**
   * Creates 4 in a line diagonal
   * 
   *    +
   *    -
   *  +-+
   *  -+-
   * -+-+
   * 
   */
  @Test
  def dropFourInALineDiagonalBottomTop {
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player1 ))
	  
	  assertEquals(BoardState.GameInProgress, testBoard.drop(5, Disc.Player2 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(5, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(5, Disc.Player2 ))
	   
	  assertEquals(BoardState.GameInProgress, testBoard.drop(4, Disc.Player1 )) 
	  assertEquals(BoardState.GameInProgress, testBoard.drop(4, Disc.Player2 )) 
	  assertEquals(BoardState.GameInProgress, testBoard.drop(4, Disc.Player1 )) 
	  
	  assertEquals(BoardState.FourInALine, testBoard.drop(3, Disc.Player2 ))
	  assertEquals(Disc.Player2 , testBoard.getWinningDisc)
  }

  @Test
  def getBoardValueEmptyBoard() {
	  for(col <- 0 until nrOfCols) {
	    for (row <- 0 until nrOfRows ) {
	      assertNull(testBoard.getBoardValue(col, row))
	    }
	  }
  }
  
  @Test
  def getBoardValueAfterDrop() {
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player2 ))

	  assertEquals(Disc.Player1, testBoard.getBoardValue(0, 5))
	  assertNull(testBoard.getBoardValue(0, 4))
	  assertNull(testBoard.getBoardValue(1, 5))
	  
	  assertEquals(Disc.Player2, testBoard.getBoardValue(6, 5))
	  assertNull(testBoard.getBoardValue(6, 4))
	  assertNull(testBoard.getBoardValue(5, 5)) 
	  
  }
  
  @Test
  def copyConstructor() {
	      
	  assertEquals(BoardState.GameInProgress, testBoard.drop(0, Disc.Player1 ))
	  assertEquals(BoardState.GameInProgress, testBoard.drop(6, Disc.Player2 ))

	  val boardCopy = new Board(testBoard)
	  
	  assertEquals(Disc.Player1, boardCopy.getBoardValue(0, 5))
	  assertNull(boardCopy.getBoardValue(0, 4))
	  assertNull(boardCopy.getBoardValue(1, 5))
	  
	  assertEquals(Disc.Player2, testBoard.getBoardValue(6, 5))
	  assertNull(boardCopy.getBoardValue(6, 4))
	  assertNull(boardCopy.getBoardValue(5, 5)) 
	  
  }
  
  @Test
  def testHashCode() {
	  testBoard.drop(0, Disc.Player1 )
	  val boardCopy = new Board(testBoard)
	  
	  assertEquals(testBoard.hashCode, boardCopy.hashCode)
  }
  
  @Test
  def testEquals() {
	  assertFalse(testBoard.equals(null))
	  assertFalse(testBoard.equals(new String()))
	  assertTrue(testBoard.equals(testBoard))

	  val other = new Board(7,6)
	  other.drop(0, Disc.Player1 )
	  assertFalse(testBoard.equals(other))
	  
	  val copy = new Board(other)
	  assertTrue(other.equals(copy))
	  assertTrue(copy.equals(other))
  }
  
  @Test
  def testCellIndices() {
      val smallBoard = new Board(2, 1)
	  val indices = smallBoard.cellIndices
	  assertEquals(smallBoard.nrOfCols * smallBoard.nrOfRows, indices.length)
	  assertTrue(indices.contains((0,0)))
	  assertTrue(indices.contains((1,0)))
	  
  }
  

}