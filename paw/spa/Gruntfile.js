module.exports = function(grunt) {
  'use strict';

  require('load-grunt-tasks')(grunt); // npm install --save-dev load-grunt-tasks

  grunt.initConfig({

    watch: {
      browserify: {
        files: ['src/scripts/**/*.js'],
        tasks: ['browserify']
      }
    },

    clean: {
      build: ['build/'],
      dist: ['dist/']
    },

    autoprefixer: {
      options: {
          browsers: ['last 5 versions', 'ie 8', 'ie 9']
      },
      build: {
        files: [{
            'expand': true,
            'cwd': 'src/styles/',
            'src': ['**/*.css'],
            'dest': 'build/styles',
            'ext': '.css'
        }]
      },
    },

    cssmin: {
      options: {
        report: 'gzip'
      },
      target: {
        files: [{
          expand: true,
          cwd: 'build/styles',
          src: ['**/*.css', '!**/*.min.css'],
          dest: 'dist/styles',
          ext: '.css'
        }]
      }
    },

    eslint: {
      target: ['Gruntfile.js', 'src/scripts/**/*.js', 'src/test/**/*.js']
    },

    typescript: {
      base: {
        src: ['src/scripts/**/*.ts'],
        dest: 'build/scripts/',
        options: {
          // module: 'amd', //or commonjs 
          target: 'es5', //or es3 
          sourceMap: true,
          declaration: true
        }
      }
    },

    babel: {
        options: {
            sourceMap: true,
            presets: ['es2015']
        },
        dist: {
            files: [{
                'expand': true,
                'cwd': 'src/scripts/',
                'src': ['**/*.js'],
                'dest': 'build/scripts',
                'ext': '.js'
            }]
        }
    },

    uglify: {
      dist: {
        files: [{
            expand: true,
            cwd: 'build/scripts',
            src: '**/*.js',
            dest: 'dist/scripts'
        }],
      }
    },

    tslint: {
      files: ['src/scripts/**/*.ts']
    }
  });

  grunt.registerTask('check', ['eslint', 'tslint']);
  grunt.registerTask('build', ['typescript', 'babel', 'autoprefixer']);
  grunt.registerTask('dist', ['clean', 'build', 'uglify', 'cssmin']);
  grunt.registerTask('default', ['check', 'dist']);

};
