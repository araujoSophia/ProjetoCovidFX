package Services;

import Entities.JogoEntity;
import Entities.ObitoEntity;
import Entities.PacienteEntity;
import Entities.TesteEntity;
import Entities.TimeEntity;
import Models.TesteModel;
import Models.TimeModel;
import Repositories.PacienteRepository;
import Repositories.TesteRepository;
import Repositories.ObitoRepository;
import Repositories.TimeRepository;

import java.util.*;

public class CampeonatoService {

    private final TimeRepository timeRepository;
    private final PacienteRepository pacienteRepository;
    private final TesteRepository testeRepository;
    private final ObitoRepository obitoRepository;

    public CampeonatoService() {
        this.timeRepository = new TimeRepository();
        this.pacienteRepository = new PacienteRepository();
        this.testeRepository = new TesteRepository();
        this.obitoRepository = new ObitoRepository();
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

    // ====== PACIENTES ======

    public PacienteEntity buscarPacientePorCpf(String cpf) throws Exception {
        return pacienteRepository.buscarPorCpf(cpf);
    }

    public void inserirPaciente(PacienteEntity paciente) throws Exception {
        pacienteRepository.inserir(paciente);
    }

    public void editarPaciente(PacienteEntity paciente) throws Exception {
        pacienteRepository.atualizar(paciente);
    }

    public List<PacienteEntity> listarPacientes() {
        return pacienteRepository.listar();
    }

    public void removerPaciente(String cpf) throws Exception {
        pacienteRepository.remover(cpf);
    }

    public ArrayList<Models.PacienteModel> obterPacientesParaTabela() {
        ArrayList<Models.PacienteModel> listaModel = new ArrayList<>();
        ArrayList<Entities.PacienteEntity> listaEntidades = pacienteRepository.listar();

        int posicao = 1;
        for (Entities.PacienteEntity paciente : listaEntidades) {
            Models.PacienteModel model = new Models.PacienteModel(
                    posicao++,
                    paciente.getCpf(),
                    paciente.getNome(),
                    String.valueOf(paciente.getIdade()),
                    paciente.getCidade(),
                    paciente.getEstado(),
                    paciente.getDataNasc());
            listaModel.add(model);
        }
        return listaModel;
    }

    public void reiniciarPacientes() throws Exception {
        ArrayList<Entities.PacienteEntity> lista = pacienteRepository.listar();
        for (Entities.PacienteEntity paciente : lista) {
            pacienteRepository.remover(paciente.getCpf());
        }
    }

    public boolean pacienteExiste(String cpf) throws Exception {
        return pacienteRepository.buscarPorCpf(cpf) != null;
    }

    // ====== TESTES ======

    public void inserirTeste(TesteEntity teste) throws Exception {
        // Antes de inserir, verifica se o paciente existe
        PacienteEntity paciente = buscarPacientePorCpf(teste.getCpfPaciente());
        if (paciente == null) {
            throw new Exception("Paciente com CPF " + teste.getCpfPaciente() + " não encontrado.");
        }
        testeRepository.inserir(teste);
    }

    public ArrayList<TesteModel> obterTestesParaTabela() {
        ArrayList<TesteEntity> testes = testeRepository.listar();
        ArrayList<TesteModel> modelos = new ArrayList<>();

        for (TesteEntity teste : testes) {
            modelos.add(new TesteModel(teste.getDataTeste(), teste.getCpfPaciente(), teste.getResultado()));
        }
        return modelos;
    }

    // ====== ÓBITOS ======

    public void inserirObito(ObitoEntity obito) throws Exception {
        PacienteEntity paciente = buscarPacientePorCpf(obito.getCpfPaciente());
        if (paciente == null) {
            throw new Exception("Paciente com CPF " + obito.getCpfPaciente() + " não encontrado.");
        }
        obitoRepository.inserir(obito);
    }

}
