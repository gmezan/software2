var contextPath  = window.location.href;

$(document).on("click",".editar-Gestor", function(){
    $("#formModal input").val('');
    //$("#formModal  #idusuarios").val($(this).data('idusuarios'));
    $.ajax({
        method:"GET", url:contextPath + "/get?id=" + $(this).data('id')
    }).done(function(usu){
        if (usu!=null){
            $("#formModal #idusuarios").val(usu.idusuarios);
            $("#formModal #nombre").val(usu.nombre);
            $("#formModal #apellido").val(usu.apellido);
            $("#formModal #correo").val(usu.correo);
            $("#formModal #telefono").val(usu.telefono);
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});

$(document).ready(function() {
    if ($("#msgGestor").text()==="ERROR"){
        $('#formModal').modal('show');
    }
});