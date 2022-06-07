<#import "template.ftl" as layout>
<@layout.mainLayout active='totp' bodyClass='totp'; section>

    <div class="row">
        <div class="col-md-10">
            <h2>${msg("authenticatorTitle")}</h2>
        </div>
    </div>

    <#if totp.enabled>
        <table class="table table-bordered table-striped">
            <thead>
            <#if totp.otpCredentials?size gt 1>
                <tr>
                    <th colspan="4">${msg("configureAuthenticators")}</th>
                </tr>
            <#else>
                <tr>
                    <th colspan="3">${msg("configureAuthenticators")}</th>
                </tr>
            </#if>
            </thead>
            <tbody>
            <#list totp.otpCredentials as credential>
                <tr>
                    <td class="provider">${msg("mobile")}</td>
                    <#if totp.otpCredentials?size gt 1>
                        <td class="provider">${credential.id}</td>
                    </#if>
                    <td class="provider">${credential.userLabel!}</td>
                    <td class="action">
                        <form action="${url.totpUrl}" method="post" class="form-inline">
                            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
                            <input type="hidden" id="submitAction" name="submitAction" value="Delete">
                            <input type="hidden" id="credentialId" name="credentialId" value="${credential.id}">
                            <button id="remove-mobile" class="btn btn-danger" data-toggle="tooltip" data-placement="right" title="Remove Authenticator">
                                <i class="pficon pficon-delete"></i>
                                <img src="${url.resourcesPath}/img/trash-fill.svg" alt="delete"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>

    <div class="row">

        <br>

        <ol>
            <li>
                <p>${msg("totpStep1")}</p>
                <ul>
                    <li>Google Authenticator</li>
                    <li>Authy</li>
                    <li>1Password</li>
                    <li>Bitwarden</li>
                    <li>LastPass</li>
                    <li>Yubico Authenticator</li>
                </ul>
            </li>

            <#if mode?? && mode = "manual">
                <li>
                    <p>${msg("totpManualStep2")}</p>
                    <p><span id="kc-totp-secret-key">${totp.totpSecretEncoded}</span></p>
                    <p><a href="${totp.qrUrl}" id="mode-barcode">${msg("totpScanBarcode")}</a></p>
                </li>
                <li>
                    <p>${msg("totpManualStep3")}</p>
                    <ul>
                        <li id="kc-totp-type">${msg("totpType")}: ${msg("totp." + totp.policy.type)}</li>
                        <li id="kc-totp-algorithm">${msg("totpAlgorithm")}: ${totp.policy.getAlgorithmKey()}</li>
                        <li id="kc-totp-digits">${msg("totpDigits")}: ${totp.policy.digits}</li>
                        <#if totp.policy.type = "totp">
                            <li id="kc-totp-period">${msg("totpInterval")}: ${totp.policy.period}</li>
                        <#elseif totp.policy.type = "hotp">
                            <li id="kc-totp-counter">${msg("totpCounter")}: ${totp.policy.initialCounter}</li>
                        </#if>
                    </ul>
                </li>
            <#else>
                <li>
                    <p>${msg("totpStep2")}</p>
                    <div class="note">${msg("totpStep2Note")}</div>
                    <p><img src="data:image/png;base64, ${totp.totpSecretQrCode}" alt="Figure: QR code"></p>
                    <p><a href="${totp.manualUrl}" id="mode-manual">${msg("totpUnableToScan")}</a></p>
                </li>
            </#if>
            <li>
                <p>${msg("totpStep3")}</p>
                <p><span class="font-italic">Note: The six digit code changes every 30 seconds. You will need the newly generated code each time you log in.</span></p>
            </li>
        </ol>

    </div>

    <form action="${url.totpUrl}" class="form-horizontal" method="post">
        <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
        <div class="form-group">
            <label for="totp" class="control-label">${msg("authenticatorCode")}</label>
            <input type="text" class="form-control" id="totp" name="totp" autocomplete="off" autofocus>
            <input type="hidden" id="totpSecret" name="totpSecret" value="${totp.totpSecret}"/>
        </div>

        <div class="form-group" ${messagesPerField.printIfExists('userLabel',properties.kcFormGroupErrorClass!)}">
            <label for="userLabel" class="control-label">${msg("totpDeviceName")}</label>
            <input type="text" class="form-control" id="userLabel" name="userLabel" autocomplete="off">
        </div>

        <div class="form-group">
            <div id="kc-form-buttons" class="text-right submit">
                <button type="submit"
                        class="btn btn-primary"
                        id="saveTOTPBtn" name="submitAction" value="Save">${msg("doSave")}
                </button>
                <button type="submit"
                        class="btn btn-light"
                        id="cancelTOTPBtn" name="submitAction" value="Cancel">${msg("doCancel")}
                </button>
            </div>
        </div>
    </form>
    </#if>

</@layout.mainLayout>
