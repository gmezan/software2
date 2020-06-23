var contextPath  = window.location.href;

$(document).on("click",".borrar-VentasCliente", function(){
    $("#deleteModal #id1").val($(this).data('id1'));
    $("#deleteModal #id2").val($(this).data('id2'));
    $("#deleteModal #id3").val($(this).data('id3'));
    $("#deleteModal #id4").val($(this).data('id4'));
    $("#deleteModal #id5").val($(this).data('id5'));
    $("#deleteModal #id6").val($(this).data('id6'));
});