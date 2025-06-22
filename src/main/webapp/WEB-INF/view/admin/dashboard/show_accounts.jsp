<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="/admin/css/reset.css" />
        <link rel="stylesheet" href="/admin/css/style_admin_dashboard.css" />
        <link rel="stylesheet" href="/admin/css/repo.css">
        <title>Admin</title>
    </head>
    <body>

        <div class="khoichung">

            <div class="khoitrai">
                <div class="khoiadmin">
                    <img src="/admin/images/setting 1.jpg" alt="icon cai dat" class="admin__img" />
                    <p class="admin__noidung">Administrator</p>
                </div>

                <div class="khoiicon">
                    <div class="item__list">

                    <!-- item1 -->
                        <div class="item__list-item">
                            <img src="/admin/images/key-square.png" alt="" class="item__img" />
                            <a href="" class="item__desc">Infomation</a>
                            <img src="/admin/images/chevron-right 2.png" alt="" class="item__svg" />
                        </div>

                    <!-- item2 -->
                        <div class="item__list-item">
                            <img src="/admin/images/3d-square 1.png" alt="" class="item__img" />
                            <a href="" class="item__desc">Match</a>
                            <img src="/admin/images/chevron-right 2.png" alt="" class="item__svg" />
                        </div>

                    <!-- item3 -->
                        <div class="item__list-item item3">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 512 512"
                                class="item__img"
                                fill="currentColor"
                            >
                                <path
                                d="M399 384.2C376.9 345.8 335.4 320 288 320l-64 0c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"
                                />
                            </svg>
                            <a href="" class="item__desc">Customers</a>
                            <img src="/admin/images/chevron-right 2.png" alt="" class="item__svg" />
                        </div>

                    <!--item4  -->
                        <div class="item__list-item">
                            <img src="/admin/images/wallet-money 2.png" alt="" class="item__img" />
                            <a href="" class="item__desc">Notification</a>
                            <img src="/admin/images/chevron-right 2.png" alt="" class="item__svg" />
                        </div>
                    </div>
                </div>

                <div class="khoian">
                    <div class="item__listmb">

                        <!-- icon-1 -->
                        <div class="item__list-item">
                            <img src="/admin/images/key-square.png" alt="" class="item__img" />
                        </div>

                        <!-- item2 -->
                        <div class="item__list-item">
                            <img src="/admin/images/3d-square 1.png" alt="" class="item__img" />
                        </div>

                        <!-- item3 -->
                        <div class="item__list-item item3">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 512 512"
                                class="item__img"
                                fill="currentColor"
                            >
                                <path
                                d="M399 384.2C376.9 345.8 335.4 320 288 320l-64 0c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"
                                />
                            </svg>
                        </div>

                        <!--item4  -->
                        <div class="item__list-item">
                            <img src="/admin/images/wallet-money 2.png" alt="" class="item__img" />
                        </div>
                    </div>

                    <script>
                    const navPC = document.querySelector(".item__list");
                    const navMB = document.querySelector("item__listmb");
                    navMB.innerHTML = navPC.innerHTML;
                    </script>
                </div>

                <div class="khoiavt">
                    <img src="/admin/images/tindao.png" alt="" class="hinhtindao" />
                    <div class="item__content">
                        <p class="content-top"><c:out value="${sessionScope.username}" /></p>
                        <p class="content-bot">Project Manager</p>
                    </div>
                    <img src="/admin/images/chevron-right 2.png" alt="" class="item__svg" />
                </div>

            </div>



            <div class="khoiphai">
                <div class="khoihello">
                    <p class="hello">Hello <c:out value="${sessionScope.username}" /> 👋🏼</p>
                </div>

                <div class="khoitieude">
                    <div class="khoitieude-item">
                        <img src="/admin/images/liver.png" alt="" class="tieude-img" />

                        <div class="khoicontent">
                            <p class="liver__content">Big Match</p>
                            <p class="liver__number">12,345</p>

                            <div class="liver__topic">
                                <img src="/admin/images/muitenlen.png" alt="" class="liver__img">
                                <p class="liver__desc">Sold</p>
                            </div>
                        </div>
                    </div>

                    <div class="khoitieude-item">
                        <img src="/admin/images/c1.png" alt="" class="tieude-img" />

                            <div class="khoicontent">
                                <p class="liver__content">Accounts</p>
                                <p class="liver__number">${tongSoTaiKhoan}</p>

                                <div  class="liver__topic">
                                    <img src="/admin/images/c1khangia.png" alt="" class="liver__img">
                                    <p class="liver__desc">This month</p>
                                </div>
                            </div>
                    </div>

                    <div class="khoitieude-item khoionline">
                        <img src="/admin/images/epl.png" alt="" class="tieude-img" />

                        <div class="khoicontent">
                            <p class="liver__content">Online</p>
                            <p class="liver__number">1</p>

                            <div class="liver__topic">
                                <img src="/admin/images/tindao.png" alt="" class="liver__img">
                                <p class="liver__desc">Account</p>
                            </div>
                        </div>
                    </div>
            </div>

            <div class="khoinhap">
                <div class="khoitren">
                    <p class="tren-desc">All Customers</p>

                    <div class="tren-topic">
                        <form action="" class="form">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M11 19C15.4183 19 19 15.4183 19 11C19 6.58172 15.4183 3 11 3C6.58172 3 3 6.58172 3 11C3 15.4183 6.58172 19 11 19Z" stroke="#7E7E7E"    stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M21 21L16.65 16.65" stroke="#7E7E7E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <input type="text" class="form-input" placeholder="Search">
                        </form>

                        <form action="" class="form__sapxep">
                            <label for="">Short by :</label>
                            <select name="thutu" id="thutu">
                                <option value="1" class="Newest">StM</option>
                                <option value="2" class="Newest">MtS</option>
                            </select>
                        </form>
                    </div>
                </div>

                <div class="khoigiua">
                        <div class="grid-row">
                            <p class="truong">UserID</p>
                            <p class="truong">Username</p>
                            <p class="truong">Email</p>
                            <p class="truong">Role</p>
                            <p class="truong">Action</p>
                        </div>

                    <%-- Ý tưởng sử dụng vòng lặp bên JSP để in ra danh sách tài khoản --%>
                    <%-- items="${users}": lấy danh sách users từ controller truyền vào model. --%>
                    <c:forEach var="userItem" items="${users}">
                        <div class="grid-row">
                            <p class="row">${userItem.id}</p>
                            <p class="row">${userItem.username}</p>
                            <p class="row">${userItem.email}</p>
                            <p class="row">${userItem.role.name}</p>

                            <form class="form__grid" action="">
                                    <button class="btn btn__update"><a href="#!" class="link">Update</a></button>
                                    <button class="btn btn__delete"><a href="#!" class="link">Delete</a></button>
                                    <button class="btn btn__read"><a href="#!" class="link">View</a></button>
                            </form>
                        </div>
                    </c:forEach>

                </div>

                <div class="khoiduoi">
                    <p class="show">Showing data</p>
                    <button class="but btn">
                        <a href="/admin/accounts/create" class="link">Create User</a>
                    </button>
                </div>

            </div>
        </div>
    </body>
</html>
