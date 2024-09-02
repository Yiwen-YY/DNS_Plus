package naming;

import java.util.List;
import java.util.Map;

import heps.HEPS;
import tree.BalancedBinaryTree;

/**
 *
 * @author f.tusa
 */
public class BrokerWithBalancedTree extends AbstractBroker {
    
    private BalancedBinaryTree table;

    public BrokerWithBalancedTree(String n, HEPS heps) 
    {
        this.name = n;
        this.heps = heps;
    
        table = new BalancedBinaryTree(this);
    }
    
    
    public void addSubscription(Subscription s) 
    {
        table.addNode(s, 0);
    }
    
    // public List<Map.Entry<Subscription, List<Integer>>> matchPublication(Publication p) 
    // {   
    //     List<Map.Entry<Subscription, List<Integer>>> found = table.search(p);
    //     //if (found)
    //     //    System.out.println("match found");
    //     return found;
        
    // }

    public List<Map.Entry<Subscription, Integer>> matchPublication(Publication p) {   
        List<Map.Entry<Subscription, Integer>> found = table.search(p);
        
        return found;
    }


}
