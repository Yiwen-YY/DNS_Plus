package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import naming.AbstractBroker;
import naming.Publication;
import naming.Subscription;

import java.util.AbstractMap;
/**
 *
 * @author uceeftu
 */
// public class BalancedBinaryTree 
// {    
//     AbstractBroker broker;

//     TreeMap<Subscription, Integer> tree;

//     public BalancedBinaryTree(AbstractBroker b) 
//     {
//         broker = b;
//         tree = new TreeMap<>(new SubscriptionComparator(broker));
//     }    
    
//     public void addNode(Subscription s) 
//     {
//         tree.put(s, 0);
//     }

    
//     // public Boolean search(Publication p)
//     // {
//     //     // this is a fake subscription used only for searching
//     //     // inside the map
//     //     Subscription s = new Subscription(p.getValue());
        
//     //     return tree.containsKey(s);
//     // }

//     // public Map.Entry<Subscription, Integer> search(Publication p) {
//     //     Subscription s = new Subscription(p.getValue());
//     //     Integer value = tree.get(s);

//     //     if (value != null) {
//     //         return Map.entry(s, value);
//     //     } else {
//     //         return null;
//     //     }
//     // }


//     public List<Map.Entry<Subscription, Integer>> search(Publication p) {
//         List<Map.Entry<Subscription, Integer>> results = new ArrayList<>();
//         Subscription s = new Subscription(p.getValue());
    
//         for (Map.Entry<Subscription, Integer> entry : tree.entrySet()) {
//             if (tree.comparator().compare(s, entry.getKey()) == 0) {
//                 results.add(entry);
//             }
//         }
    
//         return results;
//     }
    

// }

public class BalancedBinaryTree {
    private AbstractBroker broker;
    private List<Map.Entry<Subscription, Integer>> list;

    public BalancedBinaryTree(AbstractBroker b) {
        broker = b;
        list = new ArrayList<>();
    }

    public void addNode(Subscription s, Integer value) {
        list.add(new AbstractMap.SimpleEntry<>(s, value));
    }

    public List<Map.Entry<Subscription, Integer>> search(Publication p) {
        List<Map.Entry<Subscription, Integer>> results = new ArrayList<>();
        Subscription s = new Subscription(p.getValue());

        for (Map.Entry<Subscription, Integer> entry : list) {
            if (broker.cover(s, entry.getKey()) == 0) {
                results.add(entry);
            }
        }

        return results;
    }
}

