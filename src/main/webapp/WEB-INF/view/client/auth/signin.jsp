<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="client/css/reset.css" />
    <link rel="stylesheet" href="client/css/style_signin.css" />
    <link
      rel="apple-touch-icon"
      sizes="57x57"
      href="client/icon/apple-icon-57x57.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="60x60"
      href="client/icon/apple-icon-60x60.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="72x72"
      href="client/icon/apple-icon-72x72.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="76x76"
      href="client/icon/apple-icon-76x76.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="114x114"
      href="client/icon/apple-icon-114x114.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="120x120"
      href="client/icon/apple-icon-120x120.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="144x144"
      href="client/icon/apple-icon-144x144.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="152x152"
      href="client/icon/apple-icon-152x152.png"
    />
    <link
      rel="apple-touch-icon"
      sizes="180x180"
      href="client/icon/apple-icon-180x180.png"
    />
    <link
      rel="icon"
      type="image/png"
      sizes="192x192"
      href="client/icon/android-icon-192x192.png"
    />
    <link
      rel="icon"
      type="image/png"
      sizes="32x32"
      href="client/icon/favicon-32x32.png"
    />
    <link
      rel="icon"
      type="image/png"
      sizes="96x96"
      href="client/icon/favicon-96x96.png"
    />
    <link
      rel="icon"
      type="image/png"
      sizes="16x16"
      href="client/icon/favicon-16x16.png"
    />
    <link rel="manifest" href="client/icon/manifest.json" />
    <meta name="msapplication-TileColor" content="#ffffff" />
    <meta name="msapplication-TileImage" content="client/icon/ms-icon-144x144.png" />
    <meta name="theme-color" content="#ffffff" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400..900&family=PT+Serif+Caption:ital@0;1&display=swap"
      rel="stylesheet"
    />
    <title>Sign in</title>
  </head>

  <body>
    <div class="khoichung">

      <div class="khoitrai">
        <img src="client/images/salah.jpg" alt="Cau thu MU so nhat" class="img" />
      </div>

      <div class="khoiphai">

        <div class="khoisign">
          <h1 class="tieude">Sign in</h1>
        </div>

        <%-- Khi người dùng submit form đăng nhập, dữ liệu sẽ được gửi lên server bằng phương thức POST tới đường dẫn /signin. --%>
        <%-- vẫn cần dòng <form:form method="post" action="/signin" > trong view đăng nhập, dù controller chỉ có @GetMapping("/signin"). --%>
        <%-- Lý do:
        Khi người dùng nhập email và password rồi nhấn "Next", form sẽ gửi POST tới /signin.
        Spring Security sẽ tự động xử lý POST /signin để xác thực đăng nhập, không cần bạn viết controller @PostMapping("/signin").
        Chỉ cần controller @GetMapping("/signin") để hiển thị trang đăng nhập. --%>

        <form method="post" action="/signin" >



        <div class="khoigiua">

          <div class="khoimail">
              <p class="tieude">Email</p>

              <%-- Bạn cần name="username" và name="password" trong form login vì Spring Security mặc định chỉ nhận đúng hai tham số này khi xử lý đăng nhập --%>
              <%-- Nếu bạn đổi thành name="email" hoặc tên khác, Spring Security sẽ không tự động lấy giá trị đó để xác thực, trừ khi bạn cấu hình lại. --%>
              <%-- DaoAuthenticationProvider là nơi xử lý logic xác thực, so sánh mật khẩu, và chỉ nhận đúng hai tham số username và password từ form mặc định của Spring Security. --%>

              <%-- Còn phần path là các thuộc tính ở domain  --%>

              <%-- Spring dùng path để:
              Tìm đúng thuộc tính trong domain
              Gán giá trị từ form vào đúng vị trí --%>
              <input class="mail" path="email" type="email" name="username"/>
          </div>

          <div class="khoipass">
              <p class="tieude">Password</p>
              <input class="mail" path="password" type="password" name="password"/>
          </div>

          <%-- Bắt lỗi nếu đăng nhập sai (Email không tồn tại hoặc pass sai) --%>

          <%-- Khi đăng nhập sai, Spring Security sẽ tự động chuyển hướng về /signin?error.
          Lúc này, trên URL sẽ có tham số error (ví dụ: http://localhost:8080/signin?error).
          request.getParameter("error") sẽ lấy giá trị của tham số này.
          Nếu có lỗi, error sẽ khác null, nên đoạn <div>...</div> sẽ được in ra view để báo lỗi cho người dùng. --%>

          <% String error = request.getParameter("error"); %>
          <% if (error != null) { %>
              <div style="color: red; margin-left: 70px; ">Login failed. Please check your email or password</div>
          <% } %>

          <%-- Hiển thị dòng thông báo khi Logout thành công (nó tự chuyển về /signin?logout )--%>

          <% String logout = request.getParameter("logout"); %>
          <% if (logout != null) { %>
              <div style="color: rgb(0, 128, 66); margin-left: 70px; ">Logout Success</div>
          <% } %>

          <div class="khoilogin">
            <a href="/signup" class="tieude signup__hover">SignUp</a>
          </div>

          <%-- CSRF token. Spring Security yêu cầu mỗi form POST phải gửi kèm token này để bảo vệ khỏi tấn công CSRF. --%>
          <%-- Nếu bạn xóa dòng này, khi submit form đăng nhập (hoặc bất kỳ form POST nào), bạn sẽ gặp lỗi 403 Forbidden --%>

          <%-- CSRF là: Hacker lợi dụng phiên đăng nhập của bạn để thực hiện hành động trái phép. --%>
          <%-- Ví dụ : --%>
          <%-- Bạn đã đăng nhập vào tài khoản ngân hàng --%>
          <%-- Kẻ tấn công gửi cho bạn một link độc hại hoặc nhúng mã vào một trang web khác. --%>
          <%-- Khi bạn bấm vào link đó (hoặc chỉ cần truy cập trang có mã độc), trình duyệt của bạn sẽ gửi một request (ví dụ: chuyển tiền) tới website ngân hàng với quyền của bạn, vì bạn đang đăng nhập. --%>
          <%-- Hệ thống ngân hàng sẽ nghĩ đó là hành động hợp lệ của bạn. --%>

          <%-- Cách phòng chống: --%>
          <%-- CSRF Token: Mỗi form POST sẽ có một mã token bí mật (ẩn trong form). Khi gửi form, server kiểm tra token này. Nếu không đúng hoặc không có, request sẽ bị từ chối. --%>
          <div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          </div>


        <div class="khoinext">
          <button class="btn link" type="submit">
                Next
          </button>
        </div>

      </div>

      </form>

    </div>
    </div>
  </body>
</html>
