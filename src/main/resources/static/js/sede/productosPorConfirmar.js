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


    let url = contextPath + "/post";
        //+ $(this).data('id1') + "&idsede=" + $(this).data('id2')
        //+ "&idproductoinv=" + $(this).data('id3') + "&idfechaenvio=" + $(this).data('id4');

    $.ajax({
        async: false,
        dataType : "json",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method:"POST", url:url,
        data: JSON.stringify({
            sede: parseInt($(this).data('id2')),
            gestor: parseInt($(this).data('id1')),
            productoinventario: $(this).data('id3'),
            fechaenvio: $(this).data('id4')
        })
    }).done(function(asignsede){
        if (asignsede!=null){
            console.log(asignsede);
            $("#confirmarRecepcionModal  #idgestor").val(asignsede.idgestor);
            $("#confirmarRecepcionModal  #idsede").val(asignsede.idsede);
            $("#confirmarRecepcionModal  #idproductoinv").val(asignsede.idproductoinv);
            $("#confirmarRecepcionModal  #idfechaenvio").val(asignsede.idfechaenvio);
            $("#confirmarRecepcionModal #codinv").text(asignsede.idproductoinv);
            $("#confirmarRecepcionModal  #producto").text(asignsede.producto);
            $("#confirmarRecepcionModal  #color").text(asignsede.color);
            $("#confirmarRecepcionModal  #tamanho").text(asignsede.tamanho);
            $("#confirmarRecepcionModal  #precioventa").text(asignsede.precioventa);
            $("#confirmarRecepcionModal  #stock").text(asignsede.stock);
            $("#confirmarRecepcionModal  #fechaenvio").text(asignsede.idfechaenvio);
            $("#confirmarRecepcionModal #foto").attr("src",asignsede.foto).attr("hidden",asignsede.foto==="");
            $("#confirmarRecepcionModal  #comunidades").text(asignsede.comunidades);

        }
    }).fail(function (err) {
        console.log(err);
        $('#confirmarRecepcionModal').modal('hide');
        alert("Ocurrió un error");
    })
});


