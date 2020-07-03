package tokenring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClienteDois {

    public static void main(String args[]) throws IOException {

        Socket s = new Socket("localhost", 7000);
        PrintStream out = new PrintStream(s.getOutputStream());

        Socket s2 = new Socket("localhost", 7001);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
        PrintStream out2 = new PrintStream(s2.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        while (true) {
            System.out.println("Esperando pelo Token");
            str = in2.readLine();

            if (str.equalsIgnoreCase("Token")) {
                System.out.println("Voc� quer enviar algum dado?");
                System.out.println("Digite Sim ou Nao");
                str = br.readLine();

                if (str.equalsIgnoreCase("Sim")) {
                    System.out.println("Digite algum dado");
                    str = br.readLine();
                    out.println(str);
                }
                out2.println("Token");
            }
        }
    }
}
