# forest-fire-exercise

An exercise on the simulation of the propagation of a forest fire in Java.

An $l$ by $h$ discrete field represents a forest, and each slot can have three states:
- base state: living tree
- on fire
- ashes

Initially, all slots are on the base state, except for a subset of the slots, which are put on fire.

Then, at each time step, the simulation changes a slot on fire into a slot of ashes.
Also, it propagates the fire:
with a probability of $p$, each one of the four adjacent slots can transition from the base state into a slot on fire (slots in ashes do not catch on fire again).

The simulation halts when there are no more slots on fire.

## Inputs

The inputs are $h$, $l$ and $p$, as well as a list of the initial fire positions.
The data is stored in a JSON file.

## Outputs

A rendering of the $h$ by $l$ forest field that updates with each time step.

---

## Approach

Let us store the forest field data in an integer table initialized at 0 everywhere.
This avoids creating $h \times l$ objects in order to be more memory-efficient.
Let us also define that 0 represents the base-state of a living tree, while 1 represents a fire and 2 represents ashes.

The simulation should start by checking the validity of the input data: $l$ and $h$ are positive integers, $p$ is a value between 0 and 1, and the initial positions of fire cells should be a list of positive integer pairs ($x$, $y$) where $0 < x \leq l$ and $0 < y \leq h$.

Then, at each time-step, the simulation should:
1. Propagate the fire;
2. Transform fire into ashes;
3. Check if there is still fire to propagate.

### Fire propagation

At first we must determine what happens when a living tree is surrounded by more than one fire: should the probabilities $p$ pile up? Or should it be kept at $p$?

One solution is to keep a list of cells where we want the fire to propagate, and this should be adaptable to each possibility to the question that we are asking.
If we should keep the same $p$, then we simply avoid duplicates in the propagation list.
If $p$ should pile up, we intentionally duplicate or we add an effective probability factor to each element of the list.

In order to make a decision, we shall consider that the chance of a tree catching fire increases the more it is surrounded by fire.
Also, considering possible updates to the simulation that could take other factors into account that could alter the base probability $p$ (for example, wind or humidity), the solution with an effective probability is to be preferred.

Once the propagation list is complete, we can then move on to making the changes to the data-base.

### Forest update

Now the fire data is processed into a propagation list, we can turn current fire slots into ash slots.
Then, we can apply the propagation list to turn living trees into fire, applying the probability of fire catching to colapse it into a decision: if the fire propagates or not.

We can choose to keep a list of fire slots in order to avoid searching the whole table of forest slots, always being careful with data duplication and keeping a single source of truth.
Choosing to keep the list of fire slots as a local source of truth and the forest table as a register can help make the algorithm more efficient yet robust.

### Final checks

Once the forest table is updated, we can check if the list of fire spots is empty, and in that case we can proceed to halting the algorithm.
Otherwise, we repeat based on the new list of fire slots.

### Graphical interface

To decorelate the interface from the core algorithm, we can simply put some flags during the processing in order to trigger a GUI update.
The GUI, developed in a separate class, shall always look for the data in the register (forest table) to show the results of the simulation.

## The algorithm

1. Import data from input file
2. Check data validity
3. Create forest table - encapsulated in an object with precise interfaces
4. Create fire list - encapsulated in an object
5. Create propagation list - encapsulated in an object
6. Begin loop
    1. Create propagation list based on fire list
    2. Change register turning fire into ash based on fire list
    3. Recreate fire list based on propagation list and register
    4. Register fire positions
    5. Raise flag: updated forest
    6. Check if fire list is empty: in that case, exit loop