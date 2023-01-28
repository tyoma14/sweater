<#import "parts/common.ftl" as c>

<@c.page>
User editor
<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}">
    <div>
        <#list roles as role>
            <label>
                <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                ${role}
            </label>
        </#list>
    </div>
    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Сохранить</button>
</form>
</@c.page>
