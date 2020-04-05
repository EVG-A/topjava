var context, form, filterUrl, filterForm;

function makeEditable(ctx) {
    context = ctx;
    form = $('#detailsForm');
    filterForm = $('#filter');
    filterUrl = "";

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    if (confirm('Are you sure?')) {
        $.ajax({
            url: context.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            updateTable();
            successNoty("Deleted");
        });
    }
}

function filterTable() {
    var startDate = "startDate=" + filterForm.find("input[name=startDate]").val();
    var startTime = "startTime=" + filterForm.find("input[name=startTime]").val();
    var endDate = "endDate=" + filterForm.find("input[name=endDate]").val();
    var endTime = "endTime=" + filterForm.find("input[name=endTime]").val();
    filterUrl = "filter?" + startDate + "&" + startTime + "&" + endDate + "&" + endTime;
    updateTable();
}

function clearFilter() {
    filterUrl = "";
    filterForm.find(":input").val("");
    updateTable();
}

function updateTable() {
    $.get(context.ajaxUrl + filterUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}