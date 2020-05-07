$(document).on("click",".editar-Categoria", function(){
    $("#editModal  #codigo").val('');
    $("#editModal  #nombre").val('');
    var url = "http://localhost:8080/gestor/categoria/get?id=" + $(this).data('id');
    console.log("url");

    $.ajax({
        method:"GET",
        url:url
    }).done(function(cat){
        if (cat!=null){
            $("#editModal  #codigo").val(cat.codigo);
            $("#editModal #nombre").val(cat.nombre);
        }
    })

});
$(document).on("click",".new-Categoria", function(){

});
$(document).on("click",".delete-Categoria", function(){
    var id = $(this).data('id');
    console.log(id);
    $("#deleteModal #codigo").val(id);
});

