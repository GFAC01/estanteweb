<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Livros</title>
</head>
<body>

<h1>Lista de Livros</h1>

<#if livros?size == 0>

    <p>Nenhum livro no acervo no momento.</p>

<#else>

    <ul>
        <#list livros as livro>
            <li>
                ${livro.titulo} - ${livro.autor} - R$ ${livro.preco?c}
            </li>
        </#list>
    </ul>

</#if>

</body>
</html>