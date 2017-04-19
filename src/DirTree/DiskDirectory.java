package DirTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Created by Szczepan on 12.03.2017.
 */

public class DiskDirectory extends DiskElement{
    private Stream<Path> paths;
    private TreeSet<DiskElement> tree;

    public DiskDirectory(String pathString, int depth, int typeOfSort) throws IOException {
        super(pathString, depth, typeOfSort);
        this.signOfElement = "C";
        this.paths = Files.list(this.path);
        if (this.typeOfSorting == 1){
//            this.tree = new TreeSet<>(new SortByDate());      //Same below, but using other "words".
            this.tree = new TreeSet<>((o1, o2) -> -(o1.date.compareTo(o2.date)));
        } else {
            this.tree = new TreeSet<>();
        }
        makeDirTree();
    }

    private void makeDirTree(){
        paths.forEach(f -> {
           if(Files.isDirectory(f)){
               try {
                   tree.add(new DiskDirectory(f.toString(), this.depth+1, this.typeOfSorting));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           } else if (Files.isRegularFile(f)){
               try {
                   tree.add(new DiskFile(f.toString(), this.depth+1));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        });
    }

    @Override
    public void printTree() throws IOException {
        print();
        for (DiskElement e: tree){
            e.printTree();
        }
    }
}
