var contextPath  = window.location.href;

$(document).on("click",".problem-Produc", function(){

    $("#problemaModal  #idgestor12").val('');
    $("#problemaModal  #idsede22").val('');
    $("#problemaModal  #idproductoinv32").val('');
    $("#problemaModal  #idestadoasign42").val('');
    $("#problemaModal  #idprecioventa52").val('');

    let url = contextPath + "/get?idgestor=" + $(this).data('id12') + "&idsede=" + $(this).data('id22') +
        + "&idproductoinv=" + $(this).data('id32') + "&idestadoasign42=" + $(this).data('id42') + "&idprecioventa52=" + $(this).data('id52') ;

    console.log(url);

    $.ajax({
        method:"GET", url:url
    }).done(function(asignsede){
        if (asignsede!=null){
            console.log(asignsede);
            $("#problemaModal  #idgestor12").val(asignsede.id.gestor.idusuarios);
            $("#problemaModal  #idsede22").val(asignsede.id.sede.idusuarios);
            $("#problemaModal  #idproductoinv32").val(asignsede.id.productoinventario.codigoinventario);
            $("#problemaModal  #idestadoasign42").val(asignsede.idestadoasign);
            $("#problemaModal  #idprecioventa52").val(asignsede.idprecioventa);

        }
    }).fail(function (err) {
        console.log(asignsede);
        console.log(err);
        $('#problemaModal').modal('hide');
        alert("Ocurrió un error");
    })
});


