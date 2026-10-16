package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;
import java.util.HashMap;
import java.util.Map;

public class HelperFunc {

    public static Map<Integer, Object> toValueMap(Address a) {
        Map<Integer, Object> values = new HashMap<>();
        ifNotNull(values, 0, a.getFirstname());
        ifNotNull(values, 1, a.getLastname());
        ifNotNull(values, 2, a.getPhonenumber());
        ifNotNull(values, 3, a.getRegistrationDate());
        return values;
    }

    public static void ifNotNull(Map<Integer, Object> map, int key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    public static int compareByPriority(Address a1, Address a2, int priority) {
        switch (priority) {
            case 0: return a1.getFirstname().compareTo(a2.getFirstname());
            case 1: return a1.getLastname().compareTo(a2.getLastname());
            case 2: return a1.getPhonenumber().compareTo(a2.getPhonenumber());
            case 3: return a1.getRegistrationDate().compareTo(a2.getRegistrationDate());
            default: return 0;
        }
}
    public static <T extends Comparable<? super T>> int compareIfBothPresent(T x, T y) {
        if (x == null || y == null) return 0;
        return x.compareTo(y);
    }

}
