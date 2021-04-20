<%@ page import="com.luox6.FarmerMarket.model.Farmer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String city = (String) request.getAttribute("city");
    String state = (String) request.getAttribute("state");
    String zipcode = (String) request.getAttribute("zipcode");
    String distance = (String) request.getAttribute("distance");
    List<Farmer> ls = (List<Farmer>) request.getAttribute("ls");
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>Search Farmer</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<script>
    <% if (error != null) { %>
    alert("<%=error%>")
    <% } %>
</script>
<body>

<h2>Search Farmers</h2>
<p>Note: if you have zipcode entered, city and state will be ignored.</p>
<form method="get">
    <label for="city">City (Full Name): </label>
    <input id="city" value="<%=city == null ? "" : city%>" type="text" name="city"/>

    <label for="state">State (e.g. NY): </label>
    <input id="state" value="<%=state == null ? "" : state%>" maxlength="2" type="text" name="state"/>

    <label for="zipcode">Zip Code (e.g. 12180): </label>
    <input id="zipcode" value="<%=zipcode == null ? "" : zipcode%>" maxlength="5" type="text" name="zipcode"/>

    <label for="distance">Distance (in miles): </label>
    <input id="distance" value="<%=distance == null ? "" : distance%>" type="number" name="distance"/>

    <input value="Submit Comment" type="submit"/>
</form>

<h2>Result: </h2>
<div>
    <table>
        <thead>
        <tr>
            <th>Market Name</th>
            <th>City</th>
            <th>State</th>
            <th>Zipcode</th>
            <th>Website</th>
        </tr>
        </thead>
        <tbody>
        <% for (Farmer f : ls) { %>
        <tr>
            <td>
                <a href="../detail?fmid=<%=f.getFMID()%>"><%= f.getMarketName() %></a>
            </td>
            <td>
                <%= f.getCity() %>
            </td>
            <td>
                <%= f.getState() %>
            </td>
            <td>
                <%= f.getZip() %>
            </td>
            <td>
                <% if (!f.getWebsite().isEmpty()) { %>
                <a href="<%= f.getWebsite() %>">Visit Website</a>
                <% } else { %>
                No Website
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
