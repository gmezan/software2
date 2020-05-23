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
    $("#confirmarRecepcionModal  #comunidades").text('');
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
            $("#confirmarRecepcionModal  #idgestor").val(asignsede.id.gestor.idusuarios);
            $("#confirmarRecepcionModal  #idsede").val(asignsede.id.sede.idusuarios);
            $("#confirmarRecepcionModal  #idproductoinv").val(asignsede.id.productoinventario.codigoinventario);
            $("#confirmarRecepcionModal  #idfechaenvio").val(asignsede.id.fechaenvio);
            $("#confirmarRecepcionModal #codinv").text(asignsede.id.productoinventario.codigoinventario);
            $("#confirmarRecepcionModal  #producto").text(asignsede.id.productoinventario.productos.nombre);
            $("#confirmarRecepcionModal  #color").text(asignsede.id.productoinventario.color);
            $("#confirmarRecepcionModal  #tamanho").text(asignsede.id.productoinventario.tamanho);
            $("#confirmarRecepcionModal  #precioventa").text(asignsede.precioventa);
            $("#confirmarRecepcionModal  #stock").text(asignsede.stock);
            $("#confirmarRecepcionModal  #fechaenvio").text(asignsede.id.fechaenvio);
            $("#confirmarRecepcionModal #foto").attr("src",asignsede.id.productoinventario.foto).attr("hidden",asignsede.id.productoinventario.foto==="");
            $("#confirmarRecepcionModal  #comunidades").text(asignsede.id.productoinventario.comunidades.nombre);

        }
    }).fail(function (err) {
        console.log(err);
        $('#confirmarRecepcionModal').modal('hide');
        alert("Ocurrió un error");
    })
});


