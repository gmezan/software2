var contextPath  = window.location.href;

$(document).on("click",".conf-Produc", function(){

    $("#confirmarRecepcionModal  #idgestor").val('');
    $("#confirmarRecepcionModal  #idsede").val('');
    $("#confirmarRecepcionModal  #idproductoinv").val('');
    $("#confirmarRecepcionModal  #idestadoasign").val('');
    $("#confirmarRecepcionModal  #idprecioventa").val('');

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

    let data = {
        gestor: parseInt($(this).data('id1')),
        sede: parseInt($(this).data('id2')),
        productoinventario: $(this).data('id3'),
        estadoasignacion: parseInt($(this).data('id4')),
        precioventa: parseFloat($(this).data('id5'))
    };

    console.log(data);
    $.ajax({
        method:"POST",
        data: data,
        url:url
    }).done(function(asignsede){
        if (asignsede!=null){
            console.log(asignsede);
            $("#confirmarRecepcionModal  #idgestor").val(asignsede.idgestor);
            $("#confirmarRecepcionModal  #idsede").val(asignsede.idsede);
            $("#confirmarRecepcionModal  #idproductoinv").val(asignsede.idproductoinv);
            $("#confirmarRecepcionModal  #idestadoasign").val(asignsede.idestadoasign);
            $("#confirmarRecepcionModal  #idprecioventa").val(asignsede.idprecioventa);

            $("#confirmarRecepcionModal #codinv").text(asignsede.idproductoinv);
            $("#confirmarRecepcionModal  #producto").text(asignsede.producto);
            $("#confirmarRecepcionModal  #color").text(asignsede.color);
            $("#confirmarRecepcionModal  #tamanho").text(asignsede.tamanho);
            $("#confirmarRecepcionModal  #precioventa").text(asignsede.precioventa);
            $("#confirmarRecepcionModal  #stock").text(asignsede.stock);
            $("#confirmarRecepcionModal  #fechaenvio").text(asignsede.fechaenvio);
            $("#confirmarRecepcionModal #foto").attr("src",asignsede.foto).attr("hidden",asignsede.foto==="");
            $("#confirmarRecepcionModal  #comunidades").text(asignsede.comunidades);

        }
    }).fail(function (err) {
        console.log(err);
        $('#confirmarRecepcionModal').modal('hide');
        alert("Ocurrió un error");
    })
});


