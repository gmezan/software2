var contextPath  = window.location.href;

$(document).on("click",".registar-Venta", function(){
    $("#registrarModal  input").val('');
    $("#registrarModal  #id1").val($(this).data(''));
    $.ajax({
        method:"GET", url:contextPath +"/get?id1=" +$(this).data('id1')
    }).done(function(ventas){
        if (ventas!=null){
            $("#registrarModal  #cant").text('Cantidad:  (Cantidad Disponible: ' + ventas.stock + ')');

            $("#registrarModal  #id1").val(ventas.idtiendas);
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

$(document).ready(function() {
    if ($("#msgRegistrar").text()==="ERROR"){
        $('#registrarModal').modal({show: true, backdrop: 'static', keyboard: false });
    }else if($("#msgDevolucion").text()==="ERROR"){
        $('#devolucionModal').modal({show: true, backdrop: 'static', keyboard: false });
    }

});

