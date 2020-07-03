package servidor;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Gerenciador implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7757529236684909222L;
    private Eleicao eleicao;
    private ListaProcessos listaProcessos;
    private Timer timerEliminarProcesso;
    private Timer timerConsultarCoordenador;
    private Timer timerConsultarNovoProcesso;
    private Timer timerDesativarCoordenador;
    private Buffer bufferCompartilhado;

    public Gerenciador() {
        super();
        eleicao = new Eleicao();
        listaProcessos = new ListaProcessos();
        timerConsultarCoordenador = new Timer();
        timerConsultarNovoProcesso = new Timer();
        timerEliminarProcesso = new Timer();
        timerDesativarCoordenador = new Timer();
        bufferCompartilhado = new Buffer();
    }

    public void criar() {
        Processo coordenador = new Processo();
        coordenador.setPidId(Processo.getIID());
        eleicao.setCoordenador(coordenador);
        listaProcessos.add(coordenador);
        System.out.println("Processo coordenador eleito: " + coordenador);
    }

    public void inicializar() {
        TimerTask timerTaskConsultarCoordenador = new ConsultarCoordenador();
        timerConsultarCoordenador.scheduleAtFixedRate(timerTaskConsultarCoordenador, 0, 2000);
        //fixed rate � relativo ao tempo da primeira tarefa, ou seja, vai executar 2 segundos
        //independente das tarefas subsequentes

        TimerTask timerTaskNovoProcesso = new NovoProcesso();
        timerConsultarNovoProcesso.scheduleAtFixedRate(timerTaskNovoProcesso, 0, 3000);

        TimerTask timerTaskEliminarProcesso = new EliminarProcesso();
        timerEliminarProcesso.scheduleAtFixedRate(timerTaskEliminarProcesso, 0, 5000);

        TimerTask timerTaskDesativarCoordenador = new DesativarCoordenador();
        timerDesativarCoordenador.schedule(timerTaskDesativarCoordenador, 10000, 10000);
        //em 10 segundos inicia a tarefa para desativar coordenador
    }

    public void encerrar() {
        try {
            Thread.sleep(20000);
            timerDesativarCoordenador.cancel();
            timerConsultarCoordenador.cancel();
            timerConsultarNovoProcesso.cancel();
            timerEliminarProcesso.cancel();

            for (int i = 0; i < listaProcessos.size(); i++) {
                listaProcessos.get(i).getT().stop();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private class ConsultarCoordenador extends TimerTask {

        @Override
        public void run() {
            int position = Randomize.random(listaProcessos.size()); //pega um processo randomicamente
            Processo processo = listaProcessos.get(position);
            Processo coordenador = eleicao.getCoordenador();
            if (processo != null)
                if (!processo.equals(coordenador)) //se o processo n�o � o coordenador, este informa quem � o coordenador
                    eleicao.notificar();
            System.out.println(listaProcessos.toString());
            if (coordenador == null) //caso n�o se tenha um coordenador, elege o �ltimo da lista de processos
                eleicao.eleger(listaProcessos);
        }
    }

    private class NovoProcesso extends TimerTask {

        @Override
        public void run() {
            Processo processo = new Processo();
            processo.setPidId(Processo.getIID());
            //vai criando processos com ID par ou �mpar para diferenciar produtores / consumidores

            /**
             * Inicia Produto / Consumidor
             */
            Thread t;
            if ((processo.getIID() % 2) == 0) {
                t = new Produtor(processo.getIID(), bufferCompartilhado, 2);
            } else {
                t = new Consumidor(processo.getIID(), bufferCompartilhado, 2);
            }

            processo.setT(t); //seta a Thread criada no processo e adiciona o processo na lista
            listaProcessos.add(processo);

            processo.getT().start(); //inicia a Thread criada

            System.out.println("Criando Novo Processo: " + processo);
        }
    }

    private class EliminarProcesso extends TimerTask {

        @Override
        public void run() {
            int position = Randomize.random(listaProcessos.size()); //elimina um processo da lista de forma rand�mica
            Processo coordenador = eleicao.getCoordenador();
            Processo eliminado = listaProcessos.get(position);
            if ((eliminado != null) && (coordenador != null))
                if (!coordenador.equals(eliminado))
                    listaProcessos.remove(position);
        }
    }

    private class DesativarCoordenador extends TimerTask {

        @Override
        public void run() {
            Processo coordenador = eleicao.getCoordenador();
            System.out.println("Desativando Coordenador:" + coordenador);
            listaProcessos.remove(coordenador);
            eleicao.remover();
        }
    }
}
