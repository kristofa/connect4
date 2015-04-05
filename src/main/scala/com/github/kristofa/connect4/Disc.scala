package com.github.kristofa.connect4

/**
 * Represents the different 'discs' that can be dropped in the board.
 */
object Discs {

  sealed abstract class Disc(val asciiRepresentation:Char)

  case object Player1 extends Disc('+')
  case object Player2 extends Disc('-')

}