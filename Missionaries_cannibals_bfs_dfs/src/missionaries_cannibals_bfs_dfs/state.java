package missionaries_cannibals_bfs_dfs;

public class state {
    public bank bank1;
    public bank bank2;
    public state parent_state;
    public boat b;
    
    public state(bank bank1, bank bank2, boat boat){
        this.bank1 = bank1;
        this.bank2 = bank2;
        parent_state = null;
        b = boat;
    }
    
    @Override
    public String toString() { 
        if(bank1.isBoathere){
            return String.format(bank1.toString() + "\t <-- " + b.toString() + " \t" + bank2.toString());
        }
        else{
            return String.format(bank1.toString() + "\t " + b.toString() + " --> \t" + bank2.toString());
        }
    }
}
