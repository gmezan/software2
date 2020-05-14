var contextPath = "http://localhost:8080";

$(document).on("click",".registar-Venta", function(){
    $("#registrarModal #cantVenta").val($(this).data('cantVenta'));
});
$(document).on("click",".retornar-Producto", function(){
    $("#devolucionModal #cantRet").val($(this).data('cantRet'));
});

