$(function () {

    let $checkbox = $("#confirmado1");
    console.log($checkbox.is(":checked"));
    $("#id\\.numerodocumento").prop("disabled",!$checkbox.is(":checked"));
    $(".inputFile").prop("hidden",!$checkbox.is(":checked"));

    $("body").on('change','#confirmado1', function () {
        $("#id\\.numerodocumento").prop("disabled",!this.checked);
        $(".inputFile").prop("hidden",!this.checked);
    })
});