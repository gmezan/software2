var contextPath  = window.location.href;

$(document).on("click",".regis-Venta", function(){

    $("#registrarVentaModal  #idgestor").val('');
    $("#registrarVentaModal  #idsede").val('');
    $("#registrarVentaModal #nombrecliente").val('');
    $("#registrarVentaModal  #tipodocumento").val('');
    $("#registrarVentaModal  #numerodocumento").val('');
    $("#registrarVentaModal  #fecha").val('');
    $("#registrarVentaModal  #lugarventa").val('');
    $("#registrarVentaModal  #rucdni").val('');
    $("#registrarVentaModal  #cantidad").val('');
    $("#registrarVentaModal  #idproductoinvlabel").val('');
    $("#registrarVentaModal  #idproductoinvlabel").text( $(this).data('id32'));
    $("#registrarVentaModal  #idproductoinvinput").val( $(this).data('id32'));

});


$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#registrarVentaModal').modal('show');
    }
});


