package DirTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Created by Szczepan on 12.03.2017.
 */

public abstract class DiskElement implements Comparable<DiskElement>{
    protected Path path;
    protected int depth;
    protected String signOfElement;
    protected Instant date;
    protected int typeOfSorting;
    private String name;
    private String formattedDate;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
            .withLocale( Locale.GERMANY )
                        .withZone( ZoneId.systemDefault() );

    protected DiskElement(String pathString, int depth, int typeOfSort) throws IOException {
        this.path = Paths.get(pathString);
        this.depth = depth;
        this.name = this.path.getFileName().toString();
        this.date =  Files.getLastModifiedTime(this.path).toInstant();
        this.typeOfSorting = typeOfSort;
        this.formattedDate =  formatter.format( this.date );
    }

    public String getName() {
        return name;
    }

    public void printTree() throws IOException{
        print();
    }

    protected void print() throws IOException {
        for (int i=0; i < depth; i++){
            System.out.print("-");
        }
        int numberOfSpaces = 120 - name.length() - depth;
        String space = String.format(this.name + "%" + numberOfSpaces + "s    %s", this.signOfElement, this.formattedDate );
        System.out.println(space);
    }

    @Override
    public int compareTo(DiskElement o) {
        return this.name.compareTo(o.getName());
    }
}
