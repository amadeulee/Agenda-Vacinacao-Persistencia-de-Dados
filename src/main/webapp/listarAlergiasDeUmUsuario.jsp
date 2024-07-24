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
    <%
        String usuario = (String) request.getAttribute("usuario");
        out.println("<h1> Lista de alergias do usuario " + usuario + "</h1>");

    %>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Nome</td>
	</tr>
	<%
		List<Alergia> listaAlergia = (List<Alergia>)request.getAttribute("alergiasDoUsuario");
		for(Alergia alergia : listaAlergia){
			out.println("<tr>");
			out.println("<td>" + alergia.getId() + "</td>");
			out.println("<td>" + alergia.getNome() + "</td>");
			out.println("</tr>");
		}
	%>
	</table>

	<br>
	<a href="usuario?acao=listar">Voltar para lista de usuarios</a>
</body>
</html>