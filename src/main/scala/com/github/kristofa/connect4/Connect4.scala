package com.github.kristofa.connect4

/**
 * Main app. Entry point for application.
 */
object Connect4 extends App {
  
  val boardNrOfCols = 7
  val boardNrOfRows = 6
  val computerAlgoDepth = 7
  
  val board = new Board(boardNrOfCols, boardNrOfRows)
  val player1 = new ComputerPlayer("Computer", Disc.Player1, computerAlgoDepth, new DefaultEvaluation)
  val player2 = new HumanPlayer("Human", Disc.Player2)
  
  while(board.getBoardState == BoardState.GameInProgress)
  {
     val choicePlayer1 = player1.next(board)
     board.drop(choicePlayer1, player1.disc )
     draw(board, player1, player2)
     if (board.getBoardState == BoardState.GameInProgress )
     {
       val choicePlayer2 = player2.next(board)
       board.drop(choicePlayer2, player2.disc )
       draw(board, player1, player2)
     }
  }
  
  if (board.getBoardState == BoardState.FourInALine) {
    println("Four in a line")
    val winningPlayer = if (board.getWinningDisc == player1.disc) { player1.name } else { player2.name}
    println("Player: " + winningPlayer + " won.")
  }
  else
  {
    println("Draw. Board is full")
  }
  
  
  def draw(board:Board, player1:Player, player2:Player) {
    
    println(player1.name+": +  "+player2.name+": -  No value: o")
    
    for(row <- 0 until board.nrOfRows)
    {
      for(col <- 0 until board.nrOfCols)
      {
    	  print(if (board.getBoardValue(col, row) == Disc.Player1) { "+" } else if (board.getBoardValue(col, row) == Disc.Player2) { "-" } else "o")
      }
      println()
    }
  }
  

}