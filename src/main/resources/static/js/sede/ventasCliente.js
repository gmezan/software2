var contextPath = "http://localhost:8080";

$(document).on("click",".borrar-VentasCliente", function(){
    $("#deleteModal #id1").val($(this).data('id1'));
    $("#deleteModal #id2").val($(this).data('id2'));
});