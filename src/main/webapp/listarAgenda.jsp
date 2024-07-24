<%@page import="java.util.List"%>
<%@page import="org.example.entity.Agenda"%>
<%@page import="org.example.entity.Usuario"%>
<%@page import="org.example.entity.Vacina"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agenda de vacinação</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

<style>
    /* Adicione estilos personalizados aqui, se necessário */
    .custom-form {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        margin-bottom: 20px;
    }

    .custom-form .btn {
        margin-right: 20px;
    }

    .custom-datepicker {
        width: 150px;
        margin-right: 20px;
    }
</style>

</head>
<body>
    <h1>Lista de agendas cadastradas</h1>

    <!-- Adicione a classe "custom-form" para personalizar o layout do formulário -->
    <div class="custom-form">
        <form action="agenda" method="get" class="btn btn-primary">
            <input type="hidden" name="acao" value="filtrar"/>
            <input type="hidden" name="tipo" value="data"/>

            <!-- Adicione a classe "custom-datepicker" para personalizar o datepicker -->
            <div class="mb-3 custom-datepicker">
                <label for="agendaData" class="form-label">Filtrar por data:</label>
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dataAtual = sdf.format(new Date());
                %>
                <input type="date" class="form-control" name="agendaData" id="agendaData" required>
            </div>
            <input type="submit" class="btn" value="Filtrar">

            <!-- Adicione os outros campos conforme necessário -->

        </form>

        <!-- Adicionar botões como âncoras -->
        <div class="btn-group" role="group" aria-label="Filtros">
            <a href="agenda?acao=filtrar&tipo=realizado" class="btn btn-success">Realizados</a>
            <a href="agenda?acao=filtrar&tipo=cancelado" class="btn btn-warning">Cancelados</a>
            <a href="agenda?acao=listar" class="btn btn-secondary">Limpar filtro</a>
        </div>
    </div>

	<%
		String novo = (String) request.getAttribute("usuarioComAgenda");
		Integer agendasCriadas = (Integer) request.getAttribute("quantidadeAgenda");

		if (novo != null && novo != "") {
			out.println(agendasCriadas > 1 ?
			"<b>As agendas para o usuario " + novo + " foram criados com sucesso!!</b>" :
			"<b>A agenda para o usuario " + novo + " foi criado com sucesso!!</b>");
		}

		String excluido = (String) request.getAttribute("cursoexcluido");
		if (excluido != null && excluido != "") {
			out.println("O curso <b>" + excluido + "</b> foi excluído!!");
		}


	%>
	<table class="table table-striped">
	<tr>
		<td>Código</td>
		<td>Paciente</td>
		<td>Data</td>
		<td>Hora</td>
		<td>Situacao</td>
		<td>Data situacao</td>
		<td>Observacoes</td>
		<td></td>
	</tr>
	<%
		List<Agenda> listaAgenda = (List<Agenda>)request.getAttribute("agendas");
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for(Agenda agenda : listaAgenda){
			out.println("<tr>");
			out.println("<td>" + agenda.getId() + "</td>");
			out.println("<td>" + agenda.getUsuario().getNome() + "</td>");
			out.println("<td>" + agenda.getData().format(formatoSaida) + "</td>");
			out.println("<td>" + agenda.getHora() + "</td>");
			out.println("<td>" + agenda.getSituacao() + "</td>");
			out.println("<td>" + agenda.getDataSituacao() + "</td>");
			out.println("<td>" + agenda.getObservacoes() + "</td>");
			out.println("<td>");


            out.println("<a href='agenda?acao=realizado&numero="
                    + agenda.getId()
                    +"'> <img width='15px' height='15px' src='icones/done.png'/></a>");

			out.println("&nbsp;&nbsp;<a href='agenda?acao=cancelar&numero="
						+ agenda.getId()
						+"'><img width='15px' height='15px' src='icones/delete.png'/> </a>");

			out.println("&nbsp;&nbsp;<a href='agenda?acao=mostrarVacina&numero="
                        + agenda.getId()
                        +"'><img width='15px' height='15px' src='icones/vaccine-icon.jpeg'/> </a>");

            out.println("&nbsp;&nbsp;<a href='agenda?acao=mostrarUsuario&numero="
						+ agenda.getId()
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