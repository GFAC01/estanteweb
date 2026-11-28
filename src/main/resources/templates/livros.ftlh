<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>Acervo de Livros</title>
</head>
<body>

    <h1>Acervo de Livros</h1>

    <!-- Formulário de busca (Missões 1, 2 e 3) -->
    <form method="get" action="/livros/html" style="margin-bottom: 20px;">
        <label>Buscar por título:</label>
        <input type="text" name="q" value="${q!}" placeholder="Parte do título...">

        <label style="margin-left: 10px;">Autor:</label>
        <input type="text" name="autor" value="${autor!}" placeholder="Nome do autor...">

        <button type="submit">Buscar</button>
        <#if temFiltro!false>
            <a href="/livros/html" style="margin-left: 10px;">Limpar filtros</a>
        </#if>
    </form>

    <#if usuarioLogado??>
        <p>
            <a href="/livros/novo">+ Cadastrar novo livro</a>
        </p>
    </#if>

    <#if temFiltro!false>
        <p><strong>Resultado da busca:</strong> ${livros?size} livro(s) encontrado(s).</p>
    <#else>
        <p>${livros?size} livro(s) no acervo.</p>
    </#if>

    <#if livros?size == 0>
        <#if temFiltro!false>
            <p>Nenhum livro encontrado para os filtros informados (título: "${q!}", autor: "${autor!}").</p>
        <#else>
            <p>Nenhum livro no acervo no momento.</p>
        </#if>
    <#else>
        <ul>
            <#list livros as livro>
                <li>
                    <a href="/livros/${livro.id?c}">${livro.titulo}</a> - ${livro.autor} - R$ ${livro.preco?string["0.00"]}
                </li>
            </#list>
        </ul>
    </#if>

</body>
</html>
