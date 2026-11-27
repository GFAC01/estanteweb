<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <#include "cabecalho.ftlh">
    <title>Login - EstanteWeb</title>
</head>
<body>

    <h1>Entrar na EstanteWeb</h1>

    <#if erro??>
        <p style="color: red;">${erro}</p>
    </#if>

    <form method="post" action="/login">
        <div>
            <label>E-mail:</label><br>
            <input type="email" name="email" value="${email!}" required>
        </div>
        <br>
        <div>
            <label>Senha:</label><br>
            <input type="password" name="senha" required>
        </div>
        <br>
        <button type="submit">Entrar</button>
    </form>

</body>
</html>
