/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function factory_interceptor(jcWeb) {
    jcWeb.factory("httpInterceptor", ["$tokenHolder", function(tokenHolder) {
            return {
                "request": function(request) {
                    alert("request fired");
                    request.headers.jwtoken = JSON.stringify(tokenHolder.getToken());
                    return request;
                },
                "response": function(response) {
                    return response;
                }
            };
        }]);
}