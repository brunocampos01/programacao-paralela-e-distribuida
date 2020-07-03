package multicast;

/*
 * Classe que representa um processo independente dentro do multicast.
 * Realiza eventos (local, enviar, receber) aleatoriamente até ser interrompido.
 */
public class Process implements Runnable {

    private int tempoLocal;
    private final int processoId;
    private final Multicast multicast;
    private final java.util.Random random;

    public Process(Multicast multicast, int processoId) {
        tempoLocal = 0;
        this.processoId = processoId;
        this.multicast = multicast;
        random = new java.util.Random();
    }

    public void run() {
        System.out.println("Processo [P" + processoId + "] iniciado ...");
        while (true) {
            // Realize um evento aleatório (local ou externo).
            int eventoRamdomico = random.nextInt(2);
            switch (eventoRamdomico) {
                case 0: {
                    // realiza um evento local
                    localEvent();
                    break;
                }
                case 1: {
                    // realiza o evento de enviar
                    sendEvent();
                    break;
                }
            }
            try {
                // Adiciona atraso aleatório entre eventos para facilitar a visualização
                long randomDelay = System.currentTimeMillis() % (random.nextInt(1000) + 1000);
                Thread.sleep(randomDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void localEvent() {
        // Incrementa a hora do relógio local em 1, sem tempo de retorno, por isso -1
        int time = increamentClockTime(-1);
        System.out.println("Processo [P" + processoId + "] realiza um evento local. Hora do relógio é: " + time);
    }

    public void sendEvent() {
        Object message = "exclusao mutua - multicast";

        // Obtém um ID de processo aleatório para enviar o evento, excluindo seu próprio ID.
        int randomProcessoId = random.nextInt(multicast.nprocessos);
        while (randomProcessoId == processoId) {
            randomProcessoId = random.nextInt(multicast.nprocessos);
        }

        // Incrementa a hora do relógio local em 1
        int time = increamentClockTime(-1);
        System.out.println("Processo [P" + processoId + "] envia um evento para o processo [P" + randomProcessoId + "] com adição de tempo de: " + time);
        Packet packet = new Packet(message, randomProcessoId, time);

        // Envia pacotes para entregar
        multicast.despachaPacote(packet, processoId);
    }

    //o metodo é executado quando o processo recebe qualquer evento de outros processos no ambiente multicast.
    public void receiveEvent(Packet packet, int senderprocessoId) {
        // Incrementar a hora do relógio local em 1, com relação ao tempo de retorno do processo remetente
        int time = increamentClockTime(packet.getTime());
        System.out.println("Processo [P" + processoId + "] recebe um evento do processo\n" +
            " [P" + senderprocessoId + "] e a hora do relógio é: " + time);
    }

    /**
     * Incrementa o horário do relógio local, aplicando regras conforme mencionado abaixo:
     * 1. Define a hora do relógio local para o máximo de hora local e hora de partida.
     * 2. Incrementa o relógio local calculado em 1 e retorna.
     * OBS: Este é um método sincronizado para aumentar com segurança o tempo.
     */
    public synchronized int increamentClockTime(int tempoRetorno) {
        tempoLocal = tempoLocal > tempoRetorno ? tempoLocal : tempoRetorno;
        return ++tempoLocal;
    }

}
