var contextPath  = window.location.href;

$(document).on("click",".edit-Artesano", function(){
    $("#editModal  #codigo").val(' ');$("#editModal  #nombre").val('');
    $("#editModal  #apellidopaterno").val('');$("#editModal  #apellidomaterno").val('');
    $("#editModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath+"/get?id="  + $(this).data('id')
    }).done(function(artesano){
        if (artesano!=null){
            $("#editModal  #codigo").val(artesano.codigo).prop("readonly", true);
            $("#editModal #nombre").val(artesano.nombre);
            $("#editModal  #apellidopaterno").val(artesano.apellidopaterno);
            $("#editModal  #apellidomaterno").val(artesano.apellidomaterno);
            $("#editModal  #comunidades").val(artesano.comunidades.codigo);
            $("#editModal  #formTitle").text('Editar Artesano');
        }
    }).fail(function (err) {
        console.log(err);
        $('#editModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Artesano", function(){
    $("#editModal  #codigo").val('').prop("readonly", false);$("#editModal  #nombre").val('');
    $("#editModal  #apellidopaterno").val('');$("#editModal  #apellidomaterno").val('');
    $("#editModal  #type").val('1');
    $("#editModal  #formTitle").text('Nuevo Artesano');
});
$(document).on("click",".delete-Artesano", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);
    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigo").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar este Artesano? Esta acción no se puede deshacer.");
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("El/La Artesana(o) no se puede borrar, esta asociada(o) a estos productos de inventario:")
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
            let r = [], j = -1;
            for (let key=0, size=data.length; key<size; key++){
                r[++j] ='<tr><td>';
                r[++j] = data[key].codigo;
                r[++j] = '</td><td>';
                r[++j] = data[key].producto;
                r[++j] = '</td><td>';
                r[++j] = data[key].cantidad;
                r[++j] = '</td></tr>';
            }
            $("#deleteModal #tbody").html(r.join(''));
        }
    }).fail(function (err) {
        console.log(err);
        $('#editModal').modal('hide');
        alert("Ocurrió un error");
    })


});
$(document).ready(function() {
    if ($("#msgArtesanos").text()==="ERROR"){
        $('#editModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});

