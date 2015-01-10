module.exports = function(grunt) {
  'use strict';

  grunt.initConfig({

    // LESS conversion
    less: {

      watch: {
        options: {
          compress: true,
          paths: ['bower_components']
        },
        files: {
          'app/css/main.css': [
            'bower_components/fontawesome/css/font-awesome.css',
            'public/css/main.less'
          ]
        }
      }
    },
    jsttojs: {
      root: 'public/html',
      output: 'public/js/templates.js',
      ext: 'html',
      removebreak: true,
      amd: true,
      requirements: ['mustache']
    },
    // watching
    watch: {
      dev: {
        options: {
          atBegin: true,
        },
        files: ['public/css/**/*.less', 'public/js/**/*.js',
          'public/html/**/*.html', 'public/docs/**/*.md'
        ],
        tasks: ['copy:font', 'copy:image', 'less:watch', 'injector:dev',
          'jsttojs', 'requirejs:dev'
        ]
      }
    },
    // injector
    injector: {
      options: {
        addRootSlash: false
      },
      dev: {
        files: {
          'index.html': ['app/css/**/*.css']
        }
      }
    },
    requirejs: {
      dev: {
        options: {
          baseUrl: 'bower_components/',
          paths: {
            app: '../public',
            jquery: 'jquery/dist/jquery',
            mustache: 'mustache/mustache',
            text: 'requirejs-text/text',
            director: 'director/build/director',
            lodash: 'lodash/dist/lodash',
            cookie: 'jquery-cookie/jquery.cookie'
          },
          out: 'app/js/optimized.js',
          mainConfigFile: 'public/js/setup.js',
          name: 'app/js/setup',
          optimize: 'none'
        }

      },
      prod: {
        options: {
          baseUrl: 'bower_components/',
          paths: {
            app: '../public',
            jquery: 'jquery/dist/jquery',
            mustache: 'mustache/mustache',
            text: 'requirejs-text/text',
            director: 'director/build/director',
            lodash: 'lodash/dist/lodash',
            cookie: 'jquery-cookie/jquery.cookie'
          },
          out: 'app/js/optimized.js',
          mainConfigFile: 'public/js/setup.js',
          name: 'app/js/setup',
          optimize: 'uglify2',
          preserveLicenseComments: false,
          uglify2: {
            mangle: true,
            compress: {
              dead_code: true,
              comparisons: true,
              conditionals: true,
              booleans: true,
              loops: true,
              unused: true,
              drop_console: true,
              sequences: true
            },
            warnings: true
          }
        }
      }
    },
    copy: {
      font: {
        expand: true,
        cwd: 'bower_components/fontawesome/fonts/',
        src: '*',
        dest: 'app/fonts/'
      },
      image: {
        expand: true,
        cwd: 'public/images/',
        src: '*',
        dest: 'app/images/'
      }
    },
  });

  // load tasks from npm
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-injector');
  grunt.loadNpmTasks('grunt-jsttojs');
  grunt.loadNpmTasks('grunt-requirejs');
  grunt.loadNpmTasks('grunt-contrib-copy');

  grunt.registerTask('default', ['watch:dev']);
  grunt.registerTask('build', ['copy:font', 'copy:image', 'less:watch',
    'injector:dev', 'markdown:all', 'jsttojs', 'requirejs:prod'
  ]);

  // end module.exports
};