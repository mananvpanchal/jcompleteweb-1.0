/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function controller(jcWeb) {
    
    jcWeb.controller("LoginCtrl", ["$scope", "$http", "$location", "$tokenHolder", "$pathHolder", "$messageDialogCallbackHolder", function(scope, http, location, tokenHolder, pathHolder, messageDialogCallbackHolder) {
            scope.doLogin = function(username, password) {
                var cred = {
                    "username": username,
                    "password": password
                };
                http.post("webresources/open/dologin", cred).
                        success(function(data) {
                            tokenHolder.setToken(data);
                            messageDialogCallbackHolder.setCallback(function() {
                                if (pathHolder.getPath() === null) {
                                    location.path("/home");
                                } else {
                                    location.path(pathHolder.getPath());
                                }
                            });
                            showSuccessDialog("success login");
                        }).
                        error(function(data, status) {
                            showErrorStackTraceDialog(data.message, data.stackTrace);
                        });
            };
        }]);
    
    jcWeb.controller("HomeCtrl", ["$scope", "$http", "$location", "$tokenHolder", "$pathHolder", function(scope, http, location, tokenHolder, pathHolder) {
            scope.doWork = function() {
                http.post("webresources/restricted/home", {criteria: "manan"}).
                        success(function(data) {
                            if (data.data === "AUTHENTICATION_FAILED") {
                                pathHolder.setPath(location.path());
                                location.path("/");
                            } else {
                                showSuccessDialog(data.data);
                            }
                        }).
                        error(function(data, status) {
                            showErrorStackTraceDialog(data.message, data.stackTrace);
                        });
            };
        }]);
    
    jcWeb.controller("ModalDialogCtrl", ["$scope", "$messageDialogCallbackHolder", function(scope, messageDialogCallbackHolder) {
            scope.okClicked = function() {
                messageDialogCallbackHolder["callback"]();
            };
        }]);
}