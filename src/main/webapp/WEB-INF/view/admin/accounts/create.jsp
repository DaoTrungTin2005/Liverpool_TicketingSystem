<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="/admin/css/reset.css" />
        <link rel="stylesheet" href="/admin/css/style_create_users.css" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
        href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400..900&family=PT+Serif+Caption:ital@0;1&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet"
        />
        <title>Create Account</title>

        <style>
        .error {
            color: red;
            font-size: 0.9rem;
            margin-top: 4px;
            display: block;
            padding: 0;
        }

        .error::before {
            content: "⚠ ";
            font-weight: bold;
        }
        </style>

    </head>

    <body>
        <div class="khoichung">

        <div class="khoitrai">

            <div class="trai__desc">
                <a href="/admin" class="link"
                    ><img src="/admin/images/arrow-left-solid.svg" alt=""
                /></a>
                <p class="desc">You will never walk alone</p>
            </div>

            <div class="trai__img">
                <img
                    src="/admin/images/bills.jpg"
                    alt=""
                    class="trai__img--item trai__img--1"
                />
                <img
                    src="/admin/images/klopp.jpg"
                    alt=""
                    class="trai__img--item trai__img--2"
                />
                <img src="/admin/images/g8.jpg" alt="" class="trai__img--item trai__img--3" />
            </div>

            <div class="trai__point">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 512 512"
                    fill="currentColor"
                    class="point-1"
                >
                    <path
                    d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
                    />
                </svg>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 512 512"
                    fill="currentColor"
                    class="point-2"
                >
                    <path
                    d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
                    />
                </svg>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 512 512"
                    fill="currentColor"
                    class="point-3"
                >
                    <path
                    d="M464 256A208 208 0 1 0 48 256a208 208 0 1 0 416 0zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256z"
                    />
                </svg>
            </div>
        </div>



        <div class="khoiphai">

            <div class="khoitren">
                <p class="create">Create Account</p>
            </div>


        <form:form method="post" action="/admin/accounts/create" modelAttribute="newUser"> 
            <div class="nhaplieu">

                <div class="form username">
                    <label for="" class="label">Username</label>
                    <form:input type="text" class="input" path="username" autocomplete="off"/>
                    <form:errors class="error" path="username"  />
                </div>

                <div class="form email">
                    <label for="" class="label">Email</label>
                    <form:input type="email" class="input" path="email" autocomplete="off"/>

                    <!-- bắt điều kiện -->
                    <form:errors class="error" path="email"  />
                </div>

                <div class="form password">
                    <label for="" class="label">Password</label>
                    <form:input type="password" class="input" path="password" />
                    <form:errors class="error" path="password"  />
                </div>

                <div class="form role">
                    <label for="" class="label">Role</label>
                    <div class="thechon ">

                    <!-- ========================================================== -->

                    <!--  Hiện tại do muốn validate email tồn tại nên tạo thêm cái CreateAccountDTO. Do
                    đó bị vướng cái chỗ role (ban đầu tạo 2 bảng Role và User) nên không làm code này nữa)  -->
                    <!-- Sử dụng roleName ở lớp DTO -->

                    <%-- Khi bạn viết path="role.name", bạn đang bảo Spring Form làm như sau:
                    Tìm đối tượng newUser (là một User).
                    Truy cập thuộc tính role của newUser (tức là gọi newUser.getRole()).
                    Sau đó, truy cập thuộc tính name của đối tượng Role đó (tức là gọi newUser.getRole().getName() khi hiển thị và newUser.getRole().setName() khi submit).  --%>

                    <%--  Khi bạn viết:
                    <form:radiobutton path="role.name" value="ADMIN" />

                    Nó có nghĩa là:
                    user.getRole().setName("ADMIN"); (tự động tạo Role nếu cần)
                    Vì bạn đang chọn name của một object role → nên phải ghi role.name --%>

                    <!-- ================================================================= -->
                    <label class="label"
                        ><form:radiobutton path="roleName"  class="input" value="ADMIN" />ADMIN</label
                    >
                    <label class="label"
                        ><form:radiobutton path="roleName"  class="input" value="USER" />USER</label
                    >
                    <form:errors class="error" path="roleName"  />
                    </div>
                </div>

            </div>

            <div class="khoiduoi">
                <button class="btn" type="submit">
                    Create Account
                </button>
            </div>
        </div>
        </form:form>


    </div>
        <script src="./thaotac.js"></script>
    </body>
</html>
