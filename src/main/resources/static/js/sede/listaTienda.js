var contextPath = "http://localhost:8080";

$(document).on("click",".edit-Tienda", function(){
    $("#EditarTiendaModal  #idtienda").val('');
    $("#EditarTiendaModal  #nombre").val('');
    $("#EditarTiendaModal  #direccion").val('');
    const id = $(this).data('id');
    const url = contextPath + "/sede/tienda/get?id=" +id;

    $.ajax({
        method:"GET",
        url:url
    }).done(function(tienda){
        if (tienda!=null){
            $("#EditarTiendaModal  #idtienda").val(tienda.idtienda);
            $("#EditarTiendaModal  #nombre").val(tienda.nombre);
            $("#EditarTiendaModal  #direccion").val(tienda.direccion);
        }
    }).fail(function (err) {
        console.log(err);
        $('#EditarTiendaModal').modal('hide');
        alert("Ocurrió un error");
    })

});
$(document).on("click",".new-Tienda", function(){
    $("#EditarTiendaModal  #idtienda").val('0');
    $("#EditarTiendaModal  #nombre").val('');
    $("#EditarTiendaModal  #direccion").val('');
});
$(document).on("click",".delete-Tienda", function(){
    $("#deleteTiendaModal #idtienda").val($(this).data('id'));
});

$(document).ready(function() {
    console.log("Hi");
    let a = $("#msgListaTiendas").text();
    if (a==="ERROR"){
        $('#EditarTiendaModal').modal('show');
    }

});

