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

    let url = contextPath + "/get?idgestor=" + $(this).data('id12') + "&idsede=" + $(this).data('id22') + "&idproductoinv=" + $(this).data('id32') + "&idestadoasign42=" + $(this).data('id42') + "&idprecioventa52=" + $(this).data('id52') ;
    console.log(url);
    $.ajax({
        method:"GET", url:url
    }).done(function(ventas){
        if (ventas!=null){
            $("#registrarVentaModal  #rucdni").val(ventas.tienda.ruc);
            $("#registrarVentaModal  #nombrecliente").val(ventas.tienda.nombre);
            $("#registrarModal  #lugarventa").val(ventas.tienda.direccion);
            $("#registrarModal  #codigoinventario").val(ventas.asignadosSedes.id.productoinventario.codigoinventario);
            $("#registrarModal  #precioventa").val(ventas.asignadosSedes.id.precioventa);
            $("#registrarModal  #vendedor").val(ventas.asignadosSedes.id.sede.idusuarios);
        }
    }).fail(function (err) {
        console.log(err);
        $('#registrarModal').modal('hide');
        alert("Ocurrió un error");
    })
});


$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#registrarVentaModal').modal('show');
    }
});


var contextPath  = window.location.href+"/get?id1=";

$(document).on("click",".registar-Venta", function(){
    $("#registrarModal  #id1").val($(this).data('id1'));

    $.ajax({
        method:"GET", url:contextPath  +$(this).data('id1')
    }).done(function(ventas){
        if (ventas!=null){
            $("#registrarModal  #rucdni").val(ventas.tienda.ruc);
            $("#registrarModal  #nombrecliente").val(ventas.tienda.nombre);
            $("#registrarModal  #lugarventa").val(ventas.tienda.direccion);
            $("#registrarModal  #codigoinventario").val(ventas.asignadosSedes.id.productoinventario.codigoinventario);
            $("#registrarModal  #precioventa").val(ventas.asignadosSedes.id.precioventa);
            $("#registrarModal  #vendedor").val(ventas.asignadosSedes.id.sede.idusuarios);
        }
    }).fail(function (err) {
        console.log(err);
        $('#registrarModal').modal('hide');
        alert("Ocurrió un error");
    })
});

$(document).on("click",".retornar-Producto", function(){
    $("#devolucionModal #id2").val($(this).data('id2'));
    console.log($(this).data('id2'))

});
