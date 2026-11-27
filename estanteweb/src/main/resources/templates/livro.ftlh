<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>${livro.titulo} - EstanteWeb</title>
</head>
<body>

    <h1>${livro.titulo}</h1>

    <p><strong>Autor:</strong> ${livro.autor}</p>
    <p><strong>Ano:</strong> ${livro.ano}</p>
    <p><strong>Preço:</strong> R$ ${livro.preco?string["0.00"]}</p>

    <p>
        <#if usuarioLogado??>
            <a href="/livros/${livro.id?c}/editar">Editar livro</a> | 
            <a href="/livros/${livro.id?c}/remover">Remover livro</a> | 
        </#if>
        <a href="/livros/html">Voltar para o acervo</a>
    </p>

    <#if outrosLivros?has_content>
        <h2>Outros títulos de ${livro.autor}</h2>
        <ul>
            <#list outrosLivros as outro>
                <li>
                    <a href="/livros/${outro.id?c}">${outro.titulo}</a> (${outro.ano})
                </li>
            </#list>
        </ul>
    </#if>

</body>
</html>
