<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=true displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "form">
        <#if realm.password>
            <form onsubmit="login.disabled=true;return true;" action="${url.loginAction}" method="post">

                <div class="form-group">
                    <label class="form-label" for="username">
                        <#if !realm.loginWithEmailAllowed>${msg("username")}
                        <#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}
                        <#else>${msg("email")}</#if>
                    </label>
                    <input tabindex="1" id="username" class="form-control " name="username"
                            value="${(login.username!'')}" type="text" autofocus autocomplete="off"/>
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">${msg("password")}</label>
                    <input tabindex="2" id="password" class="form-control " name="password"
                            type="password" autocomplete="off"/>
                </div>

                <div class="form-group text-right">
                    <#if realm.resetPasswordAllowed>
                        <a tabindex="5" href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a>
                    </#if>
                </div>

                <div id="form-buttons" class="form-group">
                    <input type="hidden" id="id-hidden-input" name="credentialId"
                            <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                    <input tabindex="4"
                            class="btn btn-primary btn-block"
                            name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
                </div>
            </form>
        </#if>

        <div class="footer-text">
            No account? <a href="/auth/realms/${realm.name}/protocol/openid-connect/registrations?client_id=account&response_type=code">Click here</a> to register now.<br>
            Need additional help? <a href="https://sso-info.il2.dso.mil/" target="_blank">Click here</a> or <a
                    id="helpdesk" href="mailto:help@dsop.io">email us</a>
        </div>
    </#if>

</@layout.registrationLayout>

<script>
    const feedback = document.getElementById('alert-error');
    if (feedback && feedback.innerHTML.indexOf('X509 certificate') > -1 && feedback.innerHTML.indexOf('Invalid user') > -1) {
        feedback.outerHTML = [
            '<div class="alert alert-info cac-info">',
            '<h2>New DoD PKI Detected</h2>',
            '<div>If you do not have an account yet, <a href="/auth/realms/${realm.name}/protocol/openid-connect/registrations?client_id=account&response_type=code">click to register</a> now.  Otherwise, please login with your username/password to associate this CAC with your existing account.',
            '</div></div>'
        ].join('');
    }
</script>
