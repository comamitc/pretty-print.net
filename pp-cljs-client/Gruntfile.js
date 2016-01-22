module.exports = function(grunt) {
'use strict';

//------------------------------------------------------------------------------
// Grunt Config
//------------------------------------------------------------------------------

grunt.initConfig({

  less: {
    options: {
      compress: true
    },

    watch: {
      files: {
        'public/css/main.min.css': 'less/main.less'
      }
    }
  },

  watch: {
    options: {
      atBegin: true
    },

    less: {
      files: "less/*.less",
      tasks: "less:watch"
    }
  },
  injector: {
      options: {
        addRootSlash: false,
        relative: true,
        ignorePath: "/public"
      },
      dev: {
        files: {
          'public/index.html': ['public/js/pretty-print.js']
        }
      },
      build: {
        files: {
          'public/index.html': ['public/js/**/*.min.js']
        }
      }
    }

});

// load tasks from npm
grunt.loadNpmTasks('grunt-contrib-less');
grunt.loadNpmTasks('grunt-contrib-watch');
grunt.loadNpmTasks('grunt-injector');

grunt.registerTask('default', ['watch']);
grunt.registerTask('prod', ['less']);
// end module.exports
};
