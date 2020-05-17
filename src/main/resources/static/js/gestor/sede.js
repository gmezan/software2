var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Gestor", function(){
    $("#formModal #idusuarios").val('');
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal #password").val('');
    $.ajax({
        method:"GET", url:contextPath + $(this).data('id')
    }).done(function(gestor){
        if (gestor!=null){
            $("#formModal #idusuarios").val(gestor.idusuarios).prop("readonly", true);
            $("#formModal #nombre").val(gestor.nombre);
            $("#formModal #apellido").val(gestor.apellido);
            $("#formModal #correo").val(gestor.correo);
            $("#formModal #telefono").val(gestor.telefono);
            $("#formModal #password").val(gestor.password);
            $("#formModal #foto").attr("src",gestor.foto);
            if (gestor.foto===""){
                $("#formModal #foto").attr("hidden",true);
            }

        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Gestor", function(){
    $("#formModal #idusuarios").val('');
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal #password").val('');
});
$(document).on("click",".delete-Gestor", function(){
    $("#deleteModal #idusuarios").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgSedes").text()==="ERROR"){
        $('#formModal ').modal('show');
    }
});