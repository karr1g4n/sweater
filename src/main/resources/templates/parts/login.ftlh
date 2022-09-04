<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid','')}"
                       placeholder="User name" required value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>

                </#if>
            </div>
        </div>
        <div class="form-group row mt-5 mb-5">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid','')}"
                       placeholder="Password" required>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row mt-5 mb-5">
                <label class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-6">
                    <input type="password" name="password2"
                           class="form-control ${(passwordError2??)?string('is-invalid','')}"
                           placeholder="Retype password" required>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row mt-5 mb-5">
                <label class="col-sm-2 col-form-label">email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid','')}"
                           placeholder="some@some.com" required value="<#if user??>${user.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="g-recaptcha" data-sitekey="6LfERr4hAAAAABQYQroELwDekrL8WTDouAAHU3qm"></div>
                <#if captchaError??>
                    <div class="alert alert-danger mt-3" role="alert">
                        ${captchaError}
                    </div>
                </#if>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a class="reg-link" href="/registration">Add new user</a></#if>
        <button class="btn btn-primary <#if isRegisterForm>mt-3</#if>" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form class="m" action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>

</#macro>