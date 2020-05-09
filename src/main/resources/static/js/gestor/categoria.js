var contextPath = "http://localhost:8080";

$(document).on("click",".editar-Categoria", function(){
    $("#editModal  #codigo").val('');
    $("#editModal  #nombre").val('');
    var url = contextPath + "/gestor/categoria/get?id=" + $(this).data('id');
    console.log("url");

    $.ajax({
        method:"GET",
        url:url
    }).done(function(cat){
        if (cat!=null){
            $("#editModal  #codigo").val(cat.codigo);
            $("#editModal #nombre").val(cat.nombre);
        }
    }).fail(function (err) {
        console.log(err);
        $('#editModal').modal('hide');
        alert("Ocurrió un error");
    })

});
$(document).on("click",".new-Categoria", function(){

});
$(document).on("click",".delete-Categoria", function(){
    $("#deleteModal #codigo").val($(this).data('id'));
});

