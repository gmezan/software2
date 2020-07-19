var contextPath  = window.location.href;

$(function() {

    let $checkbox = $("#confirmado1");
    console.log($checkbox.is(":checked"));
    $("#id\\.numerodocumento").prop("disabled",!$checkbox.is(":checked")).val('');
    $(".inputFile").prop("hidden",!$checkbox.is(":checked"));

    if ($("#msgVenta").text()==="ERROR"){
        $("#registrarVentaModal").modal({show:true, backdrop: 'static', keyboard: false });
    }
    //$("#id\\.numerodocumento").prop("disabled",true);
    $("body").on('change','#confirmado1', function () {
        $("#id\\.numerodocumento").prop("disabled",!this.checked);
        $(".inputFile").prop("hidden",!this.checked);
    });
}).on("click",".regis-Venta", function(){
//    $("#registrarVentaModal  input").val( '');

    $("#id\\.numerodocumento").prop("disabled",true);
    $("#confirmado1").prop("checked",false);
    $(".inputFile").prop("hidden",true);

    $("#registrarVentaModal  #idgestor").val(  $(this).data('id12'));
    $("#registrarVentaModal  #vendedor").val( $(this).data('id22'));
    $("#registrarVentaModal  #inventario").val( $(this).data('id32'));
    $("#registrarVentaModal  #precioventa").val(  $(this).data('id52'));
    $("#registrarVentaModal  #idestadoasign").val( $(this).data('id42'));

    $("#registrarVentaModal  #nombrecliente").val('');
    $("#registrarVentaModal  #tipodocumento").val('');
    $("#registrarVentaModal  #numerodocumento").val('');
    $("#registrarVentaModal  #fecha").val('');
    $("#registrarVentaModal  #lugarventa").val('');
    $("#registrarVentaModal  #rucdni").val('');
    $("#registrarVentaModal  #cantidad").val('');

    let url = contextPath + "/postV";

    let data = {
        idgestor: parseInt($(this).data('id12')),
        vendedor: parseInt($(this).data('id22')),
        inventario: $(this).data('id32'),
        idestadoasign: parseInt($(this).data('id42')),
        precioventa: parseFloat($(this).data('id52'))
    };

    console.log(data);
    $.ajax({
        method:"POST",
        data: data,
        url:url
    }).done(function(registrarventa){
        if (registrarventa!=null){
            $("#registrarVentaModal #cantAsignV").text(registrarventa.cantAsignV);
        }
        $("#registrarVentaModal .modal-footer button").attr("disabled",false);
    }).fail(function (err) {
        console.log(err);
        $('#registrarVentaModal').modal('hide');
        alert("Ocurrió un error");
    })

});




