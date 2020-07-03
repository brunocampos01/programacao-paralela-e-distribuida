package multicast;

//Classe que cria, organiza os processos e realiza eventos (localizar, enviar, receber)
public class Multicast {

    public int nprocessos;
    //Lista de processos
    private final java.util.List<Process> processos;

    //Cria e configura os processos
    public Multicast(int nprocessos) {
        this.nprocessos = nprocessos;
        this.processos = new java.util.ArrayList<Process>();
        for (int i = 0; i < nprocessos; i++) {
            this.processos.add(new Process(this, i));
        }
    }

    //Entrega as mensagens do evento conforme os detalhes do pacote
    public synchronized void despachaPacote(Packet packet, int senderProcessId) {
        Process process = processos.get(packet.getProcessoId());
        process.receiveEvent(packet, senderProcessId);
    }

    public static void main(String[] args) {

        //Caso nao seja fornecido os 2 parametros, exibe a mensagem abaixo
        if (args.length < 2) {
            System.out.println("Atencao: Forneça os parametros para numero de processos(int) e duracao da simulacao em segundos(int).");
            System.exit(0);
        }
        int processCount = Integer.parseInt(args[0]);
        int duration = Integer.parseInt(args[1]);


        long timestamp = System.currentTimeMillis();
        System.out.println("Configurando a simulação do ambiente Multicast com [" + processCount + "] processos e duração de execução de [" + duration + "] segundos.");
        Multicast dcSystem = new Multicast(processCount);

        //Inicializa cada processo individualmente
        for (Process process : dcSystem.processos) {
            new Thread(process).start();
        }

        // Aguarda a duração especificada para executar a simulação e depois encerra.
        try {
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finalizando a simulação de ambiente de computação distribuída, com duração de [" + ((System.currentTimeMillis() - timestamp) / 1000) + "] segundos.");
        System.exit(0);
    }
}
