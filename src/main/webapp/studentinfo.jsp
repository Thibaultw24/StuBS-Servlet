<%@page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>StuBS</title>
    <style>
    .border {
        border: 1px solid black;
        border-collapse: collapse
    }
    th, td {
        padding: 5px;
    }
    </style>
</head>
<body>
<h1>StuBS - Het Studenten Beheer Systeem</h1>
<p><span style="color: green; "><b>${infoMsg}</b></span><span style="color: red; "><b>${errMsg}</b></span></p>
<h2>Student CRUD operaties</h2>
<form action="/stubs/" method="POST">
    <table>
        <tr>
            <td>Student ID</td>
            <td>
                <input type="text" name="studentId" value="${student.id}">
                &nbsp;(in te vullen bij wijzigen of verwijderen)
            </td>
        </tr>
        <tr>
            <td>Naam</td>
            <td><input type="text" name="naam" value="${student.naam}" maxlength="128"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" name="actie" value="Toevoegen"/>
                <input type="submit" name="actie" value="Wijzigen"/>
                <input type="submit" name="actie" value="Verwijderen"/>
            </td>
        </tr>
    </table>
</form>
<hr/>
<h2>Studenten opzoeken</h2>
<form action="/stubs/" method="POST">
    <table>
        <tr>
            <td>Student ID</td>
            <td><input type="text" name="studentId" value="${student.id}"/></td>
        </tr>
        <tr>
            <td>Naam</td>
            <td><input type="text" name="naam" value="${student.naam}"/></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="submit" name="actie" value="Zoeken"/>
            </td>
        </tr>
    </table>
</form>
<h4>Gevonden studenten:</h4>
<table class="border">
    <th class="border">ID</th>
    <th class="border">Naam</th>
    <c:forEach items="${allStudents}" var="stud">
        <tr>
            <td class="border">${stud.id}</td>
            <td class="border">${stud.naam}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
