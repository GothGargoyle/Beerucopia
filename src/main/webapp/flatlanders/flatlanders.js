/**
 * Created by sdroscher on 19/08/14.
 */
(function() {
    var app = angular.module('store', []);

    app.controller('StoreController', [ '$http', function($http) {
        var store = this;
        store.products = [ ];
        $http.get('/styles').success(function(data) {
            store.products = data._embedded.styles;
        });
    }]);

    var gems = [
        {
            name: "Dodecahedron",
            price: 2,
            description: ". . .",
            canPurchase: true,
            images: [
                {
                    full: 'dodecahedron-01-full.jpg',
                    thumb: 'dodecahedron-02-thumb.jpg'
                }
            ],
            reviews: [
                {
                    stars: 5,
                    body: "I love this product!",
                    author: "joe@thomas.com"
                },
                {
                    stars: 1,
                    body: "This product sucks",
                    author: "tim@hater.com"
                }
            ]
        },
        {
            name: "Pentagonal Gem",
            price: 5.95,
            description: ". . .",
            canPurchase: false
        }
    ];

    app.controller("ReviewController", function() {
        this.review = {};

        this.addReview = function(product) {
            product.reviews.push(this.review);
            this.review = {};
        }
    });

    app.directive('productTitle', function() {
        return {
            restrict: 'E',
            templateUrl: 'product-title.html'
        };
    });

    app.directive('productPanels', function() {
       return {
           restrict: 'E',
           templateUrl: 'product-panels.html',
           controller: function() {
               this.tab = 1;
               this.selectTab = function(setTab) {
                   this.tab = setTab;
               }
               this.isSelected = function(checkTab) {
                   return this.tab === checkTab;
               }
           },
           controllerAs: 'panel'
       }
    });

})();

