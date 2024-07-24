package org.example.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import org.example.dao.AgendaDAO;
import org.example.dao.AlergiaDAO;
import org.example.dao.UsuarioDAO;
import org.example.dao.VacinaDAO;
import org.example.entity.Alergia;
import org.example.persistence.EntityManagerFactory;

@WebServlet("/alergia")
public class ControleAlergia extends HttpServlet {
    EntityManager em = EntityManagerFactory.getEntityManager();
    AlergiaDAO alergiaDAO = new AlergiaDAO(em);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if (tipoAcao.equals("listar")) {
            listarAlergias(request, response);
        } else if (tipoAcao.equals("listarUsuario")) {
            listarUsuarioDeUmaAlergia(request, response);
        } else if (tipoAcao.equals("excluir")) {
            excluirAlergia(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if (tipoAcao.equals("incluir")) {
            incluirAlergia(request, response);
        }
    }


    private void incluirAlergia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("alergiaNome");
        Alergia novaAlergia = new Alergia(nome);

        alergiaDAO.save(novaAlergia);

        request.setAttribute("novaAlergia", nome);
        listarAlergias(request, response);
    }

    private void listarUsuarioDeUmaAlergia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));

        Alergia alergia = alergiaDAO.findById(numero);

        request.setAttribute("alergia", alergia.getNome());
        request.setAttribute("usuariosDaAlergia", alergia.getUsuarios());

        RequestDispatcher rd =
                request.getRequestDispatcher("listarUsuariosDeUmaAlergia.jsp");

        rd.forward(request, response);
    }

    private void listarAlergias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Alergia> alergias = alergiaDAO.findAll();

        request.setAttribute("alergias", alergias);
        RequestDispatcher rd =
                request.getRequestDispatcher("listarAlergia.jsp");

        rd.forward(request, response);
    }

    private void excluirAlergia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        var alergia = alergiaDAO.findById(numero);

        if (alergia.getUsuarios().isEmpty()) {
            alergiaDAO.delete(numero);
            request.setAttribute("cursoexcluido", alergia.getNome());
        } else {
            request.setAttribute("alergiaComVinculo", alergia.getNome());
        }
        listarAlergias(request, response);
    }
}
