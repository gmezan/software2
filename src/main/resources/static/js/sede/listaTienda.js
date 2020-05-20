var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Tienda", function(){
    $("#EditarTiendaModal  #idtienda").val('');
    $("#EditarTiendaModal  #nombre").val('');
    $("#EditarTiendaModal  #direccion").val('');
    $("#EditarTiendaModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath +$(this).data('id')
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
    $("#  #type").val('1');
});
$(document).on("click",".delete-Tienda", function(){
    $("#deleteTiendaModal #idtienda").val($(this).data('id'));
});
$(document).ready(function() {
    console.log("Hi");
    if ($("#msgListaTiendas").text()==="ERROR"){
        $('#EditarTiendaModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});

