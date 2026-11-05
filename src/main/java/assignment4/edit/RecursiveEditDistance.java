package assignment4.edit;

/** State is the distance of prefixes. Matching last characters transitions diagonally; otherwise take one plus the minimum of insert, delete, replace. Empty-prefix distance is its length. */
public final class RecursiveEditDistance implements EditDistance {
    private long recursiveCalls;
    @Override public int distance(String a, String b) { recursiveCalls = 0; return solve(a, b, a.length(), b.length()); }
    private int solve(String a, String b, int i, int j) {
        recursiveCalls++;
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) return solve(a, b, i - 1, j - 1);
        return 1 + Math.min(solve(a,b,i,j-1), Math.min(solve(a,b,i-1,j), solve(a,b,i-1,j-1)));
    }
    public long getRecursiveCalls() { return recursiveCalls; }
}
