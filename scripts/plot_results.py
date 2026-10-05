import csv
from collections import defaultdict
from pathlib import Path
import matplotlib.pyplot as plt

rows=[]
with open('results/results.csv', newline='') as f:
 for r in csv.DictReader(f):
  for k in ('n','time_ms','recursiveCalls','dpCellsComputed','dpStorageCells','searchComparisons','totalComparisons'): r[k]=float(r[k]) if r[k] else None
  rows.append(r)
out=Path('results/plots'); out.mkdir(parents=True,exist_ok=True)
def series(algorithm,input_name,metric):
 d=defaultdict(list)
 for r in rows:
  if r['algorithm']==algorithm and r['input']==input_name and r[metric] is not None:d[r['n']].append(r[metric])
 return sorted((n,sorted(v)[len(v)//2]) for n,v in d.items())
def save(name,title,xlabel,ylabel): plt.title(title);plt.xlabel(xlabel);plt.ylabel(ylabel);plt.legend();plt.tight_layout();plt.savefig(out/name,dpi=150);plt.close()
for label in ['identical_best','disjoint_worst','seeded_random']:
 q=series('recursive_edit',label,'recursiveCalls');plt.plot([x for x,y in q],[y for x,y in q],marker='o',label=label)
plt.yscale('log');save('edit_recursive.png','Recursive Edit Distance: Recursion Tree Growth','Input size n','Recursive calls')
for alg in ['table_edit','two_row_edit']:
 q=series(alg,'seeded_random','dpCellsComputed');plt.plot([x for x,y in q],[y for x,y in q],marker='o',label=alg)
save('edit_dp_operations.png','Edit Distance: Dynamic Programming Operations','Input size n','Calculated DP cells')
for alg in ['table_edit','two_row_edit']:
 q=series(alg,'seeded_random','dpStorageCells');plt.plot([x for x,y in q],[y for x,y in q],marker='o',label=alg)
save('edit_memory.png','Edit Distance: DP Storage Comparison','Input size n','Logical integer storage cells')
for alg,label,metric in [('naive_search','Naive','searchComparisons'),('kmp_search','KMP','totalComparisons')]:
 for typ in ['first_char_mismatch','repeated_prefix_worst','seeded_random']:
  q=series(alg,typ,metric);plt.plot([x for x,y in q],[y for x,y in q],marker='o',label=f'{label} {typ}')
plt.yscale('log');save('string_search_comparisons.png','Naive vs KMP: Character Comparison Counts','Text size n','Total character comparisons')
for alg,label in [('naive_search','Naive'),('kmp_search','KMP')]:
 for typ in ['first_char_mismatch','repeated_prefix_worst','seeded_random']:
  q=series(alg,typ,'time_ms');plt.plot([x for x,y in q],[y for x,y in q],marker='o',label=f'{label} {typ}')
save('string_search_time.png','Naive vs KMP: Median Execution Time','Text size n','Median execution time (ms)')
