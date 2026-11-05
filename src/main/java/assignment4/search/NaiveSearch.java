package assignment4.search;
import java.util.*;
public final class NaiveSearch implements StringSearch {
    private long searchComparisons;
    @Override public List<Integer> search(String text,String pattern){ if(pattern.isEmpty()) throw new IllegalArgumentException("Pattern must not be empty"); searchComparisons=0; List<Integer> out=new ArrayList<>(); if(pattern.length()>text.length())return out; for(int i=0;i<=text.length()-pattern.length();i++){int j=0;while(j<pattern.length()){searchComparisons++;if(text.charAt(i+j)!=pattern.charAt(j))break;j++;}if(j==pattern.length())out.add(i);}return out; }
    public long getSearchComparisons(){return searchComparisons;}
}
