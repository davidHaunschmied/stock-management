package pr.se.stockmanagementapi.util;

import pr.se.stockmanagementapi.model.HasDate;

import java.util.Comparator;

public class DateComparator implements Comparator<HasDate> {
    @Override
    public int compare(HasDate o1, HasDate o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
