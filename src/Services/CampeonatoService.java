package Services;

import Entities.JogoEntity;
import Entities.TimeEntity;
import Models.TimeModel;
import Repositories.TimeRepository;

import java.util.*;

public class CampeonatoService {

    private final TimeRepository timeRepository;

    public CampeonatoService() {
        this.timeRepository = new TimeRepository();
    }

    public void inserirJogo(JogoEntity jogo) {
        TimeEntity timeCasa = timeRepository.buscarPorApelido(jogo.getApelidoTimeCasa());
        TimeEntity timeVisitante = timeRepository.buscarPorApelido(jogo.getApelidoTimeVisitante());

        if (timeCasa == null || timeVisitante == null) {
            System.err.println("Erro: Um dos times informados não existe.");
            return;
        }

        // Atualizar gols marcados e sofridos
        timeCasa.setGolsMarcados(timeCasa.getGolsMarcados() + jogo.getGolsTimeCasa());
        timeCasa.setGolsSofridos(timeCasa.getGolsSofridos() + jogo.getGolsTimeVisitante());

        timeVisitante.setGolsMarcados(timeVisitante.getGolsMarcados() + jogo.getGolsTimeVisitante());
        timeVisitante.setGolsSofridos(timeVisitante.getGolsSofridos() + jogo.getGolsTimeCasa());

        // Atualizar pontos
        if (jogo.getGolsTimeCasa() > jogo.getGolsTimeVisitante()) {
            timeCasa.setPontos(timeCasa.getPontos() + 3); // Vitória casa
        } else if (jogo.getGolsTimeCasa() < jogo.getGolsTimeVisitante()) {
            timeVisitante.setPontos(timeVisitante.getPontos() + 3); // Vitória visitante
        } else {
            timeCasa.setPontos(timeCasa.getPontos() + 1); // Empate
            timeVisitante.setPontos(timeVisitante.getPontos() + 1);
        }

        // Atualizar no banco
        timeRepository.atualizar(timeCasa);
        timeRepository.atualizar(timeVisitante);
    }

    private static int diff(TimeEntity t1, TimeEntity t2) {
        if (t2.getPontos() != t1.getPontos()) {
            return Integer.compare(t2.getPontos(), t1.getPontos());
        }
        if (t2.getSaldoGols() != t1.getSaldoGols()) {
            return Integer.compare(t2.getSaldoGols(), t1.getSaldoGols());
        }
        if (t2.getGolsMarcados() != t1.getGolsMarcados()) {
            return Integer.compare(t2.getGolsMarcados(), t1.getGolsMarcados());
        }
        return 0;
    }

    private static boolean mesmaPontuacao(TimeEntity t1, TimeEntity t2) {
        return diff(t1, t2) == 0;
    }

    public List<TimeModel> obterClassificacao() {
        List<TimeEntity> lista = timeRepository.listarTodos();

        lista.sort((t1, t2) -> {
            int r = CampeonatoService.diff(t1, t2);
            if (r == 0)
                r = t1.getApelido().compareTo(t2.getApelido());
            return r;
        });

        List<TimeModel> classificao = new ArrayList<TimeModel>();

        int linha = 0;
        int posicaoAnt = 0;
        TimeEntity ant = null;
        for (TimeEntity t : lista) {
            linha++;
            TimeModel tm = new TimeModel(
                    t.getId(),
                    ant == null || !CampeonatoService.mesmaPontuacao(ant, t) ? linha : posicaoAnt,
                    t.getApelido(),
                    t.getNome(),
                    t.getPontos(),
                    t.getGolsMarcados(),
                    t.getGolsSofridos());
            classificao.add(tm);
            ant = t;
            posicaoAnt = tm.getPosicao();
        }

        return classificao;
    }

    public void reiniciarCampeonato() {
        // Zera todos os dados do campeonato (útil para testes)
        List<TimeEntity> times = timeRepository.listarTodos();
        for (TimeEntity time : times) {
            time.setPontos(0);
            time.setGolsMarcados(0);
            time.setGolsSofridos(0);
            timeRepository.atualizar(time);
        }
    }

    // ====== TIMES ======

    public void inserirTime(TimeEntity time) {
        TimeEntity existente = timeRepository.buscarPorApelido(time.getApelido());
        if (existente != null) {
            System.err.println("Erro: Já existe um time com o apelido \"" + time.getApelido() + "\".");
            return;
        }
        timeRepository.inserir(time);
    }

    public void editarTime(TimeEntity timeAtualizado) {
        TimeEntity existente = timeRepository.buscarPorApelido(timeAtualizado.getApelido());
        if (existente == null) {
            System.err.println("Erro: Time com apelido \"" + timeAtualizado.getApelido() + "\" não encontrado.");
            return;
        }

        // Mantém os dados de pontuação caso o objetivo seja editar apenas o nome
        timeAtualizado.setPontos(existente.getPontos());
        timeAtualizado.setGolsMarcados(existente.getGolsMarcados());
        timeAtualizado.setGolsSofridos(existente.getGolsSofridos());

        timeRepository.atualizar(timeAtualizado);
    }

    public TimeEntity buscarTime(String apelido) {
        return timeRepository.buscarPorApelido(apelido);
    }

    public List<String> listarApelidos() {
        return timeRepository.listarApelidos();
    }

    public void removerTime(String apelido) {
        TimeEntity existente = timeRepository.buscarPorApelido(apelido);
        if (existente == null) {
            System.err.println("Erro: Time com apelido \"" + apelido + "\" não encontrado.");
            return;
        }
        timeRepository.deletarPorApelido(apelido);
    }

}
