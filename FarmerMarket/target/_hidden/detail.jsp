<%@ page import="com.luox6.FarmerMarket.model.Farmer" %>
<%@ page import="com.luox6.FarmerMarket.model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Farmer f = (Farmer) request.getAttribute("farmer");
    List<Comment> ls = (List<Comment>) request.getAttribute("comments");
%>
<html>
<head>
    <title>Market - <%= f.getMarketName() %></title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h2>Farmer Info</h2>
<div>
    <table>
        <thead>
        <tr>
            <th>Key</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Market Name</td>
            <td>
                <a href="/detail?fmid=<%=f.getFMID()%>"><%= f.getMarketName() %></a>
            </td>
        </tr>
        <tr>
            <td>Street</td>
            <td>
                <%= f.getStreet() %>
            </td>
        </tr>
        <tr>
            <td>City</td>
            <td>
                <%= f.getCity() %>
            </td>
        </tr>
        <tr>
            <td>State</td>
            <td>
                <%= f.getState() %>
            </td>
        </tr>
        <tr>
            <td>Zip Code</td>
            <td>
                <%= f.getZip() %>
            </td>
        </tr>
        <tr>
            <td>Website</td>
            <td>
                <% if (!f.getWebsite().isEmpty()) { %>
                <a href="<%= f.getWebsite() %>">Visit Website</a>
                <% } else { %>
                No Website
                <% } %>
            </td>
        </tr>
        <tr>
            <td>Rating (out of 5)</td>
            <td>
                <%= f.getRating() %>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<h2>Comments</h2>
<div>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Rating</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <% for (Comment c : ls) { %>
        <tr>
            <td>
                <%= c.getName() %>
            </td>
            <td>
                <%= c.getRating() %>
            </td>
            <td>
                <%= c.getDescription() == null ? "No description" : c.getDescription() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<h3>Leave your comment</h3>
<form method="post" onsubmit="send(event,this)">
    <label for="name">Your name:</label><br>
    <input id="name" maxlength="32" type="text" name="name"/><br>

    <label for="rating">Choose a rating:</label><br>
    <select name="rating" id="rating">
        <% for (int i = 1; i <= 5; i++) { %>
        <option value="<%=i%>"><%=i%></option>
        <% } %>
    </select><br>

    <label for="description">Description about your rating</label><br>
    <textarea id="description" name="description"></textarea><br><br>

    <input value="Submit Comment" type="submit"/>
</form>

<script>
function send(e,form) {
    e.preventDefault();
    fetch(window.location.href, { method: 'post', body: new URLSearchParams(new FormData(form)) }).then(res => {
        return res.text().then(t => {
            if (t !== 'success') {
                alert(t)
            } else {
                alert('send success!')
                location.reload();
            }
        })
    }).catch(e => {
        console.log(e)
        alert('send failed, please try again!')
    })
}
</script>
</body>
</html>
