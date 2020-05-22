var contextPath  = window.location.href;

$(document).on("click",".conf-Produc", function(){

    $("#confirmarRecepcionModal  #idgestor").val('');
    $("#confirmarRecepcionModal  #idsede").val('');
    $("#confirmarRecepcionModal  #idproductoinv").val('');
    $("#confirmarRecepcionModal  #idfechaenvio").val('');

    $("#confirmarRecepcionModal #codinv").text('');
    $("#confirmarRecepcionModal  #producto").text('');
    $("#confirmarRecepcionModal  #color").text('');
    $("#confirmarRecepcionModal  #tamanho").text('');
    $("#confirmarRecepcionModal  #comunidad").text('');
    $("#confirmarRecepcionModal  #precioventa").text('');
    $("#confirmarRecepcionModal  #stock").text('');
    $("#confirmarRecepcionModal  #fechaenvio").text('');
    $("#confirmarRecepcionModal  #foto").text('');


    let url = contextPath + "/get?idgestor=" + $(this).data('id1') + "&idsede=" + $(this).data('id2')
        + "&idproductoinv=" + $(this).data('id3') + "&idfechaenvio=" + $(this).data('id4');

    $.ajax({
        method:"GET", url:url
    }).done(function(asignsede){
        if (asignsede!=null){
            $("#confirmarRecepcionModal  #idtienda").val(asignsede.idgestor);
            $("#confirmarRecepcionModal  #nombre").val(asignsede.idsede);
            $("#confirmarRecepcionModal  #direccion").val(asignsede.idproductoinv);
            $("#confirmarRecepcionModal  #direccion").val(asignsede.idfechaenvio);
            $("#confirmarRecepcionModal #codinv").text(asignsede.id.productoinventario.codigoinventario);
            $("#confirmarRecepcionModal  #producto").text(asignsede.id.productoinventario.productos.nombre);
            $("#confirmarRecepcionModal  #color").text(asignsede.id.productoinventario.color);
            $("#confirmarRecepcionModal  #tamanho").text(asignsede.id.productoinventario.tamanho);
            $("#confirmarRecepcionModal  #comunidad").text(asignsede.id.productoinventario.comunidades.nombre);
            $("#confirmarRecepcionModal  #precioventa").text(asignsede.precioventa);
            $("#confirmarRecepcionModal  #stock").text(asignsede.stock);
            $("#confirmarRecepcionModal  #fechaenvio").text(asignsede.id.fechaenvio);
            $("#confirmarRecepcionModal  #foto").text(asignsede.id.productoinventario.foto);
        }
    }).fail(function (err) {
        console.log(err);
        $('#EditarTiendaModal').modal('hide');
        alert("Ocurrió un error");
    })
});


