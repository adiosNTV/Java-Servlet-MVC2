<%-- 
    Document   : store
    Created on : Mar 14, 2023, 10:33:28 PM
    Author     : ADMIN
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
    </head>
    <body>
        <h1>Book Store</h1>

        <c:set var="result" value="${requestScope.BOOK_STORE}" />
        <c:if test="${not empty result}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>description</th>
                        <th>price</th>
                        <th>Quantity</th>
                        <th>Available</
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${result}" varStatus="couter">
                    <form action="cartAddItem" method="POST">
                        <tr>
                            <td>
                                <div style="text-align: right">
                                    ${couter.count + 1}
                                </div>
                            </td>
                            <td>
                                ${dto.name}
                                <input type="hidden" name="Sku" value="${dto.sku}" />
                            </td>
                            <td>
                                ${dto.description}
                            </td>
                            <td>
                                <div style="text-align: right">
                                ${dto.price}
                                </div>
                            </td>
                            <td>
                                <input type="number" name="txtQuantity" value="0" 
                                       min="0" max="${dto.quantity}" style="text-align: right; width: 70px"/>                
                            </td>
                            <td>
                                <div style="text-align: right">
                                ${dto.quantity}    
                                </div>
                            </td>
                            <td>
                                <input type="submit" value="Add Book To Your Cart" name="btAction" />
                            </td>
                        </tr>
                    </form>

                </c:forEach>
            </tbody>
        </table>
        <c:set var="addItemQuantityErrors" value="${requestScope.CART_QUANTITY_ERROR}" />
        <c:if test="${not empty addItemQuantityErrors.quantityItemsError}" >
            <font color="red">
            ${addItemQuantityErrors.quantityItemsError}
            </font>
        </c:if>
    </c:if>
    <form action="cartPage" method="POST">
        <input type="submit" value="View your cart" name="btAction" />
    </form>
</body>
</html>
