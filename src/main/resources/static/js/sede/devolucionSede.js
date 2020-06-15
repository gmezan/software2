var contextPath = window.location.href;

$(document).on("click", ".dev-Prod", function () {

    $("#devolucionModal  #id13").val($(this).data('id13'));
    $("#devolucionModal  #id23").val($(this).data('id23'));
    $("#devolucionModal  #id33").val($(this).data('id33'));
    $("#devolucionModal  #id43").val($(this).data('id43'));
    $("#devolucionModal  #id53").val($(this).data('id53'));
    $("#devolucionModal  #cantDevol").val('');


});

$(document).ready(function() {
    if ($("#msgDevolucion").text()==="ERROR"){
        $("#devolucionModal").modal({show:true});
    }
});

