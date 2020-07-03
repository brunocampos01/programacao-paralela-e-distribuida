package servidor;

import java.io.Serializable;

public class Eleicao implements Serializable {

    private static int TENTATIVAS = 0;
    private static int MAX_TENTATIVAS = 5;
    private static final long serialVersionUID = -8827196002196053276L;
    private Processo coordenador;

    public Eleicao() {
        super();
    }

    public Processo getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Processo coordenador) {
        this.coordenador = coordenador;
    }

    public void remover() {
        coordenador = null;
    }

    public void notificar() {
        synchronized (this) { //mais que uma Thread n�o vai conseguir acessar esse trecho ao mesmo tempo
            if (coordenador == null)
                System.err.println("Coordenador n�o est� respondendo....");
            else
                System.out.println("Coordenador Atual:" + coordenador.toString() + " consultado...");
        }
    }

    public void eleger(ListaProcessos processos) {
        processos.eleicao();
        coordenador = processos.last();
        System.err.println("Novo Coordenador: " + coordenador);
        processos.coordenador(coordenador);
    }

    public boolean eleger() {
        if (coordenador == null) {
            TENTATIVAS++;
            if (TENTATIVAS > MAX_TENTATIVAS) {
                TENTATIVAS = 0;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
