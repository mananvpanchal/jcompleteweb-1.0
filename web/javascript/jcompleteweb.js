/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function showErrorDialog(messageText) {
    $("#modalDialogTitle").html("Error");
    $("#modalDialogContent").html(messageText);
    $("#modalDialogOkButton").addClass("btn btn-danger");
    $("#modalDialogTitle").css("color", $("#modalDialogOkButton").css("background-color"));
    $("#modalDialog").modal();
}

function showErrorStackTraceDialog(messageText, stackTrace) {
    $("#modalDialogTitle").html("Error");
    var content=$("#modalDialogContent");
    content.html("Message: "+messageText+"<br/><br/> Stack trace:<br/>"+stackTrace);
    content.height(150);
    $("#modalDialogOkButton").addClass("btn btn-danger");
    $("#modalDialogTitle").css("color", $("#modalDialogOkButton").css("background-color"));
    $("#modalDialog").modal();
}

function showWarningDialog(messageText) {
    $("#modalDialogTitle").html("Warning");
    $("#modalDialogTitle").css("color", $(".btn-warning").css("color"));
    $("#modalDialogContent").html(messageText);
    $("#modalDialogOkButton").addClass("btn btn-warning");
    $("#modalDialogTitle").css("color", $("#modalDialogOkButton").css("background-color"));
    $("#modalDialog").modal();
}

function showSuccessDialog(messageText) {
    $("#modalDialogTitle").html("Success");
    $("#modalDialogTitle").css("color", $(".btn-warning").css("color"));
    $("#modalDialogContent").html(messageText);
    $("#modalDialogOkButton").addClass("btn btn-success");
    $("#modalDialogTitle").css("color", $("#modalDialogOkButton").css("background-color"));
    $("#modalDialog").modal();
}

