# City Search & Spell-Check

Assignment 4 for Temujin Gonchar (SE-2518), Java 17/Maven. The project implements recursive, full-table, and two-row Levenshtein distance plus Naive and KMP string search.

## Build and test
`mvn clean test`

Run the benchmark with `mvn -q package -DskipTests` followed by `java -cp target/classes assignment4.BenchmarkRunner results/results.csv`. Generate plots with `python scripts/plot_results.py`. Outputs are in `results/plots/`; analysis is in [REPORT.md](REPORT.md), with defense preparation in [DEFENSE_NOTES.md](DEFENSE_NOTES.md).

Complexities: recursive edit distance is exponential in the worst case; both DP versions take O(nm), with O(nm) and O(min(n,m)) logical storage respectively; Naive search is O((n-m+1)m) worst-case; KMP is O(n+m).

AI assistance was used during development. The implementation, tests, benchmark execution, plots, and written conclusions were checked against the repository and actual generated outputs.
