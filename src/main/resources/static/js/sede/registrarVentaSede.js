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
    $("#registrarVentaModal  #codinv").val($(this).data('id32'));
    $("#registrarVentaModal  #idproductoinvinput").val( $(this).data('id32'));
    $("#registrarVentaModal  #idprecioventa").val(  $(this).data('id52'));
    $("#registrarVentaModal  #idestadoasign").val( $(this).data('id42'));


   /* $.ajax({

    }).fail(function (err) {
        console.log(err);
        $("#registrarVentaModal").modal('hide');
        alert("Ocurrió un error");
    })*/


});


$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $("#registrarVentaModal").modal({show:true});
    }
});

