var contextPath = "http://localhost:8080";

$(document).on("click",".editar-Venta", function(){
    $("#formModal  #numerodocumento").val(' ');$("#formModal  #rucdni").val('');$("#formModal  #nombrecliente").val('');
    $.ajax({
        method:"GET", url:contextPath + "/gestor/venta/get?id=" + $(this).data('id')
    }).done(function(ven){
        if (ven!=null){
            $("#formModal  #numerodocumento").val(ven.tipodocumento).prop("readonly", true);
            $("#formModal #rucdni").val(ven.rucdni);
            $("#formModal #nombrecliente").val(ven.nombrecliente);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Venta", function(){
    $("#formModal  #numerodocumento").val('');
    $("#formModal  #rucdni").val('');
    $("#formModal  #nombrecliente").val('');
});
$(document).on("click",".delete-Venta", function(){
    $("#deleteModal #numerodocumento").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#formModal').modal('show');
    }
});