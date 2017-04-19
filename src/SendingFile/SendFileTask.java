package SendingFile;

import javafx.concurrent.Task;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Szczepan on 18.03.2017.
 */
public class SendFileTask extends Task {
    private int port;
    private File file;

    public SendFileTask(int port, File file) {
        this.port = port;
        this.file = file;
    }

    @Override
    protected Object call() throws Exception {
        updateMessage("Inicjalizacja....");

        try (Socket socketToServer = new Socket("localhost", port)) {
            System.out.print("Polaczylem sie do serwera\n");
            updateMessage("W toku...");
            try (DataOutputStream output = new DataOutputStream(socketToServer.getOutputStream())){
                try (FileInputStream inputFile = new FileInputStream(file)){
                        output.writeUTF(file.getName());
                        byte[] buffer = new byte[4096];
                        int readSize;
                        int readingFileSize = 0;
                        while ((readSize = inputFile.read(buffer)) != -1) {
                            output.write(buffer, 0, readSize);
                            readingFileSize += readSize;
                            updateProgress(readingFileSize, file.length());
                        }
                        updateMessage("Przesłane");
                    }
                }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
