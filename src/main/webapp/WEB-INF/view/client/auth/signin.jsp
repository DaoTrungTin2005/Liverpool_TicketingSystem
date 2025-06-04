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
        <div class="khoigiua">
          <div class="khoimail">
            <form action="" class="form">
              <p class="tieude">Email</p>
              <input type="email" class="mail" required />
            </form>
          </div>
          <div class="khoipass">
            <form action="" class="form">
              <p class="tieude">Password</p>
              <input type="password" class="mail" required />
            </form>
          </div>
          <div class="khoilogin">
            <a href="/signup" class="tieude">SignUp</a>
          </div>
        </div>
        <div class="khoinext">
          <button class="btn">
            <a href="#!" class="link">Next</a>
          </button>
        </div>
      </div>
    </div>
  </body>
</html>
