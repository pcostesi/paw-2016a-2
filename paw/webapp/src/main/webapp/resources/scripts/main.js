requirejs.config({
    baseUrl: '/grupo2/scripts',

    paths: {
		'css': 'https://cdnjs.cloudflare.com/ajax/libs/require-css/0.1.8/css.min',
        'jquery': 'https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min',
        'mustache': 'https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min',
        'bootstrap-js': 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min',
        'bootstrap-css': 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min',
       	'bootswatch-sandstone': 'https://cdnjs.cloudflare.com/ajax/libs/bootswatch/3.3.6/sandstone/bootstrap.min',
		'bootlint': 'https://maxcdn.bootstrapcdn.com/bootlint/latest/bootlint.min',
		'swal': 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-sweetalert/0.4.5/sweet-alert.min',
		'bootstrap-toggle': 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-toggle/2.2.1/js/bootstrap-toggle.min',
		'bootstrap-toggle-css': 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-toggle/2.2.1/css/bootstrap-toggle.min',
		'moment': 'https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min',
		'datetimepicker-js': 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min',
		'datetimepicker-css': 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min'
    },
    
    shim: {
    	'bootlint': {
    		deps: ['jquery'],
    		exports: 'bootlint'
    	},
    	'bootstrap-js': ['jquery'],
    	'swal': {
    		deps: ['jquery', 'bs', 'css!swal'],
    		exports: 'swal'
    	},
    	'bootstrap-toggle': ['bs', 'css!bootstrap-toggle-css'],
    	'moment': ['jquery'],
    	'datetimepicker-js': ['jquery', 'moment']
    },
	
	packages: [
		{ name: 'app', location: '' },
		{ name: 'ui', location: '../styles' }
	]
});

// Start the main app logic.
requirejs(['jquery', 'styles', 'bootlint', 'swal', 'think-twice', 'calendar'], function($, _, bootlint, swal, thinkTwice) {
	bootlint.lintCurrentDocument(console.error.bind(console), []);
	thinkTwice();
	window.swal = swal;
});