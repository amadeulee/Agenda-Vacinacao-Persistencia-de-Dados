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
import org.example.dao.VacinaDAO;
import org.example.entity.Vacina;
import org.example.persistence.EntityManagerFactory;

@WebServlet("/vacina")
public class ControleVacina extends HttpServlet {
    EntityManager em = EntityManagerFactory.getEntityManager();
    VacinaDAO vacinaDAO = new VacinaDAO(em);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if (tipoAcao.equals("listar")) {
            listarVacinas(request, response);
        } else if (tipoAcao.equals("excluir")) {
            excluirVacina(request, response);
        }
    }

    private void excluirVacina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        var vacinaNome = vacinaDAO.findById(numero).getTitulo();
        vacinaDAO.delete(numero);

        request.setAttribute("vacinaExcluida", vacinaNome);
        listarVacinas(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        String tipoAcao = request.getParameter("acao");
        if(tipoAcao.equals("incluir")) {
            incluirVacina(request, response);
        }
    }

    private void incluirVacina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titulo = request.getParameter("vacinaTitulo");
        String descricao = request.getParameter("vacinaDescricao");
        int doses = Integer.parseInt(request.getParameter("vacinaDose"));
        int periodicidade = Integer.parseInt(request.getParameter("vacinaPeriodicidade"));
        int intervalo = Integer.parseInt(request.getParameter("vacinaIntervalo"));


        Vacina novaVacina = new Vacina(titulo, descricao, doses, periodicidade, intervalo);
        vacinaDAO.save(novaVacina);

        request.setAttribute("vacinaTitulo", titulo);
        listarVacinas(request, response);
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

    private void listarVacinas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vacina> vacinas = vacinaDAO.findAll();

        request.setAttribute("vacinas", vacinas);
        RequestDispatcher rd =
                request.getRequestDispatcher("listarVacina.jsp");

        rd.forward(request, response);
    }
}