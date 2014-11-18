/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function showErrorDialog(messageText) {
    var dialogTitle=$("#modalDialogTitle");
    var okButton=$("#modalDialogOkButton");
    dialogTitle.html("Error");
    $("#modalDialogContent").html(messageText);
    okButton.addClass("btn btn-danger");
    dialogTitle.css("color", okButton.css("background-color"));
    $("#modalDialog").modal();
}

function showErrorStackTraceDialog(messageText, stackTrace) {
    var dialogTitle=$("#modalDialogTitle");
    var okButton=$("#modalDialogOkButton");
    dialogTitle.html("Error");
    var content=$("#modalDialogContent");
    content.html("Message: "+messageText+"<br/><br/> Stack trace:<br/>"+stackTrace);
    content.height(150);
    okButton.addClass("btn btn-danger");
    dialogTitle.css("color", okButton.css("background-color"));
    $("#modalDialog").modal();
}

function showWarningDialog(messageText) {
    var dialogTitle=$("#modalDialogTitle");
    var okButton=$("#modalDialogOkButton");
    dialogTitle.html("Warning");
    $("#modalDialogContent").html(messageText);
    okButton.addClass("btn btn-warning");
    dialogTitle.css("color", okButton.css("background-color"));
    $("#modalDialog").modal();
}

function showSuccessDialog(messageText) {
    var dialogTitle=$("#modalDialogTitle");
    var okButton=$("#modalDialogOkButton");
    dialogTitle.html("Success");
    $("#modalDialogContent").html(messageText);
    okButton.addClass("btn btn-success");
    dialogTitle.css("color", okButton.css("background-color"));
    $("#modalDialog").modal();
}

