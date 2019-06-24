import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Port: " + port + " open");
        Socket client = server.accept();

        System.out.println("Nova conexão com o cliente " + client.getInetAddress().getHostAddress());

        Scanner input = new Scanner(client.getInputStream());

        while (input.hasNextLine()) {
            System.out.println(input.nextLine());
        }

        input.close();
        server.close();
    }
}
