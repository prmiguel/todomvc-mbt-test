# TodoMVC Model-Based Testing (MBT)
This project demonstrates **Model-Based Testing (MBT)** applied to the classic [TodoMVC React application](https://todomvc.com/examples/react/dist), using **GraphWalker**, **Java**, **Maven**, **Selenide**.

The repository contains the MBT model definition, generated Java class, and automated simulation to verify navigation flows and behaviors across the TodoMVC states.

---

## 📋 Project Overview

| Component | Description |
|------------|--------------|
| **Application Under Test (AUT)** | [TodoMVC React App](https://todomvc.com/examples/react/dist) |
| **Testing Technique** | Model-Based Testing (MBT) |
| **Tool** | [GraphWalker](https://graphwalker.github.io/) + [Selenide](https://github.com/selenide/selenide) |
| **Language / Build** | Java + Maven |
| **Model File** | [`TodoMvcModel.json`](src/main/resources/mbt/todomvc/models/TodoMvcModel.json) |
| **Report** | HTML simulation report generated after execution |

---

## 🔍 Exploratory Testing

Performed manual exploratory testing on the TodoMVC React app to understand its functional flow, identify state transitions, and collect test ideas for MBT modeling.

Key behaviors explored:
- Adding, updating, and deleting TODOs.
- Filtering between **All**, **Active**, and **Completed** lists.
- Clearing completed tasks.
- Edge cases such as having an empty list, a single TODO, or toggling items between active and completed.

🎬 **GIF Demonstration (Exploratory Testing)**  
<img src="https://prmiguel.github.io/media/a54be40d-42e4-4d3b-9f4e-736df2aeb1f8.gif" width="500" height="400" />

---

## 🧠 MBT Model (GraphWalker)

Using GraphWalker Studio, a state-transition model was created representing user navigation paths and system behavior based on the TodoMVC application logic.

The model defines **states (vertices)** and **actions (edges)** to represent transitions between different UI screens and internal application states.

🎬 **GIF Demonstration (Model Simulation)**  
<img src="https://prmiguel.github.io/media/c94d99c6-5963-4428-9c75-1a6f6796b90f.gif" width="500" height="400" />

---

### 🧱 Model Vertices (Nodes)

| Vertex Name | Description |
|--------------|--------------|
| **v_EmptyList** | Initial state of the app with no TODO items. Initializes counters `active = 0`, `complete = 0`. |
| **v_AllTodos** | State showing all TODO items (active and completed). |
| **v_ActiveTodos** | Filtered view showing only active (incomplete) TODOs. |
| **v_CompletedTodos** | Filtered view showing only completed TODOs. |

---

### 🔁 Model Edges (Transitions)

| Edge | From → To | Guard | Actions | Description |
|------|------------|--------|----------|--------------|
| **e_addTodo** | v_EmptyList → v_AllTodos | — | `active++` | Add the first TODO item. |
| **e_addAnotherTodo** | v_AllTodos → v_AllTodos | — | `active++` | Add another TODO while others exist. |
| **e_updateExistingTodo** | v_AllTodos → v_AllTodos | `active >= 1` | — | Edit an existing active TODO. |
| **e_deleteActiveTodo** | v_AllTodos → v_AllTodos | `active >= 2` | `active--` | Delete one active TODO (more than one exists). |
| **e_deleteCompletedTodo** | v_AllTodos → v_AllTodos | `complete >= 2` | `complete--` | Delete one completed TODO (more than one exists). |
| **e_deleteUniqueTodo** | v_AllTodos → v_EmptyList | `(active == 1 && complete == 0) OR (active == 0 && complete == 1)` | `active=0; complete=0;` | Delete the last remaining TODO (returns to empty state). |
| **e_seeActiveTodos** | v_AllTodos → v_ActiveTodos | `active >= 1` | — | Switch view to active TODOs. |
| **e_seeCompletedTodos** | v_AllTodos → v_CompletedTodos | `complete >= 1` | — | Switch view to completed TODOs. |
| **e_seeActiveTodos** | v_CompletedTodos → v_ActiveTodos | `active >= 1` | — | Switch from completed to active view. |
| **e_seeCompletedTodos** | v_ActiveTodos → v_CompletedTodos | `complete >= 1` | — | Switch from active to completed view. |
| **e_seeAllTodos** | v_ActiveTodos → v_AllTodos | — | — | Return to all TODOs from active view. |
| **e_seeAllTodos** | v_CompletedTodos → v_AllTodos | — | — | Return to all TODOs from completed view. |
| **e_completeTodo** | v_ActiveTodos → v_ActiveTodos | `active >= 1` | `complete++; active--;` | Mark an active TODO as completed. |
| **e_uncompleteTodo** | v_CompletedTodos → v_CompletedTodos | `complete >= 1` | `complete--; active++;` | Mark a completed TODO back as active. |
| **e_clearCompletedTodos** | v_CompletedTodos → v_CompletedTodos | `complete >= 1 && active >= 1` | `complete = 0;` | Clear all completed TODOs while active ones remain. |
| **e_clearCompletedTodos** | v_CompletedTodos → v_EmptyList | `active == 0 && complete >= 1` | `active = 0; complete = 0;` | Clear all completed TODOs when list has no active items. |

---

## 🧩 MBT Implementation

After modeling the system using GraphWalker, the model was transformed into executable Java code and integrated with **Selenide** to automate browser interactions.

### Steps:

1. **Generate Java Model from GraphWalker JSON**
   - Use GraphWalker Maven plugin to generate a base Java interface.
   - This generates a `TodoMvcModel.java` file containing all vertices and edges as methods.

2. **Implement Actions and Assertions using Selenide**
   - Each **vertex** corresponds to an assertion (checking the UI state).
   - Each **edge** corresponds to an action (simulating user behavior).

3. **Create GraphWalker Test Execution**
   - Define test runner to execute the model and verify transitions automatically.

4. **Run Tests**
   - Run the simulation to cover all transitions (100% edge coverage)

🎬 **GIF Demonstration (MBT Implementation & Execution)**  
<img src="https://prmiguel.github.io/media/a3921156-b232-473e-a4cd-3751e412d91b.gif" width="500" height="400" />

---
## 🧾 HTML Report

After running the GraphWalker simulation, an **HTML report** is generated summarizing:
- Execution paths  
- Passed/failed transitions  
- Coverage of nodes and edges  
- Random edge coverage goal (100%)

🎬 GIF Demonstration (HTML Report)
(Place your final report visualization here)

---
## 🧩 Summary

This project demonstrates how Model-Based Testing can:

- Improve test coverage via model simulation.
- Automate UI verification with Selenide.
- Generate reproducible reports and traceability.
- Connect exploratory findings with systematic automated testing.
  
