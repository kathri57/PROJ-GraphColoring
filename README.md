# Comparison of Heuristic Algorithms for Graph Coloring with Emphasis on the MSISCA Algorithm

Source code for the bachelor's thesis *Primerjava hevrističnih algoritmov za barvanje grafov s poudarkom na algoritmu MSISCA*.

**Author:** Bojana Trivunović  
**Institution:** Faculty of Computer and Information Science, University of Ljubljana  
**Year:** 2026

This repository contains `PROJ-GraphColoring`, a project developed for the [ALGator](https://github.com/TDobravec/Algator) system to compare five heuristic graph coloring algorithms.

## Contents

- `algs/` – Implementations of five algorithms: Greedy, Welsh-Powell, DSatur, RLF, and MSISCA.
- `proj/` – Project configuration, input and output definitions, indicators (`NumColors`, `Check`), and test graph generators.
- `tests/` – Four test sets:
  - A: Classical graphs
  - B: Social network graphs
  - C: Random Erdős–Rényi graphs
  - D: Queen graphs and Mycielski graphs
- `results/` – Experimental results used in the thesis.
- `presenters/` – Result visualization configurations for ALGator.

## Running the Project

Copy the `PROJ-GraphColoring` directory into:

```text
<ALGATOR_ROOT>/data_root/projects/
```

Run the project using ALGator:

```bash
algator execute GraphColoring
```

## Requirements

- [ALGator](https://github.com/TDobravec/Algator) (tested with ALGator Execute 1.06, running in Docker).

## Results

The `results/F0.C0/` directory contains the measurements used in the bachelor's thesis (5 algorithms × 46 test cases).

Execution times (`Tmin`, `Tall`) are measured in microseconds.

Results can be viewed in the ALGator web interface at http://localhost:8081 by selecting the `GraphColoring` project.

## Notes

- Random graphs (test set C) are generated using fixed seeds (42–65), ensuring reproducibility.
- `Myciel(k)` denotes a Mycielski graph with chromatic number k (e.g., `Myciel(3)` = C5). The DIMACS file `myciel{k}` corresponds to `Myciel(k+1)`.


## License

This project is licensed under the GNU General Public License v3.0. See the `LICENSE` file for details.
