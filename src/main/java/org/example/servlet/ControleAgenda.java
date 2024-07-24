package org.example.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.example.comparator.AgendaComparator;
import org.example.dao.AgendaDAO;
import org.example.dao.UsuarioDAO;
import org.example.dao.VacinaDAO;
import org.example.entity.Agenda;
import org.example.entity.Alergia;
import org.example.entity.Situacao;
import org.example.entity.Usuario;
import org.example.entity.Vacina;
import org.example.persistence.EntityManagerFactory;

@WebServlet("/agenda")
public class ControleAgenda extends HttpServlet {
    EntityManager em = EntityManagerFactory.getEntityManager();
    AgendaDAO agendaDAO = new AgendaDAO(em);
    VacinaDAO vacinaDAO = new VacinaDAO(em);
    UsuarioDAO usuarioDAO = new UsuarioDAO(em);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if (tipoAcao.equals("listar")) {
            listarAgendas(request, response);
        } else if (tipoAcao.equals("paginaIncluir")) {
            renderizarPaginaDeIncluirAgenda(request, response);
        } else if (tipoAcao.equals("mostrarUsuario")) {
            mostrarUsuario(request, response);
        } else if (tipoAcao.equals("mostrarVacina")) {
            mostrarVacina(request, response);
        } else if (tipoAcao.equals("realizado")) {
            marcarVacinaComoRealizado(request, response);
        } else if (tipoAcao.equals("cancelar")) {
            marcarVacinaComoCancelado(request, response);
        } else if (tipoAcao.equals("filtrar")) {
            filtraVacinas(request, response);
        }
    }

    private void filtraVacinas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoFiltro = request.getParameter("tipo");
        List<Agenda> agendas = agendaDAO.findAll();
        List<Agenda> agendasFiltradas;
        if (tipoFiltro.equals("data")) {
            LocalDate data = LocalDate.parse(request.getParameter("agendaData"));
            agendasFiltradas = agendas.stream()
                    .filter(agenda -> agenda.getData().equals(data))
                    .collect(Collectors.toList());
        } else if (tipoFiltro.equals("realizado")) {
            agendasFiltradas = agendas.stream()
                    .filter(agenda -> agenda.getSituacao().equals(Situacao.REALIZADO))
                    .collect(Collectors.toList());
        } else if (tipoFiltro.equals("cancelado")) {
            agendasFiltradas = agendas.stream()
                    .filter(agenda -> agenda.getSituacao().equals(Situacao.CANCELADO))
                    .collect(Collectors.toList());
        } else agendasFiltradas = Collections.emptyList();

        request.setAttribute("agendas", agendasFiltradas);
        RequestDispatcher rd =
                request.getRequestDispatcher("listarAgenda.jsp");

        rd.forward(request, response);
    }

    private void marcarVacinaComoCancelado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));

        var agenda = agendaDAO.findById(numero);
        em.getTransaction().begin();
        agenda.setDataSituacao(LocalDate.now());
        agenda.setSituacao(Situacao.CANCELADO);
        em.getTransaction().commit();

        listarAgendas(request, response);
    }

    private void marcarVacinaComoRealizado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));

        var agenda = agendaDAO.findById(numero);
        em.getTransaction().begin();
        agenda.setDataSituacao(LocalDate.now());
        agenda.setSituacao(Situacao.REALIZADO);
        em.getTransaction().commit();

        listarAgendas(request, response);
    }

    private void mostrarVacina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        var usuario = agendaDAO.findById(numero).getVacina();
        List<Vacina> vacinas = new ArrayList<>(List.of(usuario));

        request.setAttribute("vacinas", vacinas);
        RequestDispatcher rd =
                request.getRequestDispatcher("listarVacina.jsp");

        rd.forward(request, response);
    }

    private void mostrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        var usuario = agendaDAO.findById(numero).getUsuario();
        List<Usuario> usuarios = new ArrayList<>(List.of(usuario));

        request.setAttribute("usuarios", usuarios);
        request.setAttribute("menuAgenda", "menuAgenda");
        RequestDispatcher rd =
                request.getRequestDispatcher("listarUsuario.jsp");

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoAcao = request.getParameter("acao");
        response.setDateHeader("Expires", 0);
        if (tipoAcao.equals("incluir")) {
            incluirAgendas(request, response);
        }
    }

    private void incluirAgendas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate dataConsulta = LocalDate.parse(request.getParameter("agendaData"));
        int idUsuario = Integer.parseInt(request.getParameter("agendaUsuario"));
        int idVacina = Integer.parseInt(request.getParameter("agendaVacina"));
        String observacoes = request.getParameter("agendaObservacoes");
        String horaInicio = request.getParameter("horaInicio");


        Vacina vacinaSelecionada = vacinaDAO.findById(idVacina);
        Usuario usuarioSelecionado = usuarioDAO.findById(idUsuario);

        int doses = vacinaSelecionada.getDoses();

        for (int doseIndex = 0; doseIndex < doses; doseIndex++) {
            Agenda novaAgenda = new Agenda(dataConsulta, horaInicio, Situacao.AGENDADO, vacinaSelecionada, usuarioSelecionado, observacoes);
            agendaDAO.save(novaAgenda);

            dataConsulta = calcularProximaDataVacina(dataConsulta, vacinaSelecionada.getPeriodicidade(), vacinaSelecionada.getIntervalo());
        }

        request.setAttribute("usuarioComAgenda", usuarioSelecionado.getNome());
        request.setAttribute("quantidadeAgenda", doses);
        listarAgendas(request, response);
    }

    private void renderizarPaginaDeIncluirAgenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.findAll();
        List<Vacina> vacinas = vacinaDAO.findAll();
        request.setAttribute("todosUsuarios", usuarios);
        request.setAttribute("todasVacinas", vacinas);

        RequestDispatcher rd =
                request.getRequestDispatcher("criarAgenda.jsp");

        rd.forward(request, response);
    }

//    private void listarUsuarioDeUmaAlergia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int numero = Integer.parseInt(request.getParameter("numero"));
//
//        Alergia alergia = alergiaDAO.findById(numero);
//
//        request.setAttribute("alergia", alergia.getNome());
//        request.setAttribute("usuariosDaAlergia", alergia.getUsuarios());
//
//        RequestDispatcher rd =
//                request.getRequestDispatcher("listarUsuariosDeUmaAlergia.jsp");
//
//        rd.forward(request, response);
//    }

    private void listarAgendas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Agenda> agendas = agendaDAO.findAll();

        agendas.sort(new AgendaComparator());
        request.setAttribute("agendas", agendas);
        RequestDispatcher rd =
                request.getRequestDispatcher("listarAgenda.jsp");

        rd.forward(request, response);
    }

    public LocalDate calcularProximaDataVacina(LocalDate dataAtual, int periodicidade, int intervalo) {
        return switch (periodicidade) {
            case 0 -> // Dias
                    dataAtual.plusDays(intervalo);
            case 1 -> // Semanas
                    dataAtual.plusWeeks(intervalo);
            case 2 -> // Meses
                    dataAtual.plusMonths(intervalo);
            case 3 -> // Anos
                    dataAtual.plusYears(intervalo);
            default -> throw new IllegalArgumentException("Periodicidade inválida.");
        };
    }
}
