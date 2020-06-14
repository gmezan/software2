var contextPath  = window.location.href;

$(document).on("click",".regis-Venta", function(){
    $("#registrarVentaModal  input").val( '');
/*
    $("#registrarVentaModal  #idgestor").val( '');
    $("#registrarVentaModal  #idsede").val( '');
    $("#registrarVentaModal  #nombrecliente").val('');
    $("#registrarVentaModal  #tipodocumento").val('');
    $("#registrarVentaModal  #numerodocumento").val('');
    $("#registrarVentaModal  #fecha").val('');
    $("#registrarVentaModal  #lugarventa").val('');
    $("#registrarVentaModal  #rucdni").val('');
    $("#registrarVentaModal  #cantidad").val('');
    $("#registrarVentaModal  #idproductoinvlabel").text('');
    $("#registrarVentaModal  #idproductoinvinput").val('');
    $("#registrarVentaModal  #idprecioventa").val( '');
    $("#registrarVentaModal  #idestadoasign").val( '');*/

    let url = contextPath + "/post";

    $.ajax({
        async: false,
        dataType : "json",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method:"POST", url:url,
        data: JSON.stringify({
            gestor: parseInt($(this).data('id12')),
            sede: parseInt($(this).data('id22')),
            productoinventario: $(this).data('id32'),
            estadoasignacion: $(this).data('id42'),
            precioventa: $(this).data('id52'),
        })
    }).done(function(regisventa){
        if (regisventa!=null){
            console.log(regisventa);
            $("#registrarVentaModal  #idgestor").val(regisventa.idgestor);
            $("#registrarVentaModal  #idsede").val(regisventa.idsede);
            $("#registrarVentaModal  #idproductoinv").val(regisventa.idproductoinv);
            $("#registrarVentaModal  #idestadoasign").val(regisventa.idestadoasign);
            $("#registrarVentaModal  #idprecioventa").val(regisventa.idprecioventa);

            $("#registrarVentaModal  #idproductoinvlabel").text(regisventa.idproductoinv1);

        }
    }).fail(function (err) {
        console.log(err);
        $("#registrarVentaModal").modal('hide');
        alert("Ocurrió un error");
    })


});


$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $("#registrarVentaModal").modal({show:true});
    }
});

