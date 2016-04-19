define(['bootstrap-js'], function (bs) {
    return {
        load: function (name, req, onload, config) {
            var component = 'bs-' + name;
            var shim = {};
            shim[component] = {
                deps: ['jquery'],
                exports: '$.fn.' + name
            }
            require.config({ shim: shim });

            req(['jquery', component], function ($, value) {
                onload($);
            });
        }
    };
});