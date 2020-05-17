var contextPath = "http://localhost:8080";

$(document).on("click",".registrar-Venta", function(){
    $("#registrarModal  #idtienda").val('');
    $("#registrarModal  #nombre").val('');
    $("#registrarModal  #direccion").val('');
    $.ajax({
        method:"GET", url:contextPath + "/sede/AsignadoTienda/get?id=" +$(this).data('id')
    }).done(function(asignados){
        if (asignados!=null){
            $("#registrarModal  #idtienda").val(asignados.);
        }
    })
});

$(document).on("click",".registar-Venta", function(){
    $("#registrarModal #cantVenta").val($(this).data('cantVenta'));
});
$(document).on("click",".retornar-Producto", function(){
    $("#devolucionModal #cantRet").val($(this).data('cantRet'));
});

