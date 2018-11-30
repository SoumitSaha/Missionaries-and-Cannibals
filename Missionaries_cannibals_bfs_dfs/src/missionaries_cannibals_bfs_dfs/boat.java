package missionaries_cannibals_bfs_dfs;

public class boat {
    
    int missionaries;
    int cannibals;
    int capacity;
    
    public boat(int missionaries, int cannibals, int capacity){
        this.cannibals = cannibals;
        this.missionaries = missionaries;
        this.capacity = capacity;
    }
    
    @Override
    public String toString() { 
        return String.format("Boat (m" + missionaries +", c" + cannibals + ")");
    }
}
