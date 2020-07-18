const contextPath  = window.location.href;

$(document).on("click",".editar-Venta", function(){
    $.ajax({
        method:"GET", url:contextPath + "/get?id=" + $(this).data('id')
    }).done(function(ven){
        if (ven!=null){
            $("#formModal #idventas").val(ven.idventas);
            $("#formModal #rucdni").val(ven.rucdni);
            $("#formModal #nombrecliente").val(ven.nombrecliente);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});

$(document).on("click", ".cancelar-Venta", function () {
    $("#cancelarModal  #id11").val('');
    $("#cancelarModal  #id22").val('');

    $("#cancelarModal  #id11").val($(this).data('id1'));
    $("#cancelarModal  #id22").val($(this).data('id2'));
});

$(document).on("click",".delete-Venta", function(){
    $("#deleteModal #id1").val($(this).data('id1'));
    $("#deleteModal #id2").val($(this).data('id2'));
});
$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});