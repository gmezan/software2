var contextPath = window.location.href;

$(document).on( "click", ".asign-A-Tienda", function () {


    $("#AsignarProductoModal  #asignadosSedes\\.id\\.gestor").val($(this).data('id1'));
    $("#AsignarProductoModal  #asignadosSedes\\.id\\.sede").val($(this).data('id2'));
    $("#AsignarProductoModal  #asignadosSedes\\.id\\.productoinventario").val($(this).data('id3'));
    $("#AsignarProductoModal  #asignadosSedes\\.id\\.estadoasignacion").val($(this).data('id4'));
    $("#AsignarProductoModal  #asignadosSedes\\.id\\.precioventa").val($(this).data('id5'));

    $("#AsignarProductoModal  #fechaasignacion").val('');
    $("#AsignarProductoModal  #stock").val('');
    $("#AsignarProductoModal  #tienda").val('');



});

$(document).ready(function() {
    if ($("#msgAsign").text()==="ERROR"){
        $("#AsignarProductoModal").modal({show:true});
    }
});

