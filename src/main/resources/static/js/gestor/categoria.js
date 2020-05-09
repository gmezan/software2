var contextPath = "http://localhost:8080";

$(document).on("click",".editar-Categoria", function(){
    $("#editModal  #codigo").val(' ');$("#editModal  #nombre").val('');
    $.ajax({
        method:"GET", url:contextPath + "/gestor/categoria/get?id=" + $(this).data('id')
    }).done(function(cat){
        if (cat!=null){
            $("#editModal  #codigo").val(cat.codigo).prop("readonly", true);
            $("#editModal #nombre").val(cat.nombre);
        }
    }).fail(function (err) {
        console.log(err);
        $('#editModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Categoria", function(){
    $("#editModal  #codigo").val('');
    $("#editModal  #nombre").val('');
});
$(document).on("click",".delete-Categoria", function(){
    $("#deleteModal #codigo").val($(this).data('id'));
});
$(document).ready(function() {
    if ($("#msgCategoria").text()==="ERROR"){
        $('#editModal').modal('show');
    }
});

