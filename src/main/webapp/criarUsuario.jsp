<%@page import="java.util.List"%>
<%@page import="org.example.entity.Alergia"%>
<%@page import="org.example.entity.Usuario"%>
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
        <form action="usuario" method="post">
            <input type="hidden" name="acao" value="incluir"/>

            <div class="mb-3">
                <label for="usuarioNome" class="form-label">Nome:</label>
                <input type="text" class="form-control" name="usuarioNome" id="usuarioNome" required>
            </div>

            <div class="mb-3">
                <label for="usuarioDataNascimento" class="form-label">Data Nascimento:</label>
                <input type="date" class="form-control" name="usuarioDataNascimento" id="usuarioDataNascimento" required>
            </div>





            <!--<div class="mb-3">
                <label for="usuarioSexo" class="form-label">Sexo (M ou F):</label>
                <input type="text" class="form-control" name="usuarioSexo" id="usuarioSexo" pattern="^[MFmf]$" title="Por favor, insira M ou F" required>
            </div>-->


            <div class="mb-3">
                <label for="usuarioSexo" class="form-label">Sexo (M ou F):</label>
                <select name="usuarioSexo" class="form-select rounded" required>
                    <%
                    List<String> sexos = (List<String>)request.getAttribute("sexos");
                    for(String sexo : sexos){
                        out.println("<option value=\"" + sexo + "\">" + sexo + "</option>");
                    }
                    %>
                </select>
            </div>





            <div class="mb-3">
                <label for="usuarioLogradouro" class="form-label">Logradouro:</label>
                <input type="text" class="form-control" name="usuarioLogradouro" id="usuarioLogradouro">
            </div>

            <div class="mb-3">
                <label for="usuarioNumero" class="form-label">Número:</label>
                <input type="number" class="form-control" name="usuarioNumero" id="usuarioNumero">
            </div>

            <div class="mb-3">
                <label for="usuarioSetor" class="form-label">Setor:</label>
                <input type="text" class="form-control" name="usuarioSetor" id="usuarioSetor">
            </div>

            <div class="mb-3">
                <label for="usuarioCidade" class="form-label">Cidade:</label>
                <input type="text" class="form-control" name="usuarioCidade" id="usuarioCidade">
            </div>


            <div class="mb-3">
                <label for="usuarioUf" class="form-label">UF:</label>
                <select name="usuarioUf" class="form-select rounded" required>
                    <%
                    List<String> ufs = (List<String>)request.getAttribute("ufs");
                    for(String uf : ufs){
                        out.println("<option value=\"" + uf + "\">" + uf + "</option>");
                    }
                    %>
                </select>
            </div>




            <!--<div class="mb-3">
                <label for="usuarioUf" class="form-label">UF:</label>
                <input type="text" class="form-control" name="usuarioUf" id="usuarioUf">
            </div>-->






            <div class="mb-3">
                <label for="usuarioAlergias" class="form-label">Alergias:</label>
                <select name="usuarioAlergias" class="form-select rounded" multiple>
                    <%
                    List<Alergia> listaAlergia = (List<Alergia>)request.getAttribute("todasAlergias");
                    for(Alergia alergia : listaAlergia){
                        out.println("<option value=\"" + alergia.getId() + "\">" + alergia.getNome() + "</option>");
                    }
                    %>
                </select>
            </div>

            <div class="mb-3">
                <input type="submit" class="btn btn-primary" value="Gravar">
            </div>
        </form>
    </div>


</body>
</html>