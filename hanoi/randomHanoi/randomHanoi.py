from copy import copy

from utils.hanoi import *
from random import randint


def is_cycle(visited_states, state):
    return state in visited_states


def start_random(initial_state):
    # print("Initial State: " + str(initial_state))

    visited_states = list()
    visited_states.append(copy(initial_state))

    state = copy(initial_state)
    count = 0
    total_count = 0
    while not is_final_state(state):
        if count < 100:
            # randomize them
            random_piece = randint(1, len(initial_state) - 1)
            random_peg = randint(1, initial_state[0])

            # if valid state->make transition, add to visited_states and print
            if is_valid(state, random_piece, random_peg) and not is_cycle(visited_states,
                                                                          transition(state, random_piece, random_peg)):
                state = transition(state, random_piece, random_peg)
                visited_states.append(copy(state))
                # print("Transition State: " + str(state))
            else:
                count += 1
        else:
            total_count += count
            count = 0
            # print("RESET")
            del state
            state = copy(initial_state)
            del visited_states[:]
            visited_states.append(copy(initial_state))
            # print("Initial State: " + str(state))

    total_count += count
    # print("DONE")

    return count, total_count
