package assignment4.edit;

/** State is the previous and current DP row. The same three-neighbor transition is used; row zero and column zero initialize empty prefixes. The shorter string is the column dimension. */
public final class TwoRowEditDistance implements EditDistance {
    private long dpCellsComputed; private long dpStorageCells;
    @Override public int distance(String a, String b) {
        if(a.length()<b.length()){String t=a;a=b;b=t;} int n=a.length(),m=b.length();
        int[] previous=new int[m+1], current=new int[m+1]; dpStorageCells=previous.length+current.length; dpCellsComputed=0;
        for(int j=0;j<=m;j++) previous[j]=j;
        for(int i=1;i<=n;i++){ current[0]=i; for(int j=1;j<=m;j++){int cost=a.charAt(i-1)==b.charAt(j-1)?0:1; current[j]=Math.min(previous[j-1]+cost,Math.min(previous[j]+1,current[j-1]+1));dpCellsComputed++;} int[] t=previous;previous=current;current=t; }
        return previous[m];
    }
    public long getDpCellsComputed(){return dpCellsComputed;} public long getDpStorageCells(){return dpStorageCells;}
}
