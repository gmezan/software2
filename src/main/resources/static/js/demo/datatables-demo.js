// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
    "scrollX": true
  });
});

$(document).ready(function() {
  $('#dataTableActivity').DataTable({
    "order": [[ 0, 'desc' ]]
  });
});
