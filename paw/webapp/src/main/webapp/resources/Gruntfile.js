module.exports = function(grunt) {

  grunt.initConfig({
    eslint: {
      target: ['Gruntfile.js', 'src/scripts/**/*.js', 'src/test/**/*.js']
    },

    typescript: {
      base: {
        src: ['src/scripts/**/*.ts'],
        dest: 'dist/scripts/',
        options: {
          // module: 'amd', //or commonjs 
          target: 'es5', //or es3 
          sourceMap: true,
          declaration: true
        }
      }
    },

    tslint: {
      files: ['src/scripts/**/*.ts']
    }
  });

  grunt.loadNpmTasks('grunt-eslint');
  grunt.loadNpmTasks('grunt-typescript');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-tslint');

  grunt.registerTask('check', ['eslint', 'tslint']);
  grunt.registerTask('build', ['typescript']);
  grunt.registerTask('default', ['check', 'build']);

};
