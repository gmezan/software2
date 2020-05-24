var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".info-Sede", function(){
    $("#infoModal #id").val($(this).data('id'));

    $.ajax({
        method:"GET", url:contextPath +$(this).data('id')
    }).done(function(sede){
        if (sede!=null){
            $("#infoModal  #nombre").val(sede.nombre);
            $("#infoModal  #apellido").val(sede.apellido);
            $("#infoModal  #telefono").val(sede.telefono);
            $("#infoModal  #correo").val(sede.correo);
        }
    }).fail(function (err) {
        console.log(err);
        $('#infoModal').modal('hide');
        alert("Ocurrió un error");
    })
});