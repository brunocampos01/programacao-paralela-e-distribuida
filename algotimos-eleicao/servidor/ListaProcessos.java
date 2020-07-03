package servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListaProcessos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4985182969269446996L;
    private Processo primeiro;
    private Processo ultimo;

    public ListaProcessos() {
        super();
    }

    public ListaProcessos(Processo primeiro, Processo ultimo) {
        super();
        this.primeiro = primeiro;
        this.ultimo = ultimo;
    }

    public Processo getPrimeiro() {
        return primeiro;
    }

    public void setPrimeiro(Processo primeiro) {
        this.primeiro = primeiro;
    }

    public Processo getUltimo() {
        return ultimo;
    }

    public void setUltimo(Processo ultimo) {
        this.ultimo = ultimo;
    }

    public void add(Processo processo) {
        synchronized (this) {
            if (ultimo == null) {
                primeiro = processo;
                ultimo = processo;
            } else {
                //como � o �ltimo o pr�ximo vai ser ele mesmo
                ultimo.setProximo(processo);
                ultimo = processo;
            }
        }
    }

    public Processo findById(Integer pidID) throws Exception {
        Processo aux = primeiro;
        while (aux != null) {
            if (aux.getPidId() == pidID)
                return aux;
            aux = aux.getProximo();
        }
        throw new Exception("Registro n�o encontrado.");
    }

    public synchronized List<Processo> findAll() {
        Processo aux = primeiro;
        List<Processo> processos = new ArrayList<Processo>();
        while (aux != null) {
            processos.add(aux);
            aux = aux.getProximo();
        }
        return processos;
    }

    public int size() {
        List<Processo> processos = findAll();
        return processos.size();

    }

    public synchronized Processo get(int position) {
        List<Processo> processos = findAll();
        return position < processos.size() ? processos.get(position) : null;
    }

    public void remove(int position) {
        synchronized (this) {
            List<Processo> processos = findAll();
            System.out.println("Removendo processo:" + processos.get(position));
            processos.remove(position);
            primeiro = null;
            ultimo = null;
            for (Processo processo : processos)
                add(processo);
        }
    }

    public void remove(Processo processo) {
        synchronized (this) {
            List<Processo> processos = findAll();
            System.out.println("Removendo processo:" + processo);
            processos.remove(processo);
            primeiro = null;
            ultimo = null;
            for (Processo p : processos)
                add(p);
        }
    }

    public synchronized Processo first() {
        List<Processo> processos = findAll();
        if (processos.isEmpty())
            return null;
        return processos.get(0);
    }

    public synchronized Processo last() {
        List<Processo> processos = findAll();
        if (processos.isEmpty())
            return null;
        int index = processos.size() - 1;
        return processos.get(index);
    }

    public void order() {
        List<Processo> processos = findAll();
        Comparator<Processo> comparator = new Comparacao();
        Processo lista[] = (Processo[]) processos.toArray();
        Arrays.sort(lista, comparator);
        primeiro = null;
        ultimo = null;
        for (Processo processo : processos)
            add(processo);
    }

    public void eleicao() {
        Processo aux = primeiro;
        List<Integer> pidIDs = new ArrayList<Integer>();
        while (aux != null) { //varre at� chegar no �ltimo processo da fila para ser o coordenador!
            pidIDs.add(aux.getPidId());
            System.out.println("PidID:" + pidIDs.toString());
            aux.mensagem();
            aux = aux.getProximo();
        }
    }

    public void coordenador(Processo coordenador) {
        Processo aux = primeiro;
        while (aux != null) {
            aux.mensagem(coordenador);
            aux = aux.getProximo();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((primeiro == null) ? 0 : primeiro.hashCode());
        result = prime * result + ((ultimo == null) ? 0 : ultimo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListaProcessos other = (ListaProcessos) obj;
        if (primeiro == null) {
            if (other.primeiro != null)
                return false;
        } else if (!primeiro.equals(other.primeiro))
            return false;
        if (ultimo == null) {
            if (other.ultimo != null)
                return false;
        } else if (!ultimo.equals(other.ultimo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return findAll().toString();
    }
}
