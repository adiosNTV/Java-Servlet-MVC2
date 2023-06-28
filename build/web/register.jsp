<%-- 
    Document   : registerPage
    Created on : Mar 15, 2023, 8:40:39 AM
    Author     : ADMIN
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new Account</title>
    </head>
    <body>
        <h1>Create new Account</h1>
        <form action="registerAction" method="POST">
            <c:set var ="errors" value="${requestScope.CREATE_ERROR}"/>

            Username* <input type="text" name="txtUsername" 
                             value="${param.txtUsername}" />(6 - 20 chars)<br>
            <c:if test="${not empty errors.usernameLengthError}">
                <font color = "red">
                ${errors.usernameLengthError}
                </font><br>
            </c:if>

            <c:if test="${not empty errors.usernameIsExisted}">
                <font color = "red">
                ${errors.usernameIsExisted}
                </font><br>
            </c:if>        

            Password* <input type="password" name="txtPassword" value="" />(6 - 30 chars)<br>
            <c:if test="${not empty errors.passwordLengthError}">
                <font color = "red">
                ${errors.passwordLengthError}
                </font><br>
            </c:if>

            Confirm* <input type="password" name="txtConfirm" value="" /><br>
            <c:if test="${not empty errors.confirmNotMatchError}">
                <font color = "red">
                ${errors.confirmNotMatchError}
                </font><br/>
            </c:if>

            Full name* <input type="text" name="txtFullName" 
                              value="${param.txtFullName}" />(2 - 50 chars)<br>
            <c:if test="${not empty errors.fullnameLengthError}">
                <font color = "red">
                ${errors.fullnameLengthError}
                </font><br/>
            </c:if>

            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
    </body>
</html>
