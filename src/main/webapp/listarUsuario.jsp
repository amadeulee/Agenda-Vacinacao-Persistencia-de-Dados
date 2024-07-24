<%@page import="java.util.List"%>
<%@page import="org.example.entity.Alergia"%>
<%@page import="org.example.entity.Usuario"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de vacinação</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

</head>
<body>
	<h1>Lista de usuarios cadastrados</h1>

	<%
		String novo = (String) request.getAttribute("novoUsuario");
		if (novo != null && novo != "") {
			out.println("<b>O usuario " + novo + " foi cadastrado com sucesso!!</b>");
		}

		String excluido = (String) request.getAttribute("usuarioExcluido");
		if (excluido != null && excluido != "") {
			out.println("O curso <b>" + excluido + "</b> foi excluído!!");
		}
	%>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Nome</td>
		<td>Data de nascimento</td>
		<td>Sexo</td>
		<td>Logradouro</td>
		<td>Numero</td>
		<td>Setor</td>
		<td>Cidade</td>
		<td>UF</td>
		<td></td>
	</tr>
	<%
		List<Usuario> listaUsuario = (List<Usuario>)request.getAttribute("usuarios");
		DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(Usuario usuario : listaUsuario){
			out.println("<tr>");
			out.println("<td>" + usuario.getId() + "</td>");
			out.println("<td>" + usuario.getNome() + "</td>");
			out.println("<td>" + usuario.getDataNascimento().format(formatoSaida) + "</td>");
			out.println("<td>" + usuario.getSexo() + "</td>");
			out.println("<td>" + usuario.getLogradouro() + "</td>");
			out.println("<td>" + usuario.getNumero() + "</td>");
			out.println("<td>" + usuario.getSetor() + "</td>");
			out.println("<td>" + usuario.getCidade() + "</td>");
			out.println("<td>" + usuario.getUf() + "</td>");

			out.println("<td>");

			out.println("<a href='controlecurso?acao=editar&numero="
					+ usuario.getId()
					+"'> <img width='15px' height='15px' src='icones/edit.png'/></a>");

			out.println("&nbsp;&nbsp;<a href='usuario?acao=excluir&numero="
						+ usuario.getId()
						+"'><img width='15px' height='15px' src='icones/delete.png'/> </a>");

		    out.println("&nbsp;&nbsp;<a href='usuario?acao=listarAlergias&numero="
                        + usuario.getId()
                        +"'><img width='15px' height='15px' src='icones/virus-icon.png'/> </a>");

            out.println("&nbsp;&nbsp;<a href='usuario?acao=listarAgendas&numero="
                        + usuario.getId()
                        +"'><img width='15px' height='15px' src='icones/calendar.png'/> </a>");
			out.println("</td>");
			out.println("</tr>");
		}
	%>
	</table>

	<br>

	<%
        String menu = (String) request.getAttribute("menu");
        if (menu != null && menu != "") {
            out.println("<a href=\"index.html\">Voltar para menu inicial</a>");
        }

        String menuAgenda = (String) request.getAttribute("menuAgenda");
        if (menuAgenda != null && menuAgenda != "") {
            out.println("<a href=\"agenda?acao=listar\">Voltar para lista de agendas</a>");
        }
    %>
</body>
</html>