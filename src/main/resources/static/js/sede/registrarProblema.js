var contextPath  = window.location.href;

$(document).on("click",".problem-Produc", function(){

    $("#problemaModal  #idgestor12").val('');
    $("#problemaModal  #idsede22").val('');
    $("#problemaModal  #idproductoinv32").val('');
    $("#problemaModal  #idfechaenvio42").val('');

    let url = contextPath + "/get?idgestor=" + $(this).data('id12') + "&idsede=" + $(this).data('id22')
        + "&idproductoinv=" + $(this).data('id32') + "&idfechaenvio=" + $(this).data('id42');

    console.log(url);

    $.ajax({
        method:"GET", url:url
    }).done(function(asignsede){
        if (asignsede!=null){
            console.log(asignsede);
            $("#problemaModal  #idgestor12").val(asignsede.id.gestor.idusuarios);
            $("#problemaModal  #idsede22").val(asignsede.id.sede.idusuarios);
            $("#problemaModal  #idproductoinv32").val(asignsede.id.productoinventario.codigoinventario);
            $("#problemaModal  #idfechaenvio42").val(asignsede.id.fechaenvio);

        }
    }).fail(function (err) {
        console.log(asignsede);
        console.log(err);
        $('#problemaModal').modal('hide');
        alert("Ocurrió un error");
    })
});


