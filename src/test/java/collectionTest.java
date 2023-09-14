import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class collectionTest {
    @Test
    public void mapIterator(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
        for (int i = 0; i < 1000; i++) {
            concurrentHashMap.put(i+"xxx","xxx");
        }

        if(concurrentHashMap.hashCode() > 100) System.out.println("map hashCode>100");
        else System.out.println(concurrentHashMap.hashCode());
        if (concurrentHashMap.getClass().getSimpleName().equals("TreeBin")) {
            System.out.println("红黑树已生成");
        } else {
            System.out.println("仍然是链表结构");
        }
        System.out.println(concurrentHashMap.size());
//        ArrayList<> a = new ArrayList()
//        Cloneable;
//                Object

//        for(Map.Entry<Integer,String> entry: concurrentHashMap.entrySet()){
//            System.out.println(entry.hashCode() + entry.toString());
//        }
//        System.out.println(concurrentHashMap.size());
//
//
//        for (Integer key : concurrentHashMap.keySet()) {
//            System.out.println(key);
//        }
//
//        for (String value : concurrentHashMap.values()) {
//            System.out.println(value);
//        }
//
//        Iterator<Map.Entry<Integer, String>> entries = concurrentHashMap.entrySet().iterator();
//        System.out.println("使用Iterator遍历,并且使用泛型:");
//        while (entries.hasNext()) {
//            Map.Entry<Integer,String> entry = entries.next();
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }
//
//        Iterator entrys = concurrentHashMap.entrySet().iterator();
//        System.out.println("使用Iterator遍历,并且不使用泛型:");
//        while (entrys.hasNext()) {
//            Map.Entry entry = (Map.Entry)entrys.next();
//            Integer key = (Integer)entry.getKey();
//            String value = (String)entry.getValue();
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }
    }
}
