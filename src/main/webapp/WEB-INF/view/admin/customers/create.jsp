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

            <div class="nhaplieu">
                <form action="" class="form username">
                    <label for="" class="label">Username</label>
                    <input type="text" class="input" />
                </form>
                <form action="" class="form password">
                    <label for="" class="label">Password</label>
                    <input type="password" class="input" />
                </form>
                <form action="" class="form email">
                    <label for="" class="label">Email</label>
                    <input type="email" class="input" />
                </form>
                <form action="" class="form role">
                    <label for="" class="label">Role</label>
                    <div class="thechon">
                    <label class="label"
                        ><input type="radio" name="role" class="input" />ADMIN</label
                    >
                    <label class="label"
                        ><input type="radio" name="role" class="input" />USER</label
                    >
                    </div>
                </form>
            </div>

            <div class="khoiduoi">
                <button class="btn">
                    <a href="" class="link">Create Account</a>
                </button>
            </div>
        </div>

    </div>
        <script src="./thaotac.js"></script>
    </body>
</html>
