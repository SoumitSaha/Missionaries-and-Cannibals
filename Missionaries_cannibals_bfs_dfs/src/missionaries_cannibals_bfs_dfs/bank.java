package missionaries_cannibals_bfs_dfs;

public class bank {
    public int missionaries_count;
    public int cannibals_count;
    public int bank;
    Boolean isBoathere;
    
    public bank(int missionaries, int cannibals, int bank, Boolean isBoathere){
        this.bank = bank;
        this.cannibals_count = cannibals;
        this.missionaries_count = missionaries;
        this.isBoathere = isBoathere;
    }
    
    @Override
    public String toString() { 
        return String.format("Bank " + bank + "(m" + missionaries_count +", c" + cannibals_count + ")"); 
    }
}
