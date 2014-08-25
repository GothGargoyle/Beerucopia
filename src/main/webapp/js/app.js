/**
 * Created by sdroscher on 19/08/14.
 */
(function() {

    var stylesApp = angular.module('styles', ['ui.bootstrap', 'restangular']);

    stylesApp.controller('StyleController', [ 'Restangular', function(Restangular) {
        var store = this;
        store.styles = [ ];
        store.next = '';
        store.page = {};
        store.selectPage = function() {
            var page = store.page.number - 1;
            var styles = Restangular.one('styles');
            styles.get({page: page}).then(function(data) {
                store.styles = data._embedded.styles;
                store.page = data.page;
                store.page.number += 1;
            });
        };
        store.selectPage(1);
    }]);

    var breweriesApp = angular.module('breweries', ['ngGrid', 'restangular']);

    breweriesApp.controller('BreweriesController', [ 'Restangular', function(Restangular) {
        var store = this;

        var breweries = Restangular.one('breweries');
        breweries.get().then(function(data) {
            store.breweries = data._embedded.breweries;
            angular.forEach(store.breweries, function(brewery) {
                delete brewery._links;
            });
        });
        store.gridOptions = { data: 'store.breweries' };


    }]);



    //breweriesApp.config(function(RestangularProvider) {
    //   RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
    //       var extractedData;
    //       if (operation === "getList") {
    //           extractedData = data;
    //           delete extractedData._links;
    //       } else {
    //           extractedData = data;
    //       }
    //       return extractedData;
    //   });
    //});



})();