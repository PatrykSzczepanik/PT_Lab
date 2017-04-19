package SendingFile;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Szczepan on 17.03.2017.
 */
public class MainServer {
    private int port;
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    public MainServer(int port){
        this.port = port;
        listen();
    }

    private void listen(){
        System.out.print("Server zostal uruchomiony i oczekuje na potencjalnych klientow.\n");
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while(true){
                final Socket client = serverSocket.accept();
                executor.submit(() -> {
                    try {
                        handleClient(client);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) throws InterruptedException {
        System.out.print("Mam polaczonego klienta!\n");
        Thread.sleep(10);
        try (DataInputStream input = new DataInputStream(client.getInputStream())) {
            try (FileOutputStream outToFile = new FileOutputStream("src\\" + input.readUTF())){
                byte[] buffer = new byte[4096];
                int readSize;
                while ((readSize = input.read(buffer)) != -1) {
                    outToFile.write(buffer, 0, readSize);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainServer server = new MainServer(5000);
    }
}
