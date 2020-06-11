var contextPath  = window.location.href;

$(document).on("click",".edit-Sede", function(){
    $("#formModal input").val('');
    $("#formModal  #type").val('0');
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
    $("#formModal input").val('').prop("readonly",false);
    $("#formModal  #type").val('1');
    $("#formModal #foto").attr("hidden",true);
    $("#formModal  #formTitle").text('Nueva sede');
});
$(document).on("click",".delete-Sede", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal1").prop("hidden",true);
    $("#deleteModal #deleteModalBody #tableModal2").prop("hidden",true);


    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data[0].length === 0 && data[1].length === 0){
            $("#deleteModal #idusuarios").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar esta Sede? Esta acción no se puede deshacer.");
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La Sede no se puede borrar, está asociada a los siguientes Ventas y/o Prodcutos Asignados");

            let r = [], j = -1;
            if(data[0].length!==0){
                for (let key=0, size=data[0].length; key<size; key++){
                    r[++j] ='<tr><td>';
                    r[++j] = data[0][key].rucdni;
                    r[++j] = '</td><td>';
                    r[++j] = data[0][key].cliente;
                    r[++j] = '</td><td>';
                    r[++j] = data[0][key].vendedor;
                    r[++j] = '</td></tr>';
                }
                $("#deleteModal #deleteModalBody #tableModal1").prop("hidden",false);
                $("#deleteModal #tbody").html(r.join(''));

            }
            if(data[1].length!==0){
                r = []; j = -1;
                for (let key=0, size=data[1].length; key<size; key++){
                    r[++j] ='<tr><td>';
                    r[++j] = data[1][key].sede;
                    r[++j] = '</td><td>';
                    r[++j] = data[1][key].stock;
                    r[++j] = '</td><td>';
                    r[++j] = data[1][key].vendedor;
                    r[++j] = '</td></tr>';
                }
                $("#deleteModal #deleteModalBody #tableModal2").prop("hidden",false);
                $("#deleteModal #tbody2").html(r.join(''));
            }
        }
    }).fail(function (err) {
        console.log(err);
        $('#deleteModal').modal('hide');
        alert("Ocurrió un error");
    });


});


$(document).ready(function() {
    if ($("#msgSedes").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});














