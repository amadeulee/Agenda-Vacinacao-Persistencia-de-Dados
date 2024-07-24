<%@page import="java.util.List"%>
<%@page import="org.example.entity.Alergia"%>
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
	<h1>Lista de alergias cadastrados</h1>

	<%
		String novo = (String) request.getAttribute("novaAlergia");
		if (novo != null && novo != "") {
			out.println("<b>A alergia " + novo + " foi cadastrado com sucesso!!</b>");
		}

		String excluido = (String) request.getAttribute("cursoexcluido");
		if (excluido != null && excluido != "") {
			out.println("A alergia <b>" + excluido + "</b> foi excluída!!");
		}

		String alergiaComVinculo = (String) request.getAttribute("alergiaComVinculo");
        if (alergiaComVinculo != null && alergiaComVinculo != "") {
            out.println("A alergia <b>" + alergiaComVinculo + "</b> nao pode ser removida por possuir vinculo com usuario!!");
        }
	%>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Nome</td>
		<td></td>
	</tr>
	<%
		List<Alergia> listaAlergia = (List<Alergia>)request.getAttribute("alergias");
		for(Alergia alergia : listaAlergia){
			out.println("<tr>");
			out.println("<td>" + alergia.getId() + "</td>");
			out.println("<td>" + alergia.getNome() + "</td>");
			out.println("<td>");

			out.println("<a href='controlecurso?acao=editar&numero="
					+ alergia.getId()
					+"'> <img width='15px' height='15px' src='icones/edit.png'/></a>");

			out.println("&nbsp;&nbsp;<a href='alergia?acao=excluir&numero="
						+ alergia.getId()
						+"'><img width='15px' height='15px' src='icones/delete.png'/> </a>");

            out.println("&nbsp;&nbsp;<a href='alergia?acao=listarUsuario&numero="
						+ alergia.getId()
						+"'><img width='15px' height='15px' src='icones/images.png'/> </a>");
			out.println("</td>");
			out.println("</tr>");
		}
	%>
	</table>

	<br>
	<a href="index.html">Voltar para menu</a>
</body>
</html>