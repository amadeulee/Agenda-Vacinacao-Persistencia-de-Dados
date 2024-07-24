<%@page import="java.util.List"%>
<%@page import="org.example.entity.Vacina"%>
<%@page import="org.example.entity.Usuario"%>
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
	<h1>Lista de vacinas cadastradas</h1>

	<%
		String novo = (String) request.getAttribute("vacinaTitulo");
		if (novo != null && novo != "") {
			out.println("<b>Vacina contra " + novo + " foi cadastrado com sucesso!!</b>");
		}

		String excluido = (String) request.getAttribute("vacinaExcluida");
		if (excluido != null && excluido != "") {
			out.println("A vacina <b>" + excluido + "</b> foi excluída!!");
		}
	%>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Titulo</td>
		<td>Descricao</td>
		<td>Doses</td>
		<td>Periodicidade</td>
		<td>Intervalo</td>
		<td></td>
	</tr>
	<%
		List<Vacina> listaVacina = (List<Vacina>)request.getAttribute("vacinas");
		for(Vacina vacina : listaVacina){
			out.println("<tr>");
			out.println("<td>" + vacina.getId() + "</td>");
			out.println("<td>" + vacina.getTitulo() + "</td>");
			out.println("<td>" + vacina.getDescricao() + "</td>");
			out.println("<td>" + vacina.getDoses() + "</td>");
			out.println("<td>" + vacina.getPeriodicidade() + "</td>");
			out.println("<td>" + vacina.getIntervalo() + "</td>");

			out.println("<td>");

			out.println("<a href='controlecurso?acao=editar&numero="
					+ vacina.getId()
					+"'> <img width='15px' height='15px' src='icones/edit.png'/></a>");

			out.println("&nbsp;&nbsp;<a href='vacina?acao=excluir&numero="
						+ vacina.getId()
						+"'><img width='15px' height='15px' src='icones/delete.png'/> </a>");
			out.println("</td>");
			out.println("</tr>");
		}
	%>
	</table>

	<br>
	<a href="index.html">Voltar para menu</a>
</body>
</html>