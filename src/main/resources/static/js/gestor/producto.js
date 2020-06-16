const contextPath  = window.location.href;
$(function() {($("#msgProducto").text()==="ERROR") && $('#formModal').modal({show: true, backdrop: 'static', keyboard: false });
}).on("click",".edit-Producto", function(){
    $("#formModal input").val('');
    $("#formModal  #type").val('0');
    $.ajax({method:"GET", url:contextPath +"/get?id=" + $(this).data('id')
    }).done(function(producto){
        if (producto!=null){
            $("#formModal #codigonom").val(producto.codigonom).prop("readonly", true);
            $("#formModal #nombre").val(producto.nombre);
            $("#formModal #codigodesc").val(producto.codigodesc);
            $("#formModal #descripcion").val(producto.descripcion);
            $("#formModal #codigolinea").val(producto.codigolinea);
            $("#formModal  #formTitle").text('Editar Producto');
        }
    }).fail(function (err) {alert("Ocurrió un error");$('#formModal').modal({show: false});
    })
}).on("click",".new-Producto", function(){
    $("#formModal").find(" #formTitle").text('Nuevo Producto').end().find(" input").val('').prop("readonly",false).end()
        .find(" #type").val('1');
}).on("click",".delete-Producto", function(){
    let id = $(this).data('id');
    $("#deleteModal #buttonDelete").prop("hidden",true);
    $("#deleteModal #deleteModalBody #deleteModalBodyP").text("");
    $("#deleteModal #deleteModalBody #tableModal").prop("hidden",true);

    $.ajax({method:"GET", url:contextPath+"/has?id=" +id
    }).done(function(data){
        if (data==null || data.length === 0){
            $("#deleteModal #codigonom").val(id);
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("¿Seguro que desea borrar este Producto? Esta acción no se puede deshacer.");
            $("#deleteModal #buttonDelete").prop("disabled",false).prop("hidden",false);
        }
        else {
            $("#deleteModal #deleteModalBody #deleteModalBodyP").text("El producto no se puede borrar, está asociado a "+data.length+" elementos de inventario:");
            $("#deleteModal #deleteModalBody #tableModal").prop("hidden",false);
            $("#deleteModal #buttonDelete").prop("disabled",true).prop("hidden",true);
            let r='';
            for (let i=0, size=data.length; i<size; i++){
                r+='<tr><td>'+data[i].codigo+'</td><td>'+data[i].comunidad+'</td><td>'+data[i].categoria+'</td></tr>';
            }
            $("#deleteModal #tbody").html(r);
        }
    }).fail(function (err) {alert("Ocurrió un error");$('#deleteModal').modal({show: false});
    })
});










