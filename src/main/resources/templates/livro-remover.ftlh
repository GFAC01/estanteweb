<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>Remover Livro - ${livro.titulo!}</title>
</head>
<body>

    <h1>Remover Livro</h1>

    <p>Você tem certeza que deseja remover o seguinte livro do acervo?</p>

    <ul>
        <li><strong>Título:</strong> ${livro.titulo!}</li>
        <li><strong>Autor:</strong> ${livro.autor!}</li>
        <li><strong>Ano:</strong> ${livro.ano?c}</li>
        <li><strong>Preço:</strong> R$ ${livro.preco?string["0.00"]}</li>
    </ul>

    <form method="post" action="/livros/${livro.id?c}/remover">
        <button type="submit" style="color: red;">Confirmar remoção</button>
        <a href="/livros/${livro.id?c}">Desistir e voltar</a>
    </form>

</body>
</html>
