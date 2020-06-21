var contextPath = window.location.href;

$("#addForm #linea").on('change', function () {
    let prodList = $("#addForm #productos");
    let option = this.value;
    if (option === "no") {

        prodList.empty();
        prodList.append("<option value=''>-Debe elegir una línea-</option>");
    } else {
        $.ajax({
            method: "GET", url: contextPath + "/getLinea?linea=" + option
        }).done(function (lista) {
            if (lista != null) {
                let len = lista.length;
                prodList.empty();
                if (lista.length !== 0) {
                    prodList.append("<option value=''>--- Seleccionar ---</option>");
                    for (let i = 0; i < len; i++) {
                        prodList.append("<option value='" + lista[i].codigonom + "'>" + lista[i].nombre + "</option>");
                    }
                } else {
                    prodList.append("<option value=''>--- No tiene productos ---</option>");
                }
            }
        }).fail(function (err) {
            console.log(err);
            alert("Ocurrió un error");
        })
    }

});

$("#addForm #codAdquisicion").on('change', function () {
    let cond = this.value === '0';
    $("#addForm #artesanoConsignacion").prop("hidden", cond).prop("disabled", cond);
    $("#addForm #vencimientoConsignacion").prop("hidden", cond).prop("disabled", cond);
    if (cond) {
        $("#addForm #artesanos").empty();
    } else {
        let option = $("#addForm #comunidades").val();
        updateArtesanos(option);
    }
});
$("#addForm #comunidades").on('change', function () {
    let option = this.value;
    let cond = ($("#addForm #codAdquisicion").val() === '1');
    if (cond && !$("#addForm #artesanoConsignacion").prop("hidden")) {
        updateArtesanos(option);
    }


});

function updateArtesanos(option) {
    let artList = $("#addForm #artesanos");
    if (option === '') {
        artList.empty();
        artList.append("<option value=''>-Debe elegir una comunidad-</option>");
    } else {
        $.ajax({
            method: "GET", url: contextPath + "/getArtesanos?comunidad=" + option
        }).done(function (lista) {
            if (lista != null) {
                let len = lista.length;
                artList.empty();
                if (lista.length !== 0) {
                    artList.append("<option value=''>--- Seleccionar ---</option>");
                    for (let i = 0; i < len; i++) {
                        artList.append("<option value='" + lista[i].codigo + "'>" + lista[i].nombre + " " + lista[i].apellidopaterno + "</option>");
                    }
                } else {
                    artList.append("<option value=''>--- No tiene artesanos ---</option>");
                }
            }

        }).fail(function (err) {
            alert("Ocurrió un error");
        })
    }


}


$("#addForm #conDia").on('change', function () {

    $("#addForm #fechadia").prop("hidden", !this.checked).prop("disabled", !this.checked);
    $("#addForm #fechames").prop("hidden", this.checked).prop("disabled", this.checked);

});
$("#addForm #fechadia").on('change', function () {
    let fecha = $("#addForm #fechadia").val().split("-");
    let fechasig = new Date(fecha[0], fecha[1] - 1, fecha[2]);
    fechasig.setDate(fechasig.getDate() + 1);
    setminfecha(fechasig);

});
$("#addForm #fechames").on('change', function () {

    let fecha = $("#addForm #fechames").val().split("-");
    let fechasig = new Date(fecha[0], fecha[1], 1);
    setminfecha(fechasig);


});

$(document).on("click", ".add-inventario", function () {
    $("#addModal #cant").val('1');
    $("#addModal #msgc").text('');
    $("#addModal #codinvAdd").val($(this).data('id'));
});

$(document).ready(function () {
    if ($("#msgError").text() === "ERROR DE CANTIDAD") {
        //$('#formModal').modal('show');
        $('#addModal').modal({show: true, backdrop: 'static', keyboard: false});
    }

});

$(document).on("click", ".edit-inventario", function () {
    $("#editModal #costotejedor").val('');
    $("#editModal #costomosqoy").val('');
    $("#editModal #facilitador").val('');
    $("#editModal #fechavencimientoconsignacion").val('');

    $("#editModal #codAdquisicion").val('');


    $("#editModal .errorInv").text('');
    $("#editModal .modal-title").text("Editar " + $(this).data('id'));
    $("#editModal #codigoinventario").val($(this).data('id'));
    $("#editModal #vencimientoConsignacion").prop("hidden", true);


    $.ajax({
        method: "GET", url: contextPath + "/getInv?id=" + $(this).data('id')
    }).done(function (inv) {
        if (inv != null) {
            $("#editModal #codAdquisicion").val(inv.codAdquisicion);
            $("#editModal #costotejedor").val(inv.costotejedor);
            $("#editModal #costomosqoy").val(inv.costomosqoy);
            $("#editModal #facilitador").val(inv.facilitador);
            if (inv.codAdquisicion === 1) {
                $("#editModal #vencimientoConsignacion").prop("hidden", false);
                $("#editModal #fechavencimientoconsignacion").val(inv.fechavencimientoconsignacion);
                let fechasig;
                if (inv.dia === 0) {
                    fechasig = new Date(inv.anho, inv.mes, 1);
                } else {
                    fechasig = new Date(inv.anho, inv.mes - 1, inv.dia);
                    fechasig.setDate(fechasig.getDate() + 1);
                }
                setminfecha(fechasig);
            }
        }
    }).fail(function (err) {
        alert("Ocurrió un error");
        $('#editModal').modal('hide');
    })
});

function setminfecha(fechasig) {
    let month = fechasig.getMonth() + 1;
    if (month < 10) {
        fechastr = fechasig.getFullYear() + "-0" + month;
    } else {
        fechastr = fechasig.getFullYear() + "-" + month;
    }
    if (fechasig.getDate() < 10) {
        fechastr = fechastr + "-0" + fechasig.getDate();
    } else {
        fechastr = fechastr + "-" + fechasig.getDate();
    }
    $("#fechavencimientoconsignacion").prop("min", fechastr);
}

$(document).ready(function () {
    if ($("#msgError").text() === "ERROR DE EDICION") {
        //$('#formModal').modal('show');
        $('#editModal').modal({show: true, backdrop: 'static', keyboard: false});
    }
});

$(document).on("click", ".delete-inventario", function () {

    $("#deleteModal .modal-title").text("Borrar " + $(this).data('id'));

    $("#deleteModal #codDelete").val($(this).data('id'));
});
$(document).on("click", ".show-foto", function () {

    $("#showFoto #fototitle").text($(this).data('id'));
    let foto = $("#showFoto #fotoinv");
    foto.attr("src", "");
    foto.attr("alt", "Cargando foto...");
    $.ajax({
        method: "GET", url: contextPath + "/getInv?id=" + $(this).data('id')
    }).done(function (inv) {
        if (inv != null) {
            foto.attr("src", inv.foto);
            foto.attr("alt", "No se encuentra una foto :(");
        }
    }).fail(function (err) {
        alert("Ocurrió un error");
        $('#editModal').modal('hide');
    })

});