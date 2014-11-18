/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function configureRoute(routeProvider) {
    routeProvider.
            when("/", {
                templateUrl: "templates/open/login.html",
                controller: "LoginCtrl"
            }).
            when("/home", {
                templateUrl: "templates/restricted/home.html",
                controller: "HomeCtrl"
            }).
            when("/home2", {
                templateUrl: "templates/restricted/home2.html",
                controller: "HomeCtrl"
            }).
            otherwise({
                redirectTo: "/"
            });
}
