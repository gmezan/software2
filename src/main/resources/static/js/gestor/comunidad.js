var contextPath = "http://localhost:8080";

$(document).on("click",".editar-Comunidad", function(){
    $("#formModal  #codigo").val(' ');$("#formModal  #nombre").val('');
    $.ajax({
        method:"GET", url:contextPath + "/gestor/comunidad/get?id=" + $(this).data('id')
    }).done(function(com){
        if (com!=null){
            $("#formModal  #codigo").val(com.codigo).prop("readonly", true);
            $("#formModal #nombre").val(com.nombre);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Comunidad", function(){
    $("#formModal  #codigo").val('');
    $("#formModal  #nombre").val('');
});
$(document).on("click",".delete-Comunidad", function(){
    $("#deleteModal #codigo").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgComunidad").text()==="ERROR"){
        $('#formModal').modal('show');
    }
});