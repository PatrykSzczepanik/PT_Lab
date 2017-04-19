package DirTree;

import java.util.Comparator;

/**
 * Created by Szczepan on 12.03.2017.
 */

public class SortByDate implements Comparator<DiskElement> {
    @Override
    public int compare(DiskElement o1, DiskElement o2) {
        return -1*(o1.date.compareTo(o2.date));
    }
}
