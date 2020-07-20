const contextPath  = window.location.href;

$(document).ready(function() {
    $(".nav-item i").prop("hidden",false);
    if ($("#msgGestor").text()==="ERROR"){
        $('#editprofile').modal({show: true, backdrop: 'static', keyboard: false });
    }
});