var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Gestor", function(){
    $("#formModal #idusuarios").val('');
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal  #type").val('0');
    $("#formModal  #passwordField").prop("hidden",true).prop("disabled",true);
    $("#formModal  #formTitle").text('Editar Sede');
    $.ajax({
        method:"GET", url:contextPath + $(this).data('id')
    }).done(function(gestor){
        if (gestor!=null){
            $("#formModal #idusuarios").val(gestor.idusuarios).prop("readonly", true);
            $("#formModal #nombre").val(gestor.nombre);
            $("#formModal #apellido").val(gestor.apellido);
            $("#formModal #correo").val(gestor.correo);
            $("#formModal #telefono").val(gestor.telefono);
            $("#formModal #foto").attr("src",gestor.foto).attr("hidden",gestor.foto==="");
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Gestor", function(){
    $("#formModal  #formTitle").text('Nueva Sede');
    $("#formModal #idusuarios").val('').prop("readonly", false);
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal  #type").val('1');
    $("#formModal  #passwordField").prop("hidden",false).prop("disabled",false);
    $("#formModal #password").val('');
    $("#formModal #foto").attr("hidden",true);
});
$(document).on("click",".delete-Gestor", function(){
    $("#deleteModal #idusuarios").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgSedes").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});