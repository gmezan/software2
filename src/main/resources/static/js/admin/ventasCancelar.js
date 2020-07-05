var contextPath  = window.location.href;

$(document).on("click", ".confirmar", function () {
    $("#confirmarModal  #id11").val('');
    $("#confirmarModal  #id12").val('');

    $("#confirmarModal  #id11").val($(this).data('id1'));
    $("#confirmarModal  #id12").val($(this).data('id2'));
});

$(document).on("click", ".rechazar", function () {
    $("#rechazarModal  #id21").val('');
    $("#rechazarModal  #id22").val('');

    $("#rechazarModal  #id21").val($(this).data('id1'));
    $("#rechazarModal  #id22").val($(this).data('id2'));
});

