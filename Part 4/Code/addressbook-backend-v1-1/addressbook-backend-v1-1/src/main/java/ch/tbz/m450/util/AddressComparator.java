package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;

import java.util.*;

import static ch.tbz.m450.util.HelperFunc.compareIfBothPresent;

public class AddressComparator implements Comparator<Address> {

    public List<Address> sort(List<Address> addresses) {
        List<Address> result = new ArrayList<>(addresses);
        for (int i = 1; i < result.size(); i++) {
            Address key = result.get(i);
            int j = i - 1;
            while (j >= 0 && compare(result.get(j), key) > 0) {
                result.set(j + 1, result.get(j));
                j--;
            }
            result.set(j + 1, key);
        }
        return result;
    }

    @Override
    public int compare(Address a1, Address a2) {

        boolean exactlyOneHasPriorityOrBothHaveTheSame = (a1.getPriority() == null) != (a2.getPriority() == null) || a1.getPriority() != null && a1.getPriority().equals(a2.getPriority());
        if (exactlyOneHasPriorityOrBothHaveTheSame) {
            Map<Integer, Object> listValuesFirst = HelperFunc.toValueMap(a1);
            Map<Integer, Object> listValuesSecond = HelperFunc.toValueMap(a2);

            // a1 has the priority and both have that exact data field filled
            if (a1.getPriority() != null && listValuesFirst.containsKey(a1.getPriority()) && listValuesSecond.containsKey(a1.getPriority())) {
                return HelperFunc.compareByPriority(a1, a2, a1.getPriority());

            }

            // a2 has the priority and both have that exact data field filled
            if (a2.getPriority() != null && listValuesFirst.containsKey(a2.getPriority()) && listValuesSecond.containsKey(a2.getPriority())) {
                return HelperFunc.compareByPriority(a1, a2, a2.getPriority());

            }
        }

        // If the priority field is not usable, we use the default
        // priorities, but only compare if both have the data set.
        int result = compareIfBothPresent(a1.getFirstname(), a2.getFirstname());
        if (result != 0) return result;

        result = compareIfBothPresent(a1.getLastname(), a2.getLastname());
        if (result != 0) return result;

        result = compareIfBothPresent(a1.getPhonenumber(), a2.getPhonenumber());
        if (result != 0) return result;

        result = compareIfBothPresent(a1.getRegistrationDate(), a2.getRegistrationDate());
        return result;
    }
}
