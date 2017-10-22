"""
A hanoi tower problem has been solved if all pieces are on the final tower.

A game is represented as a list where :
1. the first element represents the number of towers
2. the rest of the list represents the pieces, and their respective value the tower they sit on.
"""


def is_final_state(state):
    return all(x == state[0] for x in state[1:])

