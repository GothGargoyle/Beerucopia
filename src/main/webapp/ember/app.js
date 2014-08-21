window.Styles = Ember.Application.create();

Styles.ApplicationAdapter = DS.RESTAdapter.extend({
    host: 'http://localhost:8080'
});

Styles.ApplicationSerializer = DS.RESTSerializer.extend({
   normalizePayload: function(payload) {

        var styles = payload._embedded.styles;
        var transformed = [];

        styles.forEach(function(style) {
            delete style.links;
            transformed.push(style);
        })
        var payload = { styles: transformed };
        return payload;
    }
});

Styles.Router.map(function() {
  this.resource('style', { path: '/' });
});

Styles.StyleRoute = Ember.Route.extend({
    model: function() {
        return this.store.find('style');
    }
});

Styles.Style = DS.Model.extend({
    name: DS.attr('string'),
    key: DS.attr('string'),
    ratebeerId: DS.attr('string')
});


