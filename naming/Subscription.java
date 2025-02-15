package naming;

import java.math.BigInteger;

/**
 *
 * @author f.tusa
 */
public class Subscription {
    
    private BigInteger matchValue;
    private BigInteger matchValuePlusOne;
    
    private BigInteger coverValue;
    
    // for debug only
    private String serviceName;
    // private int serviceNameLen;
    
    
    public Subscription(BigInteger mv, BigInteger mvPlusOne, BigInteger cv) {
        matchValue = mv;
        matchValuePlusOne = mvPlusOne;
        coverValue = cv;
    }
    
    public Subscription(BigInteger mv, BigInteger mvPlusOne, BigInteger cv, String name) {
        matchValue = mv;
        matchValuePlusOne = mvPlusOne;
        coverValue = cv;
        serviceName = name; // used for debug
    }
    
    // this should only be used to create a fake subscription for publication 
    // match in the BalancedBinaryTree
    public Subscription(BigInteger cv) {
        coverValue = cv;
    }

    public BigInteger getMatchValue() {
        return matchValue;
    }

    public BigInteger getMatchValuePlusOne() {
        return matchValuePlusOne;
    }

    public BigInteger getCoverValue() {
        return coverValue;
    }

    public String getServiceName() {
        return serviceName;
    }
    // public int getServiceNameLen() {
    //     serviceNameLen = serviceName.length();
    //     return serviceNameLen;
    // }

    // @Override
    // public String toString() {
    //     return "Subscription{coverValue='" + coverValue + "'}";
    // }
}
