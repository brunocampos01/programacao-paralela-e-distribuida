package servidor;

import java.io.Serializable;

public class Processo implements Serializable {

    private static int ID_INCREMENT = 0;
    private static final long serialVersionUID = 4462326174062713240L;
    private Integer pidId;
    private Processo proximo;
    private Processo anterior;
    private Thread t;

    public Processo() {
        super();
    }

    public Processo(Integer pidId, Processo proximo, Processo anterior) {
        super();
        this.pidId = pidId;
        this.proximo = proximo;
        this.anterior = anterior;
    }

    public static int getIID() {
        return ++ID_INCREMENT;
    }

    public Integer getPidId() {
        return pidId;
    }

    public void setPidId(Integer pidId) {
        this.pidId = pidId;
    }

    public Processo getProximo() {
        return proximo;
    }

    public void setProximo(Processo proximo) {
        this.proximo = proximo;
    }

    public Processo getAnterior() {
        return anterior;
    }

    public void setAnterior(Processo anterior) {
        this.anterior = anterior;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((anterior == null) ? 0 : anterior.hashCode());
        result = prime * result + ((pidId == null) ? 0 : pidId.hashCode());
        result = prime * result + ((proximo == null) ? 0 : proximo.hashCode());
        return result;
    }

    //vai enviando mensagem de um processo ao pr�ximo processo da fila
    public void mensagem() {
        if (proximo != null)
            System.err.println("[" + this + "] enviando para :[" + proximo + "]");
        else
            System.err.println("[" + this + "] enviando para :[...]");

    }

    //vai enviando mensagem de um processo ao pr�ximo processo da fila sobre a elei��o do coordenador
    public void mensagem(Processo processo) {
        if (proximo != null)
            System.err.println("[" + this + "] enviando para :[" + proximo + "], coordenador eleito: [" + processo + "]");
        else
            System.err.println("[" + this + "] enviando para :[...]");

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Processo other = (Processo) obj;
        if (anterior == null) {
            if (other.anterior != null)
                return false;
        } else if (!anterior.equals(other.anterior))
            return false;
        if (pidId == null) {
            if (other.pidId != null)
                return false;
        } else if (!pidId.equals(other.pidId))
            return false;
        if (proximo == null) {
            if (other.proximo != null)
                return false;
        } else if (!proximo.equals(other.proximo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Processo [pidId=" + pidId + "]";
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
