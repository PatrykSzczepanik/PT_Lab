package DirTree;

import java.io.IOException;

/**
 * Created by Szczepan on 12.03.2017.
 */

public class DirTree {

    public static void main(String args[]) throws IOException {
        if (args.length >= 0){
//            String path = args[0];
            String path = "C:\\test";
            int typeOfSorting = 0;
            if (args.length == 2){
                if (args[1].equals("1")){
                    typeOfSorting = 1;
                }
            }
            DiskDirectory mainPath = new DiskDirectory(path, 0, typeOfSorting);
            mainPath.printTree();
        }
    }
}
