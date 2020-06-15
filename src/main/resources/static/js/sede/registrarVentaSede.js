var contextPath  = window.location.href;

$(document).on("click",".regis-Venta", function(){
//    $("#registrarVentaModal  input").val( '');

    $("#registrarVentaModal  #idgestor").val(  $(this).data('id12'));
    $("#registrarVentaModal  #idsede").val( $(this).data('id22'));
    $("#registrarVentaModal  #nombrecliente").val('');
    $("#registrarVentaModal  #tipodocumento").val('');
    $("#registrarVentaModal  #numerodocumento").val('');
    $("#registrarVentaModal  #fecha").val('');
    $("#registrarVentaModal  #lugarventa").val('');
    $("#registrarVentaModal  #rucdni").val('');
    $("#registrarVentaModal  #cantidad").val('');
    $("#registrarVentaModal  #idproductoinvinput").val( $(this).data('id32'));
    //$("#registrarVentaModal  #codinv").val( $(this).data('id32'));
    $("#registrarVentaModal  #idprecioventa").val(  $(this).data('id52'));
    $("#registrarVentaModal  #idestadoasign").val( $(this).data('id42'));



});


$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $("#registrarVentaModal").modal({show:true});
    }
});



