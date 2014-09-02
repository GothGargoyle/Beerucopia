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

    breweriesApp.controller('BreweriesController', [ 'Restangular', '$scope', function(Restangular, $scope) {
        var store = this;

        store.totalServerItems = 0;
        store.pagingOptions = {
            pageSizes: [20],
            pageSize: 20,
            currentPage: 1
        };

        store.selectPage = function() {
            var page = store.pagingOptions.currentPage - 1;
            var breweries = Restangular.one('breweries');
            breweries.get({page: page}).then(function(data) {
                store.breweries = data._embedded.breweries;
                angular.forEach(store.breweries, function(brewery) {
                    delete brewery._links;
                });
                var pageData = data.page;
                store.pagingOptions.currentPage = pageData.number + 1;
                store.pagingOptions.pageSize = pageData.size;
                store.totalServerItems = pageData.totalElements;
            });
        };

        store.selectPage(store.pagingOptions.currentPage);

        store.gridOptions = {
            data: 'store.breweries',
            enablePaging: true,
            showFooter: true,
            totalServerItems: 'store.totalServerItems',
            pagingOptions: store.pagingOptions
        };

        $scope.$watch('store.pagingOptions', function(newVal, oldVal) {
            if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                store.selectPage(store.pagingOptions.currentPage);
            }
        }, true);

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