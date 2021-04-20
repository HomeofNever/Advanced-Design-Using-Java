<%@ page import="java.util.List" %>
<%@ page import="com.luox6.FarmerMarket.model.Farmer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Farmer> ls = (List<Farmer>) request.getAttribute("list");
    int current = (int) request.getAttribute("current_page");
    boolean hasNext = (boolean) request.getAttribute("has_next");
    boolean hasPrev = (boolean) request.getAttribute("has_prev");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Farmer Market</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<div>
    <table>
        <thead>
        <tr>
            <th>Market Name</th>
            <th>City</th>
            <th>State</th>
            <th>Zipcode</th>
            <th>Website</th>
            <th>Average Rating (out of 5)</th>
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
                <td>
                    <%= f.getRating() %>
                </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<div>
    <% if (hasPrev){ %>
    <a style="margin-left: 5px" href="?page=<%=current-1%>">Previous Page</a>
    <% } %>
    Current Page:
    <%= current %>
    <% if (hasNext) { %>
    <a style="margin-left: 5px" href="?page=<%=current+1%>">Next Page</a>
    <% } %>
</div>
</body>
</html>