# Coursework Description: Sliding Puzzles

In this coursework, you are supposed to check whether a given directed graph is acyclic.

For example, for this graph:

![Cyclic Graph](./ss1.png)

The answer would be **“no”** since there is a cycle `b -> e -> c -> b`.

On the other hand, if the edge between **b** and **c** was reversed:

![Acyclic Graph](./ss2.png)

The answer would now be **“yes”** since there is no cycle.

---

## The Sink Elimination Algorithm

A **sink** in a directed graph is a vertex with no outgoing edges, like vertex **f** in the examples above.

An acyclic graph will always have a sink (can you figure out why this is true?).  
Of course, the converse is not true: a graph with a sink is not necessarily acyclic (the first graph above is a counterexample).

But we can use sinks as the basis of an algorithm using this idea:

- If a graph is acyclic, it has a sink.
- Also, removing that sink gives us again an acyclic graph (since any cycles would already have existed in the original graph), so there is again a sink, and we can repeat this until the graph is empty.

### Algorithm Steps:

1. If the graph is empty, return **“yes”** (represented by a suitable value like `true` or `1`).
2. If the graph has **no sink**, return **“no”** (represented by a suitable value like `false` or `0`).
3. Otherwise, **remove a sink** from the graph and repeat.

---

### Example:

- Starting from the **first graph**, we would first remove `f` and then return **“no”** since there is now no sink.
- For the **second graph**, we would remove `f`, `c`, `e`, `d`, `b`, `a` in this order and then return **“yes”** because there is nothing left.
