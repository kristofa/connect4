package com.github.kristofa.connect4

import org.junit._
import Assert._

import Disc._

class BetterEvaluationTest {
  
  object Eval extends BetterEvaluation {
    
  }
  
  private val nrOfCols = 7
  private val nrOfRows = 6
  
  var board : Board = _
  var player : Player = _
  
  @Before
  def setup
  {
	  board = new Board(nrOfCols, nrOfRows)
	  player = new HumanPlayer("human", Disc.Player1)
  }
 
  
  @Test
  def evaluateEmptyBoard() {
	 
	  assertEquals(0, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateMostOptimalFirstMove() {
	 
    board.drop(3, player.disc)
	assertEquals(19, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateFirstColumnFirstMove() {
	 
    board.drop(0, player.disc)
	assertEquals(12, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateSecondColumnFirstMove() {
	 
    board.drop(1, player.disc)
	assertEquals(13, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateThirdColumnFirstMove() {
	 
    board.drop(2, player.disc)
	assertEquals(14, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateFifthColumnFirstMove() {
	 
    board.drop(4, player.disc)
	assertEquals(14, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateSixthColumnFirstMove() {
	 
    board.drop(5, player.disc)
	assertEquals(13, Eval.evaluate(board, player))
	  
  }
  
  @Test
  def evaluateLastColumnFirstMove() {
	 
    board.drop(6, player.disc)
	assertEquals(12, Eval.evaluate(board, player))
	  
  }


}