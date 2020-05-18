var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Producto", function(){
    $("#formModal #codigonom").val('');
    $("#formModal #nombre").val('');
    $("#formModal #codigodesc").val('');
    $("#formModal #descripcion").val('');
    $("#formModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath + $(this).data('id')
    }).done(function(producto){
        if (producto!=null){
            $("#formModal #codigonom").val(producto.codigonom).prop("readonly", true);
            $("#formModal #nombre").val(producto.nombre);
            $("#formModal #codigodesc").val(producto.codigodesc);
            $("#formModal #descripcion").val(producto.descripcion);
            $("#formModal #linea").val(producto.linea)
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Producto", function(){
    $("#formModal #codigonom").val('').prop("readonly", false);$("#formModal  #codigodesc").val('');
    $("#formModal   #nombre").val('');$("#formModal  #descripcion").val('');
    $("#formModal  #type").val('1');
});
$(document).on("click",".delete-Producto", function(){
    $("#deleteModal #codigonom").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgProducto").text()==="ERROR"){
        $('#formModal ').modal('show');
    }
});

