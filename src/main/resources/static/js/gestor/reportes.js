const contextPath  = window.location.href;

const meses = [
    {numero:1, nombre:"Enero"},{numero:2, nombre:"Febrero"},{numero:3, nombre:"Marzo"},{numero:4, nombre:"Abril"},
    {numero:5, nombre:"Mayo"},{numero:6, nombre:"Junio"},{numero:7, nombre:"Julio"},{numero:8, nombre:"Agosto"},
    {numero:9, nombre:"Setiembre"},{numero:10, nombre:"Octubre"},{numero:11, nombre:"Noviembre"},{numero:12, nombre:"Diciembre"}
];

const trimestres = [
    {numero:1, nombre:"En-Feb-Mar"},{numero:2, nombre:"Abr-May-Jun"},
    {numero:3, nombre:"Jul-Ago-Set"},{numero:4, nombre:"Oct-Nov-Dic"}
];

const tipos = {'2':trimestres,'3':meses};

$(function () {
    let $ventaModal = $("#reporteVentaModal");

    $.ajax({method:"GET",url:contextPath+"/years"})
        .done(function (years) {
            let r = '';
            $.each(years, function () {
                r+=`<option value="${this}">${this}</option>`;
            });
            $ventaModal.find(" #years").append(r);
        })
        .fail(function(err){
            alert("Ocurrio un error: "+ err);
        });

    $("body").on('click','.reporte-Ventas',function () {
        $ventaModal.find(" #fields1 select").val(0);
        $ventaModal.find(" #fields2").prop("hidden",true).find(" #tipoSelectDiv").prop("hidden",true);
        }
    ).on('submit','#reporteForm', function () {
        if((parseInt($ventaModal.find(" #fields1 #ordenar").children("option:selected").val())===0)
            || (parseInt($ventaModal.find(" #fields1 #tipo").children("option:selected").val())===0)
                || (parseInt($ventaModal.find(" #fields2 #years").children("option:selected").val())===0)){
            alert("Debe seleccionar un tipo de reporte");
            return false;
        }
    });
    
    $ventaModal.find(" #tipo").on('change',function () {
        let val = this.value;
        if(val!=='0'){
            $ventaModal.find(" #fields2").prop("hidden",false).find(" #tipoSelectDiv").prop("hidden",val==='1');
            if(val!=='1'){
                let nombre;
                switch (val) {
                    case '2':
                        nombre='Trimestre';break;
                    case '3':
                        nombre='Mes';break;
                }
                let r ='';
                $.each(tipos[val],function () {
                    r+=makeOption(this);
                });
                $ventaModal.find(" #tipoSelectDiv").find(" label").html(nombre).end()
                    .find(" #tipoSelect").empty().append(r);
            }
        }
    })
    
});


function makeOption(x) {
    return '<option value="'+x.numero +'" >'+x.nombre + '</option> ';
}