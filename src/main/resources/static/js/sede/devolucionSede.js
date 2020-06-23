var contextPath = window.location.href;

$(document).on("click", ".dev-Prod", function () {

    $("#devolucionModal  #idgestord").val($(this).data('id13'));
    $("#devolucionModal  #idseded").val($(this).data('id23'));
    $("#devolucionModal  #idproductoinvd").val($(this).data('id33'));
    $("#devolucionModal  #idestadoasignd").val($(this).data('id43'));
    $("#devolucionModal  #idprecioventad").val($(this).data('id53'));
    $("#devolucionModal  #cantDevol").val('');

//
});

$(document).ready(function() {
    if ($("#msgDevolucion").text()==="ERROR"){
        $("#devolucionModal").modal({show:true});
    }
});

