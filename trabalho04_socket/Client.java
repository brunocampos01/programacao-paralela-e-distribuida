import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Bruno Campos
 * Para executar este exemplo, execute primeiro o código do Servidor. Depois execute o código do client.
 * No client, digite uma msg. No servidor, visualize que a msg chegou!
 */
public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {

        String host = "127.0.0.1";
        int port = 12345;

        Socket client = new Socket(host, port);
        System.out.println("O client: " + client +" se conectou ao servidor!");
        Scanner input = new Scanner(System.in);

        PrintStream out = new PrintStream(client.getOutputStream());

        while (input.hasNextLine()) {
            out.println(input.nextLine());
        }
        out.close();
        input.close();
    }

}

