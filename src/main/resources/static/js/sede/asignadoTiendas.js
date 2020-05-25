var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".registrar-Venta", function(){
    $("#registrarModal  #id").val('');
    $.ajax({
        method:"GET", url:contextPath  +$(this).data('id')
    }).done(function(asignados){
        if (asignados!=null){
            $("#registrarModal  #ruc_dni").val(asignados.tienda.ruc);
            $("#registrarModal  #nombrecliente").val(asignados.tienda.nombre);
            $("#registrarModal  #tipodocumento").val(asignados.tipodocumento);
            $("#registrarModal  #lugarventa").val(asignados.tienda.direccion);
            $("#registrarModal  #productoinventario").val(asignados.asignadosSedes.id.productoinventario.codigoinventario);
            //$("#registrarModal  #fecha").val(asignados.fecha);
            //$("#registrarModal  #nombre").val(asignados.nombre);
            //$("#registrarModal  #cantidad").val(asignados.cantidad);
            $("#registrarModal  #precio_venta").val(asignados.precio_venta);
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

