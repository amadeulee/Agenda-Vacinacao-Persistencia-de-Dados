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
        String alergia = (String) request.getAttribute("alergia");
        out.println("<h1> Lista de usuarios da alergia " + alergia + "</h1>");

    %>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Nome</td>
	</tr>
	<%
		List<Usuario> listaUsuario = (List<Usuario>)request.getAttribute("usuariosDaAlergia");
		for(Usuario usuario : listaUsuario){
			out.println("<tr>");
			out.println("<td>" + usuario.getId() + "</td>");
			out.println("<td>" + usuario.getNome() + "</td>");
			out.println("</tr>");
		}
	%>
	</table>

	<br>
	<a href="alergia?acao=listar">Voltar para lista de alergias</a>
</body>
</html>