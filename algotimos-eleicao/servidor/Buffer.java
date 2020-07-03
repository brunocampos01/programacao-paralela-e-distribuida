/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

public class Buffer {
    private int conteudo;
    private boolean disponivel;

    public synchronized void set(int idProdutor, int valor) {
        while (disponivel == true) {
            try {
                System.out.println("-----Produtor #" + idProdutor + " esperando...");
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        conteudo = valor;
        System.out.println("-----Produtor #" + idProdutor + " colocou " + conteudo);
        disponivel = true;
        notifyAll();
    }

    public synchronized int get(int idConsumidor) {
        while (disponivel == false) {
            try {
                System.out.println("-----Consumidor #" + idConsumidor + " esperado...");
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("-----Consumidor #" + idConsumidor + " consumiu: " + conteudo);
        disponivel = false;
        notifyAll();
        return conteudo;
    }
}
