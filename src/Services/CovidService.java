package Services;

import java.util.ArrayList;
import java.util.List;

import Entities.ObitoEntity;
import Entities.PacienteEntity;
import Entities.TesteEntity;
import Models.ObitoModel;
import Models.TesteModel;
import Repositories.ObitoRepository;
import Repositories.PacienteRepository;
import Repositories.TesteRepository;

public class CovidService {

    private final PacienteRepository pacienteRepository;
    private final TesteRepository testeRepository;
    private final ObitoRepository obitoRepository;

    public CovidService() {
        this.pacienteRepository = new PacienteRepository();
        this.testeRepository = new TesteRepository();
        this.obitoRepository = new ObitoRepository();
    }

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


    public void inserirTeste(TesteEntity teste) throws Exception {
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

    public void inserirObito(ObitoEntity obito) throws Exception {
        PacienteEntity paciente = buscarPacientePorCpf(obito.getCpfPaciente());
        if (paciente == null) {
            throw new Exception("Paciente com CPF " + obito.getCpfPaciente() + " não encontrado.");
        }
        obitoRepository.inserir(obito);
    }

    public ArrayList<ObitoModel> obterObitosParaTabela() {
        ArrayList<ObitoEntity> obitos = obitoRepository.listar();
        ArrayList<ObitoModel> modelos = new ArrayList<>();

        for (ObitoEntity obito : obitos) {
            modelos.add(new ObitoModel(obito.getDataObito(), obito.getCpfPaciente()));
        }
        return modelos;
    }

}
