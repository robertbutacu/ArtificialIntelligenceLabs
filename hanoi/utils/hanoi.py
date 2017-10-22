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


def initialize(number_of_towers, number_of_pieces):
    return [number_of_towers] + list(map(lambda x: 1, range(number_of_pieces)))


def transition(state, piece, peg):
    state[piece] = peg
    return state



