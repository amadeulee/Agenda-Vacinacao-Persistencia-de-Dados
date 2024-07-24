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
import java.util.List;
import javax.persistence.EntityManager;
import org.example.comparator.AgendaComparator;
import org.example.dao.AlergiaDAO;
import org.example.dao.UsuarioDAO;
import org.example.entity.Agenda;
import org.example.entity.Alergia;
import org.example.entity.UF;
import org.example.entity.Usuario;
import org.example.persistence.EntityManagerFactory;

@WebServlet("/usuario")
public class ControleUsuario extends HttpServlet {
    EntityManager em = EntityManagerFactory.getEntityManager();
    UsuarioDAO usuarioDAO = new UsuarioDAO(em);
    AlergiaDAO alergiaDAO = new AlergiaDAO(em);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if (tipoAcao.equals("listar")) {
            listarUsuarios(request, response);
        } else if (tipoAcao.equals("paginaIncluir")) {
            renderizarPaginaDeIncluirUsuario(request, response);
        } else if (tipoAcao.equals("listarAgendas")) {
            listarAgendasDoUsuario(request, response);
        } else if (tipoAcao.equals("excluir")) {
            excluirUsuario(request, response);
        } else if (tipoAcao.equals("listarAlergias")) {
            listarAlergiasDeUmUsuario(request, response);
        }
    }

    private void listarAlergiasDeUmUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));

        var usuario = usuarioDAO.findById(numero);

        request.setAttribute("usuario", usuario.getNome());
        request.setAttribute("alergiasDoUsuario", usuario.getAlergias());

        RequestDispatcher rd =
                request.getRequestDispatcher("listarAlergiasDeUmUsuario.jsp");

        rd.forward(request, response);
    }

    private void listarAgendasDoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));

        Usuario usuario = usuarioDAO.findById(numero);
        List<Agenda> agendasDoUsuario = usuario.getAgendas();
        agendasDoUsuario.sort(new AgendaComparator());
        request.setAttribute("nomeUsuario", usuario.getNome());
        request.setAttribute("agendas", agendasDoUsuario);

        RequestDispatcher rd =
                request.getRequestDispatcher("listarAgenda.jsp");

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoAcao = request.getParameter("acao");
        response.setDateHeader("Expires", 0);
        if (tipoAcao.equals("incluir")) {
            incluirUsuario(request, response);
        }
    }

    private void incluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("usuarioNome");
        LocalDate dataNascimento = LocalDate.parse(request.getParameter("usuarioDataNascimento"));
        char sexo = request.getParameter("usuarioSexo").charAt(0);
        String logradouro = request.getParameter("usuarioLogradouro");
        int numero = Integer.parseInt(request.getParameter("usuarioNumero"));
        String setor = request.getParameter("usuarioSetor");
        String cidade = request.getParameter("usuarioCidade");
        UF uf = UF.valueOf(request.getParameter("usuarioUf"));
        String[] alergias = request.getParameterValues("usuarioAlergias") != null ?
                request.getParameterValues("usuarioAlergias") :
                new String[0];

        List<Alergia> alergiaList = new ArrayList<>();
        for (String cadaAlergia : alergias) {
            Alergia alergiaSelecionado = alergiaDAO.findById(Integer.parseInt(cadaAlergia));
            alergiaList.add(alergiaSelecionado);
        }

        Usuario novoUsuario = new Usuario(nome, dataNascimento, alergiaList, sexo, logradouro, numero, setor, cidade, uf);
        usuarioDAO.save(novoUsuario);

        request.setAttribute("novoUsuario", nome);
        listarUsuarios(request, response);
    }

    private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        var usuarioNome = usuarioDAO.findById(numero).getNome();
        usuarioDAO.delete(numero);

        request.setAttribute("usuarioExcluido", usuarioNome);
        listarUsuarios(request, response);
    }

    private void renderizarPaginaDeIncluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Alergia> alergias = alergiaDAO.findAll();
        request.setAttribute("todasAlergias", alergias);

        request.setAttribute("sexos", List.of("M", "F"));
        request.setAttribute("ufs", UF.getAllValues());
        RequestDispatcher rd =
                request.getRequestDispatcher("criarUsuario.jsp");

        rd.forward(request, response);
    }

//    private void listarUsuarioDeUmaAlergia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int numero = Integer.parseInt(request.getParameter("numero"));
//
//        Alergia alergia = usuarioDAO.findById(numero);
//
//        request.setAttribute("alergia", alergia.getNome());
//        request.setAttribute("usuariosDaAlergia", alergia.getUsuarios());
//
//        RequestDispatcher rd =
//                request.getRequestDispatcher("listarUsuariosDeUmaAlergia.jsp");
//
//        rd.forward(request, response);
//    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.findAll();

        request.setAttribute("usuarios", usuarios);
        request.setAttribute("menu", "menu");

        RequestDispatcher rd =
                request.getRequestDispatcher("listarUsuario.jsp");

        rd.forward(request, response);
    }
}
