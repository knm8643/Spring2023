<!doctype html>
<html lang="ko">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form name="form" method="post" action="/logins" id="form-title1">
    <table>
        <tr>
            <td colspan="3">
                <h2>로그인</h2>
            </td>
        </tr>
        <tr>
            <td>아이디</td>
            <td><input type="text" placeholder="아이디 입력" name="id"></td>
        </tr>
        <tr>
            <td>비밀번호</td>
            <td><input type="text" placeholder="비밀번호 입력" name="pw"></td>
        </tr>
        <tr>
            <td colspan="2">
                <span onclick="returnURL()" class="custom-button">되돌아가기</span>
                <button type="submit" class="custom-button">로그인</button>
                <button type="reset" class="custom-button">초기화</button>
            </td>
        </tr>
    </table>
</form>
<form name="form" method="get" action="/SiginPage" id="form-title2">
    <table>
        <tr>
            <td colspan="3">
                <h2>회원가입</h2>
            </td>
        </tr>
        <tr>
            <td>아이디</td>
            <td><input type="text" placeholder="아이디 입력" name="id"></td>
        </tr>
        <tr>
            <td>비밀번호</td>
            <td><input type="text" placeholder="비밀번호 입력" name="pw"></td>
        </tr>
        <tr>
            <td colspan="2">
                <span onclick="returnURL()" class="custom-button">되돌아가기</span>
                <button type="submit" class="custom-button">가입</button>
                <button type="reset" class="custom-button">초기화</button>
            </td>
        </tr>
    </table>
</form>
<script>
    function returnURL() {
        window.history.back();
    }

    // 페이지 로드 시 실행할 함수
    function onPageLoad() {
        // 여기에 실행할 코드 작성
        console.log("페이지가 로드되었습니다.");

        // 현재 URL 가져오기
        var url = new URL(window.location.href);

        // URL 파라미터 가져오기
        var loginParam = url.searchParams.get("login");

        // URL 파라미터가 "true"인 경우
        if (loginParam === "true") {
            var formTitle = document.getElementById("form-title2");
            formTitle.style.display = 'none';
        } else {
            var formTitle = document.getElementById("form-title1");
            formTitle.style.display = 'none';
        }

    }

    // 페이지가 완전히 로드되었을 때 실행되는 이벤트 핸들러 등록
    document.addEventListener("DOMContentLoaded", onPageLoad);
</script>
</body>
<style>
    td{
        border: 1px solid black;
    }
    table{
        text-align: center;
        margin: 0 auto;
    }
    .custom-button {
        font-size: small;
        display: inline-block;
        padding: 5px 10px;
        background-color: transparent;
        border: none;
        color: inherit;
        cursor: pointer;
    }
</style>
</html>