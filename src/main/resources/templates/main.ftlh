<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="container">
        <form method="get" action="/main" class="form-inline">
            <div class="row">
                <div class="col-6">
                    <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                           placeholder="Search by tag">
                </div>

                <div class="col-6">
                    <button type="submit" class="btn btn-primary ml-2">Search</button>
                </div>
            </div>
        </form>
    </div>

    <div class="container mt-3">
        <p>
            <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseMessage" role="button"
               aria-expanded="false"
               aria-controls="collapseMessage">
                Add new Message
            </a>
        <p>
    </div>
    <div class="container">
        <div class="collapse" id="collapseMessage">
            <div class="form-group">
                <form method="post" enctype="multipart/form-data">
                    <div class="input-group">
                        <input type="text" class="form-control" name="text"
                               placeholder="Введите сообщение" required minlength="1" maxlength="2048"/>

                    </div>
                    <div class=" input-group">
                        <input type="text" class="form-control" name="tag" placeholder="Тэг" required minlength="1" maxlength="2048">
                    </div>
                    <div class="input-group mb-3">
                        <input type="file" class="form-control" id="inputGroupFile02" name="file">
                        <label class="input-group-text" for="inputGroupFile02">Upload</label>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="input-group">
                        <button type="submit" class="btn btn-primary ml-2">Добавить</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="card-columns">
            <#list messages as message>
                <div class="card my-3">
                    <div>
                        <#if message.filename??>
                            <img src="/img/${message.filename}" class="card-img-top">
                        </#if>
                    </div>
                    <div class="m-2">
                        <span>${message.text}</span>
                        <i>${message.tag}</i>
                    </div>

                    <div class="card-footer text-muted">
                        ${message.authorName!" "}
                    </div>


                </div>
            <#else>
                No message
            </#list>
        </div>

    </div>


</@c.page>