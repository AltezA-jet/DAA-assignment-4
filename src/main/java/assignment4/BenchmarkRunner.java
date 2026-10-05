package assignment4;
import assignment4.edit.*; import assignment4.search.*; import java.io.*; import java.nio.file.*; import java.util.*;

/** Reproducible benchmark: counters are collected in one call and timing uses the same algorithm without reading counters. */
public final class BenchmarkRunner {
 private static volatile long checksum;
 private record Case(String algorithm,String input,int n,int m,String a,String b){}
 public static void main(String[] args)throws Exception{Path out=Path.of(args.length>0?args[0]:"results/results.csv");Files.createDirectories(out.getParent());List<Case> cases=new ArrayList<>();
  for(int n:new int[]{2,4,6,8})for(String type:new String[]{"identical_best","disjoint_worst","seeded_random"}){String[] x=editInput(n,type);cases.add(new Case("recursive_edit",type,n,n,x[0],x[1]));}
  for(int n:new int[]{64,128,256,512})for(String type:new String[]{"identical_best","disjoint_worst","seeded_random"}){String[] x=editInput(n,type);cases.add(new Case("table_edit",type,n,n,x[0],x[1]));cases.add(new Case("two_row_edit",type,n,n,x[0],x[1]));}
  for(int n:new int[]{1000,2000,4000,8000,16000})for(String type:new String[]{"first_char_mismatch","repeated_prefix_worst","seeded_random"}){String[] x=searchInput(n,type);cases.add(new Case("naive_search",type,n,x[1].length(),x[0],x[1]));cases.add(new Case("kmp_search",type,n,x[1].length(),x[0],x[1]));}
  try(BufferedWriter w=Files.newBufferedWriter(out)){w.write("algorithm,input,n,m,run,repetitions,time_ms,recursiveCalls,dpCellsComputed,dpStorageCells,searchComparisons,lpsComparisons,totalComparisons\n");for(Case c:cases)run(c,w);}
 }
 static void run(Case c,Writer w)throws IOException{long[] metrics=metrics(c);int reps=calibrate(c);for(int i=0;i<3;i++)execute(c,reps);for(int run=1;run<=5;run++){long start=System.nanoTime();execute(c,reps);double ms=(System.nanoTime()-start)/1_000_000.0/reps;w.write(String.format(Locale.ROOT,"%s,%s,%d,%d,%d,%d,%.6f,%s,%s,%s,%s,%s,%s\n",c.algorithm,c.input,c.n,c.m,run,reps,ms,fmt(metrics,0),fmt(metrics,1),fmt(metrics,2),fmt(metrics,3),fmt(metrics,4),fmt(metrics,5)));}}
 static String fmt(long[]a,int i){return a[i]<0?"":Long.toString(a[i]);}
 static int calibrate(Case c){int reps=1;while(reps<10000){long s=System.nanoTime();execute(c,reps);if(System.nanoTime()-s>=2_000_000)break;reps*=2;}return reps;}
 static void execute(Case c,int reps){for(int i=0;i<reps;i++){if(c.algorithm.endsWith("edit")){EditDistance e=makeEdit(c.algorithm);checksum+=e.distance(c.a,c.b);}else{StringSearch s=c.algorithm.startsWith("naive")?new NaiveSearch():new KmpSearch();checksum+=s.search(c.a,c.b).size();}}}
 static long[] metrics(Case c){long[] x={-1,-1,-1,-1,-1,-1};if(c.algorithm.equals("recursive_edit")){var e=new RecursiveEditDistance();e.distance(c.a,c.b);x[0]=e.getRecursiveCalls();}else if(c.algorithm.equals("table_edit")){var e=new TableEditDistance();e.distance(c.a,c.b);x[1]=e.getDpCellsComputed();x[2]=e.getDpStorageCells();}else if(c.algorithm.equals("two_row_edit")){var e=new TwoRowEditDistance();e.distance(c.a,c.b);x[1]=e.getDpCellsComputed();x[2]=e.getDpStorageCells();}else if(c.algorithm.equals("naive_search")){var e=new NaiveSearch();e.search(c.a,c.b);x[3]=e.getSearchComparisons();}else{var e=new KmpSearch();e.search(c.a,c.b);x[3]=e.getSearchComparisons();x[4]=e.getLpsComparisons();x[5]=e.getTotalComparisons();}return x;}
 static EditDistance makeEdit(String a){return switch(a){case "recursive_edit"->new RecursiveEditDistance();case "table_edit"->new TableEditDistance();default->new TwoRowEditDistance();};}
 static String[] editInput(int n,String t){if(t.equals("identical_best"))return new String[]{"A".repeat(n),"A".repeat(n)};if(t.equals("disjoint_worst"))return new String[]{"A".repeat(n),"B".repeat(n)};Random r=new Random(991+n);return new String[]{random(r,n),random(r,n)};}
 static String[] searchInput(int n,String t){int m=n/4;if(t.equals("first_char_mismatch"))return new String[]{"A".repeat(n),"B"+"A".repeat(m-1)};if(t.equals("repeated_prefix_worst"))return new String[]{"A".repeat(n),"A".repeat(m-1)+"B"};Random r=new Random(2025L+n);return new String[]{random(r,n),random(r,m)};}
 static String random(Random r,int n){StringBuilder s=new StringBuilder(n);for(int i=0;i<n;i++)s.append((char)('A'+r.nextInt(4)));return s.toString();}
}
