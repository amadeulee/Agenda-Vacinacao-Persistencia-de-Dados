<%@page import="java.util.List"%>
<%@page import="org.example.entity.Agenda"%>
<%@page import="org.example.entity.Usuario"%>
<%@page import="org.example.entity.Vacina"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda de Vacinação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-5">
        <form action="agenda" method="post" onsubmit="return validarHorario();">
            <input type="hidden" name="acao" value="incluir"/>

            <div class="mb-3">
                <label for="agendaData" class="form-label">Data da consulta:</label>
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dataAtual = sdf.format(new Date());
                %>
                <input type="date" class="form-control" name="agendaData" id="agendaData" min="<%= dataAtual %>" required>
            </div>


            <div class="mb-3">
                <label for="horaInicio" class="form-label">Hora da vacina:</label>
                <input type="time" class="form-control" name="horaInicio" id="horaInicio" min="09:00" max="18:00" required>
            </div>



            <div class="mb-3">
                <label for="agendaUsuario" class="form-label">Usuario:</label>
                <select name="agendaUsuario" class="form-select rounded" required>
                    <%
                    List<Usuario> listaUsuario = (List<Usuario>)request.getAttribute("todosUsuarios");
                    for(Usuario usuario : listaUsuario){
                        out.println("<option value=\"" + usuario.getId() + "\">" + usuario.getNome() + "</option>");
                    }
                    %>
                </select>
            </div>

            <div class="mb-3">
                <label for="agendaVacina" class="form-label">Vacina:</label>
                <select name="agendaVacina" class="form-select rounded" required>
                    <%
                    List<Vacina> listaVacina = (List<Vacina>)request.getAttribute("todasVacinas");
                    for(Vacina vacina : listaVacina){
                        out.println("<option value=\"" + vacina.getId() + "\">" + vacina.getTitulo() + "</option>");
                    }
                    %>
                </select>
            </div>

            <div class="mb-3">
                <label for="agendaObservacoes" class="form-label">Observacoes:</label>
                <input type="text" class="form-control" name="agendaObservacoes" id="agendaObservacoes">
            </div>

            <div class="mb-3">
                <input type="submit" class="btn btn-primary" value="Gravar">
            </div>
        </form>
    </div>

    <script>
        function validarHorario() {
            var horaInicio = document.getElementById('horaInicio').value;
            var horaFim = document.getElementById('horaFim').value;

            if (horaInicio >= horaFim) {
                alert('A hora de início deve ser anterior à hora de término.');
                return false;
            }

            return true;
        }
    </script>

</body>
</html>