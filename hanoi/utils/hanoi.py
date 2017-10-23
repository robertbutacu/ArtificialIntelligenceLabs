"""
A hanoi tower problem has been solved if all pieces are on the final tower.

A game is represented as a list where :
1. the first element represents the number of towers
2. the rest of the list represents the pieces, and their respective value the tower they sit on.
"""


def is_final_state(state):
    return all(x == state[0] for x in state[1:])


"""
The game is initialized with a list where the first element represents the number of towers,
and the rest of the list - the pieces that are by default positioned on the first peg.
"""


def initialize(number_of_pegs, number_of_pieces):
    return [number_of_pegs] + list(map(lambda x: 1, range(number_of_pieces)))


def transition(state, piece, peg):
    state[piece] = peg
    return state


def is_valid(state, piece_index, new_peg):
    only_pieces = state[1:]

    # don't sit on itself, it's already there
    if only_pieces[piece_index] == new_peg:
        print("Already there")
        return False

    # no piece should be on top of the piece wanted to be moved
    # aka all pieces should be on a different peg up to the current piece
    if not all(only_pieces[piece_index] != x for x in range(0, piece_index)):
        return False

    # there isn't another piece of lower weight already on the new peg
    if not all(new_peg == x for x in range(0, piece_index)):
        return False

    # all goochy
    return True
