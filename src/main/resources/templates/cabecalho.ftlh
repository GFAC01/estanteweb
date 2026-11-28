<header>
    <h2>EstanteWeb</h2>
    <a href="/">Voltar para o início</a>

    <div style="margin-top: 10px;">
        <#if usuarioLogado??>
            <span>Olá, ${usuarioLogado.nome}!</span>
            <form method="post" action="/logout" style="display: inline; margin-left: 10px;">
                <button type="submit">Sair</button>
            </form>
        <#else>
            <a href="/login" style="margin-left: 10px;">Entrar</a>
        </#if>
    </div>
</header>

<hr>
