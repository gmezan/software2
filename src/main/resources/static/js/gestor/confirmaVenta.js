const contextPath  = window.location.href;

$(function() {
    if ($("#msgVenta").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
}).on("click",".confirmar-Venta", function(){
    $("#formModal  #idventas").val($(this).data('id'));

    let url = contextPath + "/get?id=" + $(this).data('id');
    console.log(url);
    $.ajax({
        method:"GET", url:url
    }).done(function(ven){
        if (ven!=null){
            $("#formModal #id\\.tipodocumento").val(ven.id.tipodocumento);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
}).on("click", ".denegar-Venta", function () {
    $("#cancelarModal  #id").val('').val($(this).data('id'));
});
