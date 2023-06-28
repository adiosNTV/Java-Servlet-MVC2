<%-- 
    Document   : order
    Created on : Mar 15, 2023, 11:10:43 AM
    Author     : ADMIN
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receipt</title>
    </head>
    <body>
        <h1>Receipt</h1>
        <c:set var="result" value="${requestScope.ORDER}" />
        <c:if test="${not empty result}">
            <h3>ID:${result.orderId}</h3>
            <h3>Name:${result.name}</h3>
            <h3>Address:${result.address}</h3>
            <h3>Date:${result.date}</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Amount</th>       
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty sessionScope.RECEIPT}">
                    <c:set var="items" value="${sessionScope.RECEIPT}" />
                        <c:if test ="${not empty items}">
                        <c:forEach var="item" items="${items}">
                                <tr>
                                    <td>
                                        ${item.name}
                                    </td>
                                    <td>
                                        <div style="text-align: right">
                                        ${item.quantity}
                                        </div>
                                    </td>
                                    <td>
                                        <div style="text-align: right">
                                        ${item.price}
                                        </div>
                                    </td>
                                    <td>
                                        <div style="text-align: right">
                                        ${item.total}
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <th colspan="3">
                                    Total
                                </th>
                                <th>
                                    <div style="text-align: right">
                                    ${result.total}
                                    </div>
                                </th>
                            </tr>
                        </c:if>
                    </c:if>
                </tbody>
            </table>
         
        </c:if>
        <a href="loginPage">Login</a>
        <a href="storeView">Buy Books</a>
    </body>
</html>
