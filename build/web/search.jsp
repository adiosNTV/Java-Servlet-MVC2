<%-- 
    Document   : search.jsp
    Created on : Mar 14, 2023, 11:02:19 AM
    Author     : ADMIN
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <font color="red">
             Welcome, ${sessionScope.USER.username}
        </font>
        <h1>Search Value</h1>
        <form action="searchAction" method="POST">
            Search text <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
            <input type="submit" value="Search" name="btnAction" />
        </form><br>
        <c:set var ="searchValue" value="${param.txtSearchValue}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <form action="updateAccountAction" method="POST">
                            <tr>
                                <td>
                                    <div style="text-align: right">
                                    ${counter.count}
                                    </div>
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" 
                                           value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${dto.password}" required/>
                                </td>
                                <td>
                                    ${dto.fullname}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <c:if  test="${dto.admin}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <c:url var ="deleteLink" value="deleteAccountAction">
                                        <c:param name="btAction" value="delete"/>
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="update" name="btnAction" />
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty requestScope.UPDATE_PASSWORD_ERROR}">
                <c:set var="error" value="${requestScope.UPDATE_PASSWORD_ERROR}"></c:set>
                <c:if test="${not empty error.updatePassWordLengthError}">
                    <font color = "red">
                        ${error.updatePassWordLengthError}
                    </font><br>
                </c:if>
            </c:if>

        </c:if >
        <c:if test="${empty result}">
            <h2>
                No record is matched!!!
            </h2>
        </c:if>
    </c:if>
    <form action="logoutAction" method="POST">
        <input type="submit" value="LogOut" />
    </form>
</body>
</html>
