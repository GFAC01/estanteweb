<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>Editar Livro - ${livro.titulo!}</title>
</head>
<body>

    <h1>Editar Livro</h1>

    <#if erro??>
        <p style="color: red;">${erro}</p>
    </#if>

    <form method="post" action="/livros/${livro.id?c}/editar">
        <div>
            <label>Título da obra:</label><br>
            <input type="text" name="titulo" value="${livro.titulo!}">
        </div>
        <br>
        <div>
            <label>Nome do Autor:</label><br>
            <input type="text" name="autor" value="${livro.autor!}">
        </div>
        <br>
        <div>
            <label>Ano de criação:</label><br>
            <input type="number" name="ano" value="${livro.ano?c}">
        </div>
        <br>
        <div>
            <label>Preço (R$):</label><br>
            <input type="number" step="0.01" name="preco" value="${livro.preco?string['0.00']}">
        </div>
        <br>
        <button type="submit">Confirmar alterações</button>
        <a href="/livros/${livro.id?c}">Cancelar</a>
    </form>

</body>
</html>
