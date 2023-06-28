<%-- 
    Document   : cart
    Created on : Mar 14, 2023, 3:51:58 PM
    Author     : ADMIN
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
    </head>
    <body>
        <h1>Book Store</h1>
        <c:if test="${empty sessionScope.CART.items}">
            <h2>
                No Cart is existed !!!
                <br><a href="storeView">Click here to buy book</a>
            </h2>
        </c:if>
        <c:if test="${not empty sessionScope}">
            <c:if test="${not empty sessionScope.CART}">
                <c:set var="items" value="${sessionScope.CART.items}"></c:set>
                <c:if test ="${not empty items}">
                    <form action="cartDeleteItemAction" method="POST">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${items}" varStatus="couter">
                                    <tr>
                                        <td>
                                            <div style="text-align: right">
                                            ${couter.count}
                                            </div>

                                        </td>
                                        <td>
                                            ${item.key.name}
                                        </td>
                                        <td>
                                            <div style="text-align: right">
                                            ${item.value}
                                            </div>
                                        </td>
                                        <td>
                                            <input type="checkbox" name="chkItem" 
                                                   value="${item.key.sku}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <th colspan="3">
                                        <a href="storeView">Add more Books to your Cart</a>
                                    </th>
                                    <th>
                                        <input type="submit" value="RemoveProduct" name="btAction" />
                                    </th>
                                </tr>
                            </tbody>
                        </table> 
                    </form>
                    <form action="checkOutAction" method="POST">
                        <c:if test="${not empty requestScope.OrderError}">
                            <c:set var="Errors" value="${requestScope.OrderError}"></c:set>
                        </c:if>
                        name: <input type="text" name="txtNameOrder" value="" /><br>
                        <c:if test="${not empty Errors.nameOrderErrorl}">
                            <font color = "red">
                            ${Errors.nameOrderErrorl}
                            </font><br>
                        </c:if>
                        address: <textarea name="txtAddress" rows="4" cols="20" value="" 
                                           style="overflow-y: scroll; resize: none">
                        </textarea><br>
                        <c:if test="${not empty Errors.addressError}">
                            <font color = "red">
                            ${Errors.addressError}
                            </font><br>
                        </c:if>
                        <c:if test="${not empty Errors.quantityNotEnoughError}">
                            <font color = "red">
                            ${Errors.quantityNotEnoughError}
                            </font><br>
                        </c:if><br>

                        <input type="submit" value="order" name="btAction" />                     
                    </form>
                </c:if>
            </c:if>
        </c:if>
        
    </body>
</html>
