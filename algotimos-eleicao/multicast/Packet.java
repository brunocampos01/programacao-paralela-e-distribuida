package multicast;

public class Packet {
    private final Object messagem;
    //Processo receptor
    private final int processoId;
    //Tempo de retorno do processo remetente.
    private final int time;

    public Packet(Object messagem, int processId, int time) {
        this.messagem = messagem;
        this.processoId = processId;
        this.time = time;
    }

    public Object getMensagem() {
        return messagem;
    }

    //Processo receptor
    public int getProcessoId() {
        return processoId;
    }

    //Tempo de retorno do processo remetente.
    public int getTime() {
        return time;
    }

    public String toString() {
        return String.format("Pacote [messagem=%s, recebendo processoId=%s, tempo de retorno time=%s]", messagem, processoId, time);
    }
}
