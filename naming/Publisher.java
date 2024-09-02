package naming;

import heps.Entity;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author f.tusa
 */
public class Publisher extends Entity {
    
    
     public Publisher(String name) {
        this.name = name;
    }
    
    @Override
    public void getSecurityParameters() 
    {
        params = heps.generatePubParameters();
    }
    
    @Override
    public BigInteger matchBlind(BigInteger m) {
        BigInteger rv = (new BigInteger(heps.getRandomRange(), new Random())).mod(heps.getR());
        
        return params.get(2).multiply(params.get(0).modPow(m.subtract(BigInteger.valueOf(1)), heps.getNsquare()))
                     .multiply(params.get(1).modPow(rv, heps.getNsquare())).mod(heps.getNsquare());
    }
    
    public Publication generatePublication(String name, int cutNum) { 
        String cutName = name.substring(0, cutNum);
        BigInteger nameAsBigInteger = new BigInteger(cutName.toLowerCase().getBytes());  

        // BigInteger blindBigInteger = matchBlind(nameAsBigInteger);
        // String str = blindBigInteger.toString(); 
        // int cutLen = str.length() / name.length();
        // String cutBlindBigInteger = str.substring(0, cutLen);   
        // Publication p = new Publication(new BigInteger(cutBlindBigInteger));
        
        BigInteger blindBigInteger = matchBlind(nameAsBigInteger);
        Publication p = new Publication(blindBigInteger);

        return p;  
    
    }

    public Publication generatePublication(BigInteger nameAsBigInteger) {      
        Publication p = new Publication(matchBlind(nameAsBigInteger));
        return p;  
    }
}
