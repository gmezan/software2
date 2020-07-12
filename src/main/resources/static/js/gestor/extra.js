$(document).ready(function () {
    let tableContainer = $(".table-responsive");
    let fakeContainer = $(".fakescroll");
    updatefakeScroll();

    fakeContainer.scroll(function () {
        tableContainer.scrollLeft(fakeContainer.scrollLeft());

    });
    tableContainer.scroll(function () {
        fakeContainer.scrollLeft(tableContainer.scrollLeft());

    });

    let tablawrapper = $("#dataTable_wrapper");
    tablawrapper.children().eq(0).appendTo("#newFilterLength");
    tablawrapper.children().eq(1).appendTo("#newtablefoot");

    $("#dataTable_filter label input").on('input', function () {
        updatefakeScroll();
    });
    $(".card-body").on('click', function () {
        updatefakeScroll();
    });
    $('.show-foto').removeAttr('disabled');
    $(document).on("click", ".show-foto", function () {
        let showfoto = $("#showFoto #fotoinv");
        showfoto.attr("src", "");
        $("#showFoto #fototitle").text($(this).data('id'));
        let id='#'+$(this).data('id')+'photo';
        let url= $(id).attr('src');
        showfoto.attr("src", url);


    });
    $('.show-fotoU').removeAttr('disabled');
    $(document).on("click", "#newtablefoot", function () {
        $('.show-fotoU').removeAttr('disabled');
        $('.show-foto').removeAttr('disabled');
    });
    $(document).on("click", ".show-fotoU", function () {
        let showfoto = $("#showFotoU #fotoinv");
        showfoto.attr("src", "");
        let idname='#'+$(this).data('id')+'name';
        let idlast='#'+$(this).data('id')+'last';
        $("#showFotoU #fototitle").text($(idname).text()+" "+$(idlast).text());
        let id='#'+$(this).data('id')+'photo';
        let url= $(id).attr('src');
        showfoto.attr("src", url);


    });
});
$( window ).resize(function() {
    updatefakeScroll();
});
function updatefakeScroll() {
    let fakeDiv = $(".fakescroll div");
    let table = $(".table-responsive table");
    let tableWidth = table.width();
    fakeDiv.width(tableWidth);
}

