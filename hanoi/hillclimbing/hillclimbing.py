from copy import copy

from utils.hanoi import *
from random import randint


def is_cycle(visited_states, state):
    return state in visited_states


def score(state):
    return sum(state)


def all_possible_moves(state):
    moves = []
    for piece in range(1, len(state)):
        for peg in range(1, state[0] + 1):
            if is_valid(copy(state), piece, peg):
                moves.append(transition(copy(state), piece, peg))
    return moves


def scores_for_possible_moves(moves):
    scores = []
    for state in moves:
        scores.append(score(state))
    return scores


def index_for_better_score(danger, current_state, moves):
    scores = scores_for_possible_moves(moves)
    current_score = score(current_state)
    better_scores_indexes = []

    while (len(better_scores_indexes) == 0):
        for i in range(0, len(scores)):
            if (scores[i] >= current_score - danger):
                better_scores_indexes.append(i)
        danger += 1

    random_number = randint(0, len(better_scores_indexes) - 1)
    return better_scores_indexes[random_number]


def best_move(state, danger):
    moves = all_possible_moves(state)
    index = index_for_better_score(danger, state, moves)
    return moves[index]


def start_hill_climbing(initial_state):
    # print("Initial State: " + str(initial_state))
    count = 0
    total_count = 0
    matrix = list()
    matrix.append(copy(initial_state))
    state = copy(initial_state)
    while not is_final_state(state):
        danger = 0
        temp = best_move(state, danger)
        while temp in matrix and danger < 5:
            danger = danger + 1
            temp = best_move(state, danger)
            total_count += 1
        else:
            state = best_move(state, danger)
            count += 1
        # print("Best Move is "+str(state))
        matrix.append(copy(temp))
        # else:
        # print("DONE")

    total_count += count
    return count, total_count
