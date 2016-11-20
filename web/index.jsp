<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>DSA</title>
  <meta charset="utf-8">
  <%-- CODIGO PARA UTILIZAR LOS BOOTSTRAPS --%>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script>
    $(document).ready(function(){
      $("#btn2").click(function (){
        var name= $("#name").val();
        var password= $("#password").val();
        $.post("/Servlet",{name:name,password:password},function(responseText) {
          if (responseText=="Usuario creado: "+name){
          window.location="vista.jsp";}
          else{$("#res").text(responseText);}
        });
      });
    });
  </script>
      <script>
      $(document).ready(function(){
      $("#btn1").click(function (){
        var name= $("#name").val();
        var password= $("#password").val();
        $.get("/Servlet",{name:name,password:password},function(responseText) {
          if(responseText=="no") {
            $("#res").text(responseText);
          }
          else{ window.location="vista.jsp";}
        });
      });
    });
  </script>

</head>

<body>

<div class="container">

  <div class="jumbotron">
    <h1>EETACMON</h1>

    <h2>Bienvenida</h2>

    <div class="container">
      <div class="jumbotron">
        <h1>Inicio Session</h1>

        <div class="form-group">
          <label>Name</label>
          <input class="form-control" id="name" type="text">
        </div>

        <div class="form-group">
          <label>Password</label>
          <input class="form-control"id="password" type="password">
        </div>

        <button id="btn1" class="btn btn-primary center-block">Iniciar session</button>
        <button id="btn2" class="btn btn-primary center-block">Registrarse</button>
        <p id="res"></p>

      </div>

    </div>

  </div>

</div>

</body>

</html>