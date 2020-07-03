package tokenring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClienteUm {

    public static void main(String args[]) throws IOException {

        Socket s = new Socket("localhost", 7000);
        PrintStream out = new PrintStream(s.getOutputStream());

        ServerSocket ss = new ServerSocket(7001);
        Socket s1 = ss.accept();

        BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        PrintStream out1 = new PrintStream(s1.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "Token";

        while (true) {

            if (str.equalsIgnoreCase("Token")) {
                System.out.println("Você quer enviar algum dado?");
                System.out.println("Digite Sim ou Nao");
                str = br.readLine();

                if (str.equalsIgnoreCase("Sim")) {
                    System.out.println("Digite algum dado");
                    str = br.readLine();
                    out.println(str);
                }
                out1.println("Token");
            }
            System.out.println("Esperando pelo Token");
            str = in1.readLine();
        }
    }
}
