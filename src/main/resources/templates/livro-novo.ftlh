<header>
    <h2>EstanteWeb</h2>
    <a href="/">Voltar para o início</a>
</header>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/livros">
    <label>Titulo da obra</label>
    <input type="text" name="titulo" value="${titulo!}">

    <label>Nome do Autor</label>
    <input type="text" name="autor" value="${autor!}">

    <label>Ano de criação</label>
    <input type="number" name="ano" value="${ano!}">

    <label>Preço</label>
    <input type="number" name="preco" value="${preco!}">

    <button>Enviar</button>
</form>