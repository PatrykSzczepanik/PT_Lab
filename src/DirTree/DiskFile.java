package DirTree;

import java.io.IOException;

/**
 * Created by Szczepan on 12.03.2017.
 */

public class DiskFile extends DiskElement {

    public DiskFile(String pathString, int depth) throws IOException {
        super(pathString, depth, 0);            //Dla plikow nie jest potrzebny typ sortowania
        this.signOfElement = "F";
    }
}
