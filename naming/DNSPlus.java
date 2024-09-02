package naming;

import heps.HEPS;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

/**
 *
 * @author f.tusa
 */
public final class DNSPlus 
{
    // may use a list for each type of entity
    HEPS heps = new HEPS(2048, 2048/8, 512);
        
    Subscriber subscriber = new Subscriber("Subscriber1");
    Publisher publisher = new Publisher("Publisher1");
    AbstractBroker broker = new BrokerWithBalancedTree("Broker1", heps);
    //AbstractBroker broker = new Broker("Broker1", heps);
    
    File serviceNames;
    String[] services;
    
    double addSubscriptions = 0;
    Map<String, Double> matchTimings = new HashMap<>();
        
       
    
    public DNSPlus(String file)
    {
        serviceNames = new File(file);
        services = new String[1000];
        
        subscriber.setHeps(heps);
        publisher.setHeps(heps);
        
        subscriber.getSecurityParameters();
        publisher.getSecurityParameters();
    }
    
    private void setExperimentParameters() 
    {
        // matchTimings.put("b", 0d);
        // matchTimings.put("bi", 0d);
        // matchTimings.put("bit", 0d);
        matchTimings.put("bitpay.com", 0d);
        matchTimings.put("google.com", 0d);
        matchTimings.put("colorado.edu", 0d);
        matchTimings.put("adobe.ly", 0d);
        matchTimings.put("music.apple.com", 0d);
    }
    
    private void setRandomExperimentParameters() 
    {
        Random n = new Random();
        int randomIndex;
        
        for (int i = 0; i < 900; i += 99) {
            randomIndex = n.nextInt(i, i+99);
            System.out.println("Index: " + randomIndex + " service: " + services[randomIndex]);
            matchTimings.put(services[randomIndex], 0d);
        }
        
        matchTimings.put("doesnotexist", 0d);
    }
    
    public void generateSubscriptions(int cutNum) throws FileNotFoundException 
    {
        long t;
        int i = 0;
        
        try (Scanner scanner = new Scanner(serviceNames)) {
            while (scanner.hasNextLine())
            {
                String service = scanner.nextLine();
                // System.out.println(service);
                services[i++] = service;
                Subscription s = subscriber.generateSubscription(service, cutNum);

                // String subStr = service.substring(0, 1);
                // if (service.equals("bit")) {
                //     System.out.println(service);
                //     System.out.println("Cover value: " + s.getCoverValue());
                //     // System.out.println("Match value: " + s.getMatchValue());
                //     // System.out.println("Match value 1: " + s.getMatchValuePlusOne());
                // }

                t = System.nanoTime();
                broker.addSubscription(s);
                addSubscriptions += (System.nanoTime() - t);
            }
        }
        
        //setRandomExperimentParameters();
        setExperimentParameters();
    }
    
    public List<Map.Entry<Subscription, Integer>> match(String service, Publication p) 
    {
        double timing = matchTimings.get(service);
        
        long t = System.nanoTime();
        List<Map.Entry<Subscription, Integer>> matchResult = broker.matchPublication(p);
        timing += (System.nanoTime() - t);
        
        matchTimings.put(service, timing);
        return matchResult;
    }
    
    
    public static Number[] matchResults(int cutNum) 
    {    
        Number[] results = new Number[2];

        // int iterations = 1;
        int iterations = 1000;
        double subscriptions = 0;
             
        long t;
        
        String home = System.getProperty("user.home");
        DNSPlus dnsPlus = new DNSPlus(home + "/Developer/dnsPlus/websites.txt");
        try {
            System.out.println("Generating subscriptions table");
            t = System.nanoTime();
            dnsPlus.generateSubscriptions(cutNum); 
            subscriptions = System.nanoTime() - t;
            
        } catch (FileNotFoundException ex) {
            System.out.println("Subscription could not be loaded: " + ex.getMessage());
            System.exit(-1);
        }
        
        
        System.out.println("Matching publications");

        for (String service : dnsPlus.matchTimings.keySet())
        {   
            Publication publication = dnsPlus.publisher.generatePublication(service, cutNum);
            // System.out.println("Publication Value: \n" + publication.getValue());

            for (int i=0; i<iterations; i++) 
            {   
                List<Map.Entry<Subscription, Integer>> matchResult = dnsPlus.match(service, publication);
                if (matchResult != null) {
                    // System.out.println(service + ": " + matchResult.size());
                    results[0] = matchResult.size();

                    // if (matchResult.size() != 0) {
                    //     for (int j = 0; j < matchResult.size(); j++) {
                    //         System.out.println(matchResult.get(j).getKey().getServiceName());
                    //         // System.out.println(matchResult.get(j).getKey().getCoverValue());
                    //     }
                    // }
                }
            }
        }
        
        
        System.out.println("Total Subscriptions table generation (ms) [includes subscriptions generation]: " + subscriptions / 1000000);
        System.out.println("Subscriptions table generation (ms) [actual time to add subscriptions to the table]: " + dnsPlus.addSubscriptions / 1000000);
        
        double timingSum = 0;
        for (String service : dnsPlus.matchTimings.keySet()) 
        {
            double timing = (dnsPlus.matchTimings.get(service) / 1000000) / iterations;
            System.out.println("Match [" + service + "] (ms): " + String.format("%.2f", timing));
            timingSum += timing;
        }

        double averageMatchTiming = timingSum / dnsPlus.matchTimings.size();
        
        System.out.println("Average match time (ms): " + averageMatchTiming);

        results[1] = averageMatchTiming;

        return results;
    } 

    public static void main(String[] args) {
        String name = "google.com";
        Number[] results = new Number[2];
        int[] matchNum = new int[10];
        double[] matchTiming = new double[10];

        // for (int i = 0; i < name.length(); i++) {
        for (int i = 0; i < 1; i++) {
            // String cutName = name.substring(0, 1);
            results = matchResults(i + 1);
            matchNum[i] = results[0].intValue();
            matchTiming[i] = results[1].doubleValue();
        }
        
        for(int i = 0; i < 10; i++ ) {
            System.out.println(matchNum[i]);
            System.out.println(matchTiming[i]);
        }

    }
}