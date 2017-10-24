from hanoi.utils.hanoi import *
import time


def start_backtracking(pegs, pieces):
    def go(state, current_piece_index, previous_states):
        if is_final_state(state):
            print("Found state! " + str(state))

        for peg in range(1, state[0] + 1):
            if is_valid(state, current_piece_index, peg):
                new_state = transition(state, current_piece_index, peg)
                print("Current state " + str(new_state))
                print("Current piece index " + str(current_piece_index))
                print("Previous states " + str(previous_states))
                time.sleep(2)
                for x in range(1, new_state[0] + 1):
                    if x != current_piece_index:
                        return go(new_state, x, previous_states +  [new_state])

    initial_state = initialize(pegs, pieces)
    return go(initial_state, 1, [])


print(start_backtracking(3, 3))

