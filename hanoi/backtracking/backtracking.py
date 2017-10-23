from hanoi.utils.hanoi import *


def start_backtracking(pegs, pieces):
    def go(state, current_piece_index):
        if is_final_state(state):
            return state

        for current_peg in range(1, pegs + 1):
            if is_valid(state, current_piece_index, current_peg):
                new_state = transition(state, current_piece_index, current_peg)
                print(str(new_state) + "\n")
                for next_piece in range(1, state[0] + 1):
                    if next_piece != current_piece_index:
                        go(new_state, next_piece)

    initial_state = initialize(pegs, pieces)
    return go(initial_state, 1)


print(start_backtracking(3, 3))

