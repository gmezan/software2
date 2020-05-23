var contextPath  = window.location.href;

$(document).on("click",".editar-Categoria", function(){
    $("#formModal  #codigo").val(' ');$("#formModal  #nombre").val('');
    $("#formModal  #type").val('0');
    $("#formModal  #formTitle").text('Editar Categoría');
    $.ajax({
        method:"GET", url:contextPath+"/get?id="+ $(this).data('id')
    }).done(function(cat){
        if (cat!=null){
            $("#formModal  #codigo").val(cat.codigo).prop("readonly", true);
            $("#formModal #nombre").val(cat.nombre);

        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Categoria", function(){
    $("#formModal  #codigo").val('').prop("readonly", false);
    $("#formModal  #nombre").val('');
    $("#formModal  #type").val('1');
    $("#formModal  #formTitle").text('Nueva Categoría');
});
$(document).on("click",".delete-Categoria", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);

    $.ajax({
        method:"GET", url:contextPath+"/has?id=" + id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigo").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar esta Categoría? Esta acción no se puede deshacer.")
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La categoría no se puede borrar, esta asociada a lo siguiente:")
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
    if ($("#msgCategoria").text()==="ERROR"){
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});

