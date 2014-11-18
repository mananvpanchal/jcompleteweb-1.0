/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function configureApp(jcWeb) {
    jcWeb.config(["$routeProvider", "$httpProvider", function(routeProvider, httpProvider) {
            httpProvider.interceptors.push("httpInterceptor");
            configureRoute(routeProvider);
        }]);
}
