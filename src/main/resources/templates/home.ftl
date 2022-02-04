<!DOCTYPE html>
<html>
    <head>
        <title>TopTen-Lists.com - Homepage</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/topten/static/topten.css">
    </head>
    <body>
        <h1>TopTen Lists</h1>
        <h2>The Best TopTen Lists on the Internet!</h2>

        <a href="?cmd=showList">Show me the first list!</a><br />
        <br />

        <table>
            <thead>
                <tr>
                    <th scope="col">Likes</th><th scope="col">Description</th><th scope="col">Views</th>
                </tr>
            </thead>
            <tbody>
                <#list topTenLists as topTenList>
                <tr>
                    <td class="list-likes">${topTenList.numLikes}</td>
                    <td class="list-description"><a href="?cmd=showList&index=${topTenList?index}">${topTenList.description}</a></td>
                    <td class="list-views">${topTenList.numViews}</td>
                </tr>
                </#list>
            </tbody>
        </table><br />
        <br />

        <#if loggedIn>
            <a href="?cmd=showAddList">Add a New List</a><br />
            <a href="?cmd=logout">Log Out</a>
        <#else>
            <a href="?cmd=showLogin">Log In</a><br />
            <a href="?cmd=showRegistration">Register</a>
        </#if>
    </body>
</html>
