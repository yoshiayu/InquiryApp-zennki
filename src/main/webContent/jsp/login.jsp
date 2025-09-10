<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ログイン</title>
  <link rel="stylesheet" href="<c:url value='/style.css'/>">
  <style>
    .login-box{max-width:420px;margin:60px auto;padding:24px;border:1px solid #ddd;border-radius:10px;background:#fff}
    .error{color:#b00020;margin-top:8px}
    label{display:block;margin-top:12px}
    input[type="email"], input[type="password"]{width:100%;padding:8px}
    button{margin-top:16px;padding:8px 16px}
  </style>
</head>
<body>
<div class="login-box">
  <h2>ログイン</h2>
  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>
  <form action="<c:url value='/login'/>" method="post">
    <label>メールアドレス
      <input type="email" name="email" required value="user@example.com">
    </label>
    <label>パスワード
      <input type="password" name="password" required value="pass1234">
    </label>
    <button type="submit">ログイン</button>
  </form>
</div>
</body>
</html>
