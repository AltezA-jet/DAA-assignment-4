package assignment4.edit;

/** State dp[i][j] is the distance between prefixes of lengths i and j. Each interior cell takes the matching diagonal or one plus the minimum of three neighbors; row/column zero are empty-prefix base cases. */
public final class TableEditDistance implements EditDistance {
    private long dpCellsComputed; private long dpStorageCells;
    @Override public int distance(String a, String b) {
        int n=a.length(), m=b.length(); int[][] dp=new int[n+1][m+1];
        dpStorageCells=(long)(n+1)*(m+1); dpCellsComputed=0;
        for(int i=0;i<=n;i++) dp[i][0]=i; for(int j=0;j<=m;j++) dp[0][j]=j;
        for(int i=1;i<=n;i++) for(int j=1;j<=m;j++) { int cost=a.charAt(i-1)==b.charAt(j-1)?0:1; dp[i][j]=Math.min(dp[i-1][j-1]+cost,Math.min(dp[i-1][j]+1,dp[i][j-1]+1)); dpCellsComputed++; }
        return dp[n][m];
    }
    public long getDpCellsComputed(){return dpCellsComputed;} public long getDpStorageCells(){return dpStorageCells;}
}
