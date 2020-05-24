var contextPath  = window.location.href;

$(document).on("click",".editar-Comunidad", function(){
    $("#formModal  #codigo").val(' ');$("#formModal  #nombre").val('');
    $("#formModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath + "/get?id=" + $(this).data('id')
    }).done(function(com){
        if (com!=null){
            $("#formModal  #codigo").val(com.codigo).prop("readonly", true);
            $("#formModal #nombre").val(com.nombre);
            $("#formModal  #formTitle").text('Editar Comunidad');
            $("#formModal  #formSavebtn").text('Actualizar');
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
    $("#formModal  #formSavebtn").text('Registrar');
});

$(document).on("click",".delete-Comunidad", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);
    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigo").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar esta Comunidad? Esta acción no se puede deshacer.")
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La comunidad no se puede borrar, está asociada a " + data.length + " artesanos:")
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
            let r = [], j = -1;
            for (let key=0, size=data.length; key<size; key++){

                r[++j] ='<tr><td>';
                r[++j] = data[key].codigo;
                r[++j] = '</td><td>';
                r[++j] = data[key].nombre;
                r[++j] = '</td><td>';
                r[++j] = data[key].apellido;
                r[++j] = '</td></tr>';
            }
            $("#deleteModal #tbody").html(r.join(''));
        }
    }).fail(function (err) {
        console.log(err);
        $('#deleteModal').modal('hide');
        alert("Ocurrió un error");
    })
});




$(document).ready(function() {
    if ($("#msgComunidad").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});