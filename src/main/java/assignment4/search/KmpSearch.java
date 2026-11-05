package assignment4.search;
import java.util.*;
public final class KmpSearch implements StringSearch {
    private long lpsComparisons, searchComparisons;
    public int[] lps(String pattern){ if(pattern.isEmpty()){lpsComparisons=0;return new int[0];} int[] l=new int[pattern.length()]; int len=0,i=1; while(i<pattern.length()){lpsComparisons++;if(pattern.charAt(i)==pattern.charAt(len)){l[i++]=++len;}else if(len>0){len=l[len-1];}else{i++;}}return l; }
    @Override public List<Integer> search(String text,String pattern){if(pattern.isEmpty())throw new IllegalArgumentException("Pattern must not be empty");lpsComparisons=0;searchComparisons=0;int[] l=lps(pattern);List<Integer> out=new ArrayList<>();int i=0,j=0;while(i<text.length()){searchComparisons++;if(text.charAt(i)==pattern.charAt(j)){i++;j++;if(j==pattern.length()){out.add(i-j);j=l[j-1];}}else if(j>0)j=l[j-1];else i++;}return out;}
    public long getLpsComparisons(){return lpsComparisons;} public long getSearchComparisons(){return searchComparisons;} public long getTotalComparisons(){return lpsComparisons+searchComparisons;}
}
