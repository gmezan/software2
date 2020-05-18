var contextPath  = window.location.href;

$(document).on("click",".editar-Venta", function(){
    $.ajax({
        method:"GET", url:contextPath + "/get?id1=" + $(this).data('id1') + "&id2=" + $(this).data('id2')
    }).done(function(ven){
        if (ven!=null){
            $("#formModal #rucdni").val(ven.rucdni);
            $("#formModal #nombrecliente").val(ven.nombrecliente);
            $("#formModal #id12").val(ven.id.numerodocumento);
            $("#formModal #id21").val(ven.id.tipodocumento);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});

$(document).on("click",".delete-Venta", function(){
    $("#deleteModal #id1").val($(this).data('id1'));
    $("#deleteModal #id2").val($(this).data('id2'));
});
$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#formModal').modal('show');
    }
});