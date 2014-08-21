/**
 * Created by sdroscher on 19/08/14.
 */
(function() {

    var app = angular.module('store', ['ui.bootstrap', 'restangular']);

    app.controller('StyleController', [ 'Restangular', function(Restangular) {
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

})();