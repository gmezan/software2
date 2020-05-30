var contextPath  = window.location.href+"/get?id=";

$(document).on("click",".edit-Gestor", function(){
    $("#formModal #idusuarios").val('');
    $("#formModal #nombre").val('');
    $("#formModal #apellido").val('');
    $("#formModal #correo").val('');
    $("#formModal #telefono").val('');
    $("#formModal  #type").val('0');
    $("#formModal  #passwordField").prop("hidden",true).prop("disabled",true);
    $("#formModal  #formTitle").text('Editar Gestor');
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
    $("#formModal  #formTitle").text('Nuevo Gestor');
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
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);


    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data[0].length === 0 && data[1].length === 0){
            console.log(data);
            $("#deleteModal #idusuarios").val($(this).data('id'));
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar esta Sede? Esta acción no se puede deshacer.");
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            console.log(data);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La Sede no se puede borrar, está asociada a los siguientes Ventas y Prodcutos Asignados");
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);

            if(data[0].length===0){
            }else{

                let r = [], j = -1;
                for (let key=0, size=data[0].length; key<size; key++){
                    r[++j] ='<tr><td>';
                    r[++j] = data[0][key].rucdni;
                    r[++j] = '</td><td>';
                    r[++j] = data[0][key].cliente;
                    r[++j] = '</td><td>';
                    r[++j] = data[0][key].vendedor;
                    r[++j] = '</td></tr>';
                }
                $("#deleteModal #tbody").html(r.join(''));

            }
            if(data[1].length===0){
            }else{
                let rr = [], jj = -1;
                let ventas = data[1];
                for (let key=0, size=data[1].length; key<size; key++){
                    rr[++jj] ='<tr><td>';
                    rr[++jj] = ventas[key].sede;
                    rr[++jj] = '</td><td>';
                    rr[++jj] = ventas[key].stock;
                    rr[++jj] = '</td><td>';
                    rr[++jj] = ventas[key].vendedor;
                    rr[++jj] = '</td></tr>';
                }
                $("#deleteModal #tbody1").html(rr.join(''));
            }



        }
    }).fail(function (err) {
        console.log(err);
        $('#deleteModal').modal('hide');
        alert("Ocurrió un error");
    });
});

$(document).ready(function() {
    if ($("#msgGestores").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});