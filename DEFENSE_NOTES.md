# Defense notes

- Edit distance is the minimum insertions, deletions, and substitutions needed to transform one string into another.
- Dynamic programming stores answers for prefixes. The state is `dp[i][j]`; the transition uses diagonal match/replace, insertion, and deletion; empty prefixes have distance equal to the other prefix length.
- Plain recursion recomputes the same prefix pairs, so its branching tree can grow exponentially.
- A full table uses O(nm) memory. Two rows keep only the previous and current row, reducing logical storage to O(min(n,m)), but cannot reconstruct a full path without extra work.
- Naive search tries every alignment and compares left to right. KMP builds an LPS (longest proper prefix that is also a suffix) array and uses it after mismatch, so the text index never moves backward.
- After a match, KMP falls back through LPS, which is why overlapping matches are supported.
- `recursiveCalls`, `dpCellsComputed`, and `dpStorageCells` measure recursion, DP work, and logical array slots. Search counters measure actual character comparisons; KMP separates LPS construction from text search.
- Wall-clock time includes JVM and machine effects; operation counts are defined algorithmic evidence and are not heap bytes or milliseconds.
- This experiment demonstrated equal DP work, lower two-row storage, content-sensitive Naive search, and KMP's avoidance of repeated prefixes. The benchmark used warm-ups, five samples, batching, and median plots.
