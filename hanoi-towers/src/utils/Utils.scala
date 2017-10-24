package utils

object Utils {
  """
A hanoi tower problem has been solved if all pieces are on the final tower.

A game is represented as a list where :
1. the first element represents the number of pegs
2. the rest of the list represents the pieces, and their respective value the peg they sit on.
"""

  def isFinalState(state: List[Int]): Boolean = state.forall(_ == state.head)

  """
The game is initialized with a list where the first element represents the number of pegs,
and the rest of the list - the pieces that are by default positioned on the first peg.
"""

  def initialize(numberOfPegs: Int, numberOfPieces: Int): List[Int] =
    List(numberOfPegs) ::: List.fill(numberOfPieces)(1)

  def transition(state: List[Int], piece: Int, peg: Int): List[Int] =
    state.updated(piece, peg)

  /*
  def is_valid(state, piece_index, new_peg):
  # don't sit on itself, it's already there
  if state[piece_index] == new_peg:
  return False

  # no piece should be on top of the piece wanted to be moved
  # aka all pieces should be on a different peg up to the current piece
  if not all(state[piece_index] != state[x] for x in range(1, piece_index)):
  return False

  # there isn't another piece of lower weight already on the new peg
  if list(filter(lambda x: state[x] == new_peg, range(1, piece_index))):
  return False

  # all goochy
  return True
  */
}
