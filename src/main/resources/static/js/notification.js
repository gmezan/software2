const BASE = window.location.href.split("/")[0]+"//"+window.location.href.split("/")[2]+"/notification";
var i=0;
$(function () {
    //Load notifications
    $.ajax({method:"GET", url:BASE+"/number"})
        .done(function (value) {
            $("#notification").find(" #number").text(value);
        })
        .fail(function (err) {
            alert("Ocurrió un error al cargar la página");
        })
}).on("click",".dropdownButton",function () {
    console.log(++i);
});