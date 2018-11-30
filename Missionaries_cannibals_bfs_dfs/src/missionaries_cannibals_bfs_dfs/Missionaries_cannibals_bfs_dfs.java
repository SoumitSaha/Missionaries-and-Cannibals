package missionaries_cannibals_bfs_dfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Missionaries_cannibals_bfs_dfs {

    public static int bfs_node_count_expanded;                              // counts total nodes expanded(popped) during bfs
    public static int bfs_node_count_explored;                              // counts total nodes explored(created) during bfs
    public static long time;                                                // time taken by bfs
    public static int dfs_node_count_expanded;                              // counts total nodes expanded(popped) during dfs
    public static int dfs_node_count_explored;                              // counts total nodes explored(created) during dfs
    public static long time2;                                               // time taken by dfs
    public static Boolean isSlovablebybfs; 
    public static Boolean isSlovablebydfs;
    public static state startbfsstate;                                      // start state during bfs
    public static state startdfsstate;                                      // start state during dfs
    
    public static String value_to_key(state st, String boatpos){            // takes a state(2 banks' missionaries 
        String str = boatpos + st.bank1.missionaries_count +                // and cannibals no) along with the shore where 
                st.bank1.cannibals_count + st.bank2.missionaries_count +    // the boat is, to generate a key for hashing
                st.bank2.cannibals_count + "";
        return str;
    }
    
    public static Boolean isValid(int m1, int c1, int m2, int c2){          
        if((m1 < 0) || (m2 < 0) ||(c1 < 0) || (c2 < 0)) return false;       // doesn't allow negative number of people in a bank
        if((m1 != 0 && (m1 >= c1)) || (m1 == 0)) {                          // determines wheather the missionaries are 
            if((m2 != 0 && (m2 >= c2)) || (m2 == 0)){                       // outnumbered
                return true;
            }
        }
        return false;
    }
    
    public static state bfs(int missionaries, int cannibals, int boat_capacity, double allowable_time){
        long init_time = System.currentTimeMillis();
        HashMap<String, state> map = new HashMap<>();
        isSlovablebybfs = false;
        bank init1 = new bank(missionaries, cannibals, 1, true);
        bank init2 = new bank(0, 0, 2, false);
        boat b = new boat(0, 0, 2);
        state init_state = new state(init1, init2, b);
        
        Queue<state> q = new LinkedList<>();
        q.add(init_state);
        startbfsstate = init_state;
        String cur_state_key = value_to_key(init_state, "bank1");
        map.put(cur_state_key, init_state);
        
        while(q.peek() != null){
            long looptime = System.currentTimeMillis();
            long runningtime = (looptime - init_time) ;
            
            if(bfs_node_count_explored == 1000000){
                System.out.println("Explored node exceeded 1000000. Searching terminated.");
                time = runningtime;
                return null;
            }
            
            if(runningtime >= allowable_time){
                System.out.println("Allowable time exceeded. Searching Terminated.");
                time = runningtime;
                return null;
            }
            
            state cur_state = q.remove();
            bank bank1 = cur_state.bank1;
            bank bank2 = cur_state.bank2;
            bfs_node_count_expanded++;
            
            if(bank1.isBoathere){                                                           // boat has reached at shore of bank1
                int m1 = bank1.missionaries_count;
                int m2 = bank2.missionaries_count;
                int c1 = bank1.cannibals_count;
                int c2 = bank2.cannibals_count;
                
                for(int i = 0; i <= boat_capacity; i++){                                     // i represents # of missionaries
                    for(int j = boat_capacity - i; j >= 0; j--){                             // j represents # of cannibals
                        if((!(i == 0 && j == 0)) && (((i != 0) && (i >= j)) || (i == 0))){   // boat is safe for missionaries
                            int new_m1 = m1 - i;
                            int new_c1 = c1 - j;
                            int new_m2 = m2 + i;
                            int new_c2 = c2 + j;
                            if(isValid(new_m1, new_c1, new_m2, new_c2)){
                                bank new_bank1 = new bank(new_m1, new_c1, 1, false);
                                bank new_bank2 = new bank(new_m2, new_c2, 2, true);
                                b = new boat(i, j, 2);
                                bfs_node_count_explored++;
                                state new_state = new state(new_bank1, new_bank2, b);
                                String new_state_key = value_to_key(new_state, "bank1");
                                if(!map.containsKey(new_state_key)){
                                    new_state.parent_state = cur_state;
                                    q.add(new_state);
                                    map.put(new_state_key, new_state);
                                }
                            }
                        }
                    }
                }
            }
            
            
            if(bank2.isBoathere){                                                               // boat has reached at shore of bank2
                int m1 = bank1.missionaries_count;
                int m2 = bank2.missionaries_count;
                int c1 = bank1.cannibals_count;
                int c2 = bank2.cannibals_count;
                if((m1 == 0) && (c1 == 0)){                                                     // checks wheather we reached our
                    isSlovablebybfs = true;                                                     // target state (bank 2 m 0 c 0)
                    looptime = System.currentTimeMillis();
                    runningtime = (looptime - init_time) ;
                    time = runningtime;
                    return cur_state;
                }
                for(int i = 0; i <= boat_capacity; i++){                                         // i represents # of missionaries
                    for(int j = boat_capacity - i; j >= 0; j--){                                 // j represents # of cannibals
                        if( (!(i == 0 && j == 0)) && (((i != 0) && (i >= j)) || (i == 0))){      // boat is safe for missionaries
                            int new_m1 = m1 + i;
                            int new_c1 = c1 + j;
                            int new_m2 = m2 - i;
                            int new_c2 = c2 - j;
                            if(isValid(new_m1, new_c1, new_m2, new_c2)){
                                bank new_bank1 = new bank(new_m1, new_c1, 1, true);
                                bank new_bank2 = new bank(new_m2, new_c2, 2, false);
                                b = new boat(i, j, 2);
                                bfs_node_count_explored++;
                                state new_state = new state(new_bank1, new_bank2, b);
                                String new_state_key = value_to_key(new_state, "bank2");
                                if(!map.containsKey(new_state_key)){
                                    new_state.parent_state = cur_state;
                                    q.add(new_state);
                                    map.put(new_state_key, new_state);
                                }
                            }
                        }
                    }
                }
            }
            
            
            
        }
        long looptime = System.currentTimeMillis();
        long runningtime = (looptime - init_time) ;
        time = runningtime;
        return null;
    }
    
    
    
    
    public static state dfs(int missionaries, int cannibals, int boat_capacity, double allowable_time){
        long init_time = System.currentTimeMillis();
        HashMap<String, state> map = new HashMap<>();
        isSlovablebydfs = false;
        bank init1 = new bank(missionaries, cannibals, 1, true);
        bank init2 = new bank(0, 0, 2, false);
        boat b = new boat(0, 0, 2);
        state init_state = new state(init1, init2, b);
        
        Stack<state> q = new Stack<>();
        q.push(init_state);
        startdfsstate = init_state;
        String cur_state_key = value_to_key(init_state, "bank1");
        map.put(cur_state_key, init_state);
        
        while(!q.empty()){
            long looptime = System.currentTimeMillis();
            long runningtime = (looptime - init_time) ;
            
            if(dfs_node_count_explored == 1000000){
                System.out.println("Node count exceeded 1000000. Searching terminated.");
                time2 = runningtime;
                return null;
            }
            
            if(runningtime >= allowable_time){
                System.out.println("Allowable time exceeded. Searching Terminated.");
                time2 = runningtime;
                return null;
            }
            
            state cur_state = q.pop();
            bank bank1 = cur_state.bank1;
            bank bank2 = cur_state.bank2;
            dfs_node_count_expanded++;
            
            if(bank1.isBoathere){                                                           // boat has reached at shore of bank1
                int m1 = bank1.missionaries_count;
                int m2 = bank2.missionaries_count;
                int c1 = bank1.cannibals_count;
                int c2 = bank2.cannibals_count;

                for(int i = 0; i <= boat_capacity; i++){                                     // i represents # of missionaries
                    for(int j = boat_capacity - i; j >= 0; j--){                             // j represents # of cannibals
                        if((!(i == 0 && j == 0)) && (((i != 0) && (i >= j)) || (i == 0))){   // boat is safe for missionaries
                            int new_m1 = m1 - i;
                            int new_c1 = c1 - j;
                            int new_m2 = m2 + i;
                            int new_c2 = c2 + j;
                            if(isValid(new_m1, new_c1, new_m2, new_c2)){
                                bank new_bank1 = new bank(new_m1, new_c1, 1, false);
                                bank new_bank2 = new bank(new_m2, new_c2, 2, true);
                                b = new boat(i, j, 2);
                                dfs_node_count_explored++;
                                state new_state = new state(new_bank1, new_bank2, b);
                                String new_state_key = value_to_key(new_state, "bank1");
                                if(!map.containsKey(new_state_key)){
                                    new_state.parent_state = cur_state;
                                    q.push(new_state);
                                    map.put(new_state_key, new_state);
                                }
                            }
                        }
                    }
                }
            }
            
            
            if(bank2.isBoathere){                                                               // boat has reached at shore of bank2
                int m1 = bank1.missionaries_count;
                int m2 = bank2.missionaries_count;
                int c1 = bank1.cannibals_count;
                int c2 = bank2.cannibals_count;
                if((m1 == 0) && (c1 == 0)){                                                     // checks wheather we reached our
                    isSlovablebydfs = true;                                                     // target state ( bank 2 m 0 c 0)
                    looptime = System.currentTimeMillis();
                    runningtime = (looptime - init_time) ;
                    time2 = runningtime;
                    return cur_state;
                }
                for(int i = 0; i <= boat_capacity; i++){                                        // i represents # of missionaries
                    for(int j = boat_capacity - i; j >= 0; j--){                                // j represents # of cannibals
                        if( (!(i == 0 && j == 0)) && (((i != 0) && (i >= j)) || (i == 0))){     // boat is safe for missionaries
                            int new_m1 = m1 + i;
                            int new_c1 = c1 + j;
                            int new_m2 = m2 - i;
                            int new_c2 = c2 - j;
                            if(isValid(new_m1, new_c1, new_m2, new_c2)){
                                bank new_bank1 = new bank(new_m1, new_c1, 1, true);
                                bank new_bank2 = new bank(new_m2, new_c2, 2, false);
                                b = new boat(i, j, 2);
                                dfs_node_count_explored++;
                                state new_state = new state(new_bank1, new_bank2, b);
                                String new_state_key = value_to_key(new_state, "bank2");
                                if(!map.containsKey(new_state_key)){
                                    new_state.parent_state = cur_state;
                                    q.push(new_state);
                                    map.put(new_state_key, new_state);
                                }
                            }
                        }
                    }
                }
            }
            
            
            
        }
        long looptime = System.currentTimeMillis();
        long runningtime = (looptime - init_time) ;
        time2 = runningtime;
        return null;
    }
    
    
    public static void path(state start, state end){
        if(end == start){
            System.out.println(start.toString());
        }
        else if(end.parent_state == null){
            System.out.println("No path.");
        }
        else{
            path(start, end.parent_state);
            System.out.println(end.toString());
        }
    }
    
    
    public static void main(String[] args) {
        bfs_node_count_expanded = 0;
        dfs_node_count_expanded = 0;
        dfs_node_count_explored = 0;
        dfs_node_count_explored = 0;
        int m, c, cap, ti;
        Boolean pathprint;

        Scanner in = new Scanner(System.in);
        Scanner in2 = new Scanner(System.in);
        System.out.print("# of Missionaries : ");
        m = in.nextInt();
        System.out.print("# of Cannibals : ");
        c = in.nextInt();
        System.out.print("Boat capacity : ");
        cap = in.nextInt();
        System.out.print("Allowable time (in seconds) : ");
        ti = in.nextInt();
        ti = ti * 1000;
        System.out.println("");
        
        
        state solvedstatebybfs = bfs(m, c, cap, ti);
        
        System.out.println("-------------------------------BFS--------------------------------");
        if(isSlovablebybfs){
            System.out.println("Solved by bfs, Total Expanded Node : " + bfs_node_count_expanded + ", Total Explored Node : " + bfs_node_count_explored);
            System.out.println("Total Time Taken : " + time + " mili sec.");
            System.out.print("Print path [y/n] : ");
            String ans;
            ans = in2.nextLine();
            if(ans.equalsIgnoreCase("y")){
                System.out.println("");
                path(startbfsstate, solvedstatebybfs);
                System.out.println("");
            }
        }
        else{
            System.out.println("Could not be solved by BFS.");
        }
        
        
        state solvedstatebydfs = dfs(m, c, cap, ti);
        
        System.out.println("-------------------------------DFS--------------------------------");
        if(isSlovablebydfs){
            System.out.println("Solved dfs, Total Expanded Node : " + dfs_node_count_expanded + ", Total Explored Node : " + dfs_node_count_explored);
            System.out.println("Total Time Taken : " + time2 + " mili sec.");
            System.out.print("Print path [y/n] : ");
            String ans;
            ans = in2.nextLine();
            if(ans.equalsIgnoreCase("y")){
                System.out.println("");
                path(startdfsstate, solvedstatebydfs);
                System.out.println("");
            }
        }
        else{
            System.out.println("Could not be solved by DFS.");
        }
    }
    
}
