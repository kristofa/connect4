package com.github.kristofa.connect4


object GridStates {

  sealed abstract class GridState

  case object GameInProgress extends GridState
  case object Full extends GridState
  case object FourInALine extends GridState

}
