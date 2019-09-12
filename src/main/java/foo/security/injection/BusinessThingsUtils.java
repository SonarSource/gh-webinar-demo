package foo.security.injection;

import java.util.List;
import java.util.ArrayList;

public final class BusinessThingsUtils {

    private BusinessThingsUtils() {   
    }

    public static String[] doComplexBusinessThings(String taintedString) {
        List<String> stockList = new ArrayList<String>();
        stockList.add("foo");
        stockList.add(taintedString);
        stockList.add("bar");
    
        String[] stockArr = new String[stockList.size()];
        return stockList.toArray(stockArr);
      }

}