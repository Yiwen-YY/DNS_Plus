package naming;

import java.util.Map;

import heps.HEPS;
import tree.BinaryTree;
import tree.Node;

/**
 *
 * @author f.tusa
 */
public class Broker extends AbstractBroker {
    
    
    private BinaryTree table;
    

    public Broker(String n, HEPS heps) 
    {
        this.name = n;
        this.heps = heps;
        table = new BinaryTree(this);
    }
    
    
    public void addSubscription(Subscription s) 
    {
        table.addNode(s);
    }
    
    // public boolean matchPublication(Publication p) 
    // {
    //     Node node = table.search(table.getRoot(), p);
    //     if (node != null)
    //     {
    //         //System.out.println("found match: " + node.getSubscription().getServiceName());
    //         return true;
    //     }
        
    //     return false;
        
    // }

    public Map.Entry<Subscription, Integer> matchPublication(Publication p) {
        Map<Subscription, Integer> tree;
        Subscription s = new Subscription(p.getValue());
        Integer value = tree.get(s);

        if (value != null) {
            return Map.entry(s, value);
        } else {
            return null;
        }
    }
}
