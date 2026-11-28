<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>Sugestões</title>
</head>
<body>

    <h1>Sugestões de Livros</h1>

    <!-- Formulário de busca em Sugestões (Missão 4) -->
    <form method="get" action="/sugestoes" style="margin-bottom: 20px;">
        <label>Nome do cliente:</label>
        <input type="text" name="nomeCliente" value="${nomeCliente!}" placeholder="Nome do cliente...">

        <label style="margin-left: 10px;">Livro sugerido:</label>
        <input type="text" name="livroSugerido" value="${livroSugerido!}" placeholder="Livro sugerido...">

        <button type="submit">Buscar</button>
        <#if temFiltro!false>
            <a href="/sugestoes" style="margin-left: 10px;">Limpar filtros</a>
        </#if>
    </form>

    <#if temFiltro!false>
        <p><strong>Resultado da busca:</strong> ${sugestoes?size} sugestão(ões) encontrada(s).</p>
    <#else>
        <p>${sugestoes?size} sugestão(ões) registrada(s).</p>
    </#if>

    <#if sugestoes?size == 0>
        <#if temFiltro!false>
            <p>Nenhuma sugestão encontrada para os filtros informados (cliente: "${nomeCliente!}", livro: "${livroSugerido!}").</p>
        <#else>
            <p>Nenhuma sugestão cadastrada no momento.</p>
        </#if>
    <#else>
        <ul>
            <#list sugestoes as sugestao>
                <li>
                    <strong>${sugestao.nomeCliente}</strong> sugeriu: <em>${sugestao.livroSugerido}</em>
                </li>
            </#list>
        </ul>
    </#if>

</body>
</html>
