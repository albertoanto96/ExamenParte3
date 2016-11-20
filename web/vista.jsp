<%--
<%--
  Created by IntelliJ IDEA.
  User: Turpitude
  Date: 16/11/2016
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pokemon</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="script3.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#btn1").click(function (){
                var name= $("#name").val();
                var nombre= $("#nombre").val();
                $.post("/ServletPokemon",{nombre:nombre,name:name},function(responseText) {
                    $("#res").text(responseText);
                });
            });
        });

    </script>
    <script>
        $(document).ready(function(){
            $("#btn2").click(function (){
                var nombre= $("#nombre").val();
                $.get("/ServletPokemon",{nombre:nombre},function(responseJson){
                    var $ul = $("<ul>").appendTo($("#res"));
                    $.each(responseJson, function(index, item) {
                        $("<li>").text(item).appendTo($ul)});
                });
            });
        });
    </script>
</head>
<body>

<div class="container">
    <div class="jumbotron">
        <h1>Pokemon</h1>
        <p id="res"></p>
        <footer id="foot01"></footer>
    </div>
    <div class="form-group">
        <label>Recuerdame tu nombre y actualiza</label>
        <input class="form-control" id="nombre" type="text">
    </div>

    <div class="form-group">
        <label>Nombre pokemon</label>
        <input class="form-control"id="name" type="text">
    </div>
    <button id="btn2" class="btn btn-primary center-block">Actualizar</button>
    <button id="btn1" class="btn btn-primary center-block">Insertar pokemon</button>

</div>
</body>
</html>
