var contextPath  = window.location.href;

$(document).on("click",".edit-Producto", function(){
    $("#formModal #codigonom").val('');
    $("#formModal #nombre").val('');
    $("#formModal #codigodesc").val('');
    $("#formModal #descripcion").val('');
    $("#formModal  #type").val('0');
    $.ajax({
        method:"GET", url:contextPath +"/get?id=" + $(this).data('id')
    }).done(function(producto){
        if (producto!=null){
            $("#formModal #codigonom").val(producto.codigonom).prop("readonly", true);
            $("#formModal #nombre").val(producto.nombre);
            $("#formModal #codigodesc").val(producto.codigodesc);
            $("#formModal #descripcion").val(producto.descripcion);
            $("#formModal #codigolinea").val(producto.codigolinea);
            $("#formModal  #formTitle").text('Editar Producto');
        }
    }).fail(function (err) {
        console.log(err);
        $('#formModal').modal('hide');
        alert("Ocurrió un error");
    })
});
$(document).on("click",".new-Producto", function(){
    $("#formModal #codigonom").val('').prop("readonly", false);$("#formModal  #codigodesc").val('');
    $("#formModal   #nombre").val('');$("#formModal  #descripcion").val('');
    $("#formModal  #type").val('1');
    $("#formModal  #formTitle").text('Nuevo Producto');
});
$(document).on("click",".delete-Producto", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);

    $.ajax({
        method:"GET", url:contextPath+"/has?id="  + id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigonom").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar este Producto? Esta acción no se puede deshacer.");
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("La comunidad no se puede borrar, está asociada a " + data.length + " elementos de inventario:");
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
            let r = [], j = -1;
            for (let key=0, size=data.length; key<size; key++){

                r[++j] ='<tr><td>';
                r[++j] = data[key].codigo;
                r[++j] = '</td><td>';
                r[++j] = data[key].comunidad;
                r[++j] = '</td><td>';
                r[++j] = data[key].categoria;
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
    if ($("#msgProducto").text()==="ERROR"){
        //$('#formModal').modal('show');
        $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
    }
});








