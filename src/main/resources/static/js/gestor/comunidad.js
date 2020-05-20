var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".editar-Comunidad", function(){
    $("#formModal  #codigo").val(' ');$("#formModal  #nombre").val('');
    $("#formModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath+ $(this).data('id')
    }).done(function(com){
        if (com!=null){
            $("#formModal  #codigo").val(com.codigo).prop("readonly", true);
            $("#formModal #nombre").val(com.nombre);
            $("#formModal  #formTitle").text('Editar Comunidad');
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Comunidad", function(){
    $("#formModal  #codigo").val('').prop("readonly", false);
    $("#formModal  #nombre").val('');$("#formModal  #type").val('1');
    $("#formModal  #formTitle").text('Nueva Comunidad');
});
$(document).on("click",".delete-Comunidad", function(){
    $("#deleteModal #codigo").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgComunidad").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});