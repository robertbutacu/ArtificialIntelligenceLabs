from copy import copy

from utils.hanoi import *
from randomHanoi.randomHanoi import start_random
from hillclimbing.hillclimbing import start_hill_climbing
import timeit

#  get user input
number_of_pegs = int(input("Number of pegs: "))
number_of_pieces = int(input("Number of pieces: "))

# get and print initial state
initial_state = initialize(number_of_pegs, number_of_pieces)

states_to_solution=[]
states_tested=[]

def random_algorithm():
    global states_to_solution
    global states_tested
    for i in range(99):
        ss,st=start_random(initial_state)
        states_to_solution.append(copy(ss))
        states_tested.append(copy(st))

start=timeit.default_timer()
random_algorithm()
end=timeit.default_timer()

time_spent=(end-start)/100
average_states_to_solutions=sum(states_to_solution)//len(states_to_solution)
average_states_tested=sum(states_tested)//len(states_tested)

print("Random Algorithm : average time = "+str(time_spent)+", average states to solution = "+str(average_states_to_solutions)+
                        ",average states tested = "+str(average_states_tested))






def hillclimbing_algorithm():
    global states_to_solution
    global states_tested
    for i in range(99):
        ss,st=start_hill_climbing(initial_state)
        states_to_solution.append(copy(ss))
        states_tested.append(copy(st))



start=timeit.default_timer()
hillclimbing_algorithm()
end=timeit.default_timer()


time_spent=(end-start)/100
average_states_to_solutions=sum(states_to_solution)//len(states_to_solution)
average_states_tested=sum(states_tested)//len(states_tested)

print("HillClimbing Algorithm : average time = "+str(time_spent)+", average states to solution = "+str(average_states_to_solutions)+
                        ",average states tested = "+str(average_states_tested))