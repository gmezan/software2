var contextPath  = window.location.href;

$("#addForm #linea").on('change', function() {
    let option = this.value;
    if(option==="no"){
        $("#addForm #productos").find('option').remove().end();
    }
    else{
        $.ajax({
            method:"GET", url:contextPath+ "/getLinea?linea=" + option
        }).done(function(lista){
            if (lista!=null){
                let len = lista.length;
                $("#addForm #productos").empty();
                $("#addForm #productos").append("<option value=''>--- Seleccionar ---</option>");
                for( let i = 0; i<len; i++){
                    $("#addForm #productos").append("<option value='"+lista[i].codigonom+"'>"+lista[i].nombre+"</option>");
                }
            }
        }).fail(function (err) {
            console.log(err);
            alert("Ocurrió un error");
        })
    }

});

$("#addForm #codAdquisicion").on('change', function() {
    let cond = this.value==='0';
        $("#addForm #artesanoConsignacion").prop("hidden",cond).prop("disabled",cond);
        if (cond){
            $("#addForm #artesanos").empty();
        }else {
            let option = $("#addForm #comunidades").val();
            $.ajax({
                method:"GET", url:contextPath+ "/getArtesanos?comunidad=" + option
            }).done(function(lista){
                if (lista!=null){
                    let len = lista.length;
                    $("#addForm #artesanos").empty();
                    $("#addForm #artesanos").append("<option value=''>--- Seleccionar ---</option>");
                    for( let i = 0; i<len; i++){
                        $("#addForm #artesanos").append("<option value='"+lista[i].codigo+"'>"+lista[i].nombre+" "+lista[i].apellidopaterno+"</option>");
                    }
                }
            }).fail(function (err) {
                alert("Ocurrió un error");
            })
        }
});


$("#addForm #comunidades").on('change', function() {
    let option = this.value;
    if(!$("#addForm #artesanoConsignacion").prop("hidden")){
        $.ajax({
            method:"GET", url:contextPath+ "/getArtesanos?comunidad=" + option
        }).done(function(lista){
            if (lista!=null){
                let len = lista.length;
                $("#addForm #artesanos").empty();
                $("#addForm #artesanos").append("<option value=''>--- Seleccionar ---</option>");
                for( let i = 0; i<len; i++){
                    $("#addForm #artesanos").append("<option value='"+lista[i].codigo+"'>"+lista[i].nombre+" "+lista[i].apellidopaterno+"</option>");
                }
            }
        }).fail(function (err) {
            alert("Ocurrió un error");
        })
    }

});
$("#addForm #conDia").on('change', function() {

    $("#addForm #fechadia").prop("hidden",!this.checked).prop("disabled",!this.checked);
    $("#addForm #fechames").prop("hidden",this.checked).prop("disabled",this.checked);

});
