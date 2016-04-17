requirejs.config({
    baseUrl: '/scripts',

    paths: {
		'css': 'https://cdnjs.cloudflare.com/ajax/libs/require-css/0.1.8/css.min',
        'jquery': 'https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min',
        'mustache': 'https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min',
        'bootstrap-js': 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min',
        'bootstrap-css': 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min',
       	'bootswatch-sandstone': 'https://cdnjs.cloudflare.com/ajax/libs/bootswatch/3.3.6/sandstone/bootstrap.min',
		'bootlint': 'https://maxcdn.bootstrapcdn.com/bootlint/latest/bootlint.min',
    },
    
    shim: {
    	'bootlint': {
    		deps: ['jquery'],
    		exports: 'bootlint'
    	},
    	'bootstrap-js': ['jquery'],
    	'html-inspector': { exports: 'HTMLInspector' }
    }
});

// Start the main app logic.
requirejs(['jquery', 'styles', 'bootlint'], function($, _, bootlint) {
	bootlint.lintCurrentDocument(console.error.bind(console), []);
});
