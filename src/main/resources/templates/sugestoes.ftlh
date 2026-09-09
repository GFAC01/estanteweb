<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Sugestões</title>
</head>
<body>

<h1>Sugestões de Leitura</h1>

<#if sugestoes?size == 0>

    <p>Nenhuma sugestão cadastrada.</p>

<#else>

    <ul>
        <#list sugestoes as sugestao>
            <li>
                ${sugestao.nomeCliente} sugeriu
                <strong>${sugestao.livroSugerido}</strong>
            </li>
        </#list>
    </ul>

</#if>

<#if enviada?? && enviada>
    <p>Sugestão enviada com sucesso!</p>
</#if>

</body>
</html>