var contextPath  = window.location.href;

$(document).on("click",".edit-Sede", function(){
    $("#formModal #idusuarios").val('');
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal  #type").val('0');
    $("#formModal  #passwordField").prop("hidden",true).prop("disabled",true);
    $("#formModal  #formTitle").text('Editar Sede');
    $.ajax({
        method:"GET", url:contextPath + "/get?id=" + $(this).data('id')
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
$(document).on("click",".delete-Sede", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);



    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigo").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar esta Sede? Esta acción no se puede deshacer.")
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La Sede no se puede borrar, está asociada a " + data.length + " ventas:")
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
            let r = [], j = -1;
            for (let key=0, size=data.length; key<size; key++){
                r[++j] ='<tr><td>';
                r[++j] = data[key].rucdni;
                r[++j] = '</td><td>';
                r[++j] = data[key].cliente;
                r[++j] = '</td><td>';
                r[++j] = data[key].vendedor;
                r[++j] = '</td></tr>';
            }
            $("#deleteModal #tbody").html(r.join(''));
        }
    }).fail(function (err) {
        console.log(err);
        $('#deleteModal').modal('hide');
        alert("Ocurrió un error");
    })




    $("#deleteModal #idusuarios").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgSedes").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});














