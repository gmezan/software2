var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Producto", function(){
    $("#formModal #codigonom").val('');
    $("#formModal #nombre").val('');
    $("#formModal #codigodesc").val('');
    $("#formModal #descripcion").val('');
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
    $("#formModal #codigo").val('');$("#editModal  #nombre").val('');
    $("#formModal   #apellidopaterno").val('');$("#editModal  #apellidomaterno").val('');
});
$(document).on("click",".delete-Producto", function(){
    $("#deleteModal #codigo").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgArtesanos").text()==="ERROR"){
        $('#formModal ').modal('show');
    }
});

