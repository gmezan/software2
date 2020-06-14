var contextPath = window.location.href;

$(document).on("click", ".dev-Prod", function () {

    $("#devolucionModal  #idgestor3").val('');
    $("#devolucionModal  #idsede3").val('');
    $("#devolucionModal  #idproductoinv3").val('');
    $("#devolucionModal  #idestadoasign3").val('');
    $("#devolucionModal  #idprecioventa3").val('');


    $("#devolucionModal  #idgestor3").val($(this).data('id13'));
    $("#devolucionModal  #idsede3").val($(this).data('id23'));
    $("#devolucionModal  #idproductoinv3").val($(this).data('id33'));
    $("#devolucionModal  #idestadoasign3").val($(this).data('id43'));
    $("#devolucionModal  #idprecioventa3").val($(this).data('id53'));


});



