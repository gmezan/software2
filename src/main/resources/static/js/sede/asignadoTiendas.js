var contextPath = "http://localhost:8080";

$(document).on("click",".registrar-Venta", function(){
    $("#registrarModal  #ruc_dni").val('');
    $("#registrarModal  #nombrecliente").val('');
    $("#registrarModal  #tipodocumento").val('');
    $("#registrarModal  #lugarventa").val('');
    $("#registrarModal  #productoinventario").val('');
    $("#registrarModal  #fecha").val('');
    $("#registrarModal  #nombre").val('');
    $("#registrarModal  #cantidad").val('');
    $("#registrarModal  #precio_venta").val('');
    $.ajax({
        method:"GET", url:contextPath + "/sede/AsignadoTienda/get?id=" +$(this).data('id')
    }).done(function(venta){
        if (venta!=null){
            $("#registrarModal  #ruc_dni").val(venta.ruc_dni);
            $("#registrarModal  #nombrecliente").val(venta.nombrecliente);
            $("#registrarModal  #tipodocumento").val(venta.tipodocumento);
            $("#registrarModal  #lugarventa").val(venta.lugarventa);
            $("#registrarModal  #productoinventario").val(venta.productoinventario);
            $("#registrarModal  #fecha").val(venta.fecha);
            $("#registrarModal  #nombre").val(venta.nombre);
            $("#registrarModal  #cantidad").val(venta.cantidad);
            $("#registrarModal  #precio_venta").val(venta.precio_venta);
        }
    }).fail(function (err) {
        console.log(err);
        $('#registrarModal').modal('hide');
        alert("Ocurrió un error");
    })
});

$(document).on("click",".retornar-Producto", function(){
    $("#devolucionModal #cantRet").val($(this).data('cantRet'));
});

