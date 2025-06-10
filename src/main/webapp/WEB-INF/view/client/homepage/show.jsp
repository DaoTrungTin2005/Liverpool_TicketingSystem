<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang HOME</title>
</head>
<body>
        <h1>Trang chủ FAKE</h1>

        <%-- Khi bạn bấm nút "Đăng xuất", form sẽ gửi POST tới /logout.
            Spring Security sẽ tự động:
            Xoá session
            Xoá cookie xác thực
            Chuyển hướng về /login?logout (hoặc /signin?logout nếu bạn cấu hình loginPage là /signin). --%>

        <%-- Nếu bạn không cấu hình .logout() (Nếu muốn Customize), Spring Security vẫn tự động hỗ trợ logout --%>

        <form action="/logout" method="post">

            <%-- Phải có dòng này trong mọi form POST để Spring Security xác thực request là hợp lệ và không bị tấn công giả mạo. --%>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button>Đăng xuất</button>
        </form>
</body>
</html>