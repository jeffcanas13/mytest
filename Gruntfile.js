'use strict';
var fs = require('fs');

var parseString = require('xml2js').parseString;

var parseVersionFromPomXml = function () {
    var version = 0;
    var pomXml = fs.readFileSync('pom.xml', 'utf8');
    parseString(pomXml, function (err, result) {
        if (result.project.properties && result.project.properties[0].version && result.project.properties[0].version[0]) {
            version = result.project.properties[0].version[0];
        } else if (result.project.version && result.project.version[0]) {
            version = result.project.version[0];
        } else {
            throw new Error("pom.xml is malformed. No version is defined");
        }
    });

    return version;
}


module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        vars: {
            templateFolder: "src/main/resources/templates",
            htmlTemplate: "index.tpl.html",
            webapp: "src/main/webapp",
            indexHtml: "index.html",
            dist: "src/main/webapp/dist",
            injectorVar: ['assets/styles/**/*.css', 'scripts/app/atms.js', 'scripts/**/**.js'],
            fontFolder: 'assets/fonts',
            sassFiles: 'assets/sass/**.scss'
        },
        update_version: {
            build: {
                src: '<%= pkg %>',
                dest: 'package.json'
            }
        },
        clean: {
            tmp: {
                src: ['.tmp']
            },
            dist: {
                src: ['<%= vars.dist %>', '.tmp']
            }
        },
        sass: {
            main: {
                files: {
                    '<%= vars.webapp %>/assets/styles/app.css': '<%= vars.webapp %>/assets/sass/app.scss'
                }
            }
        },
        autoprefixer: {
            options: {
                browsers: ['last 20 versions', 'ie 8', 'ie 9', '> 1%']
            },
            build: {
                expand: true,
                cwd: '<%= vars.webapp %>',
                src: ['assets/**.css', 'assets/**/*.css'],
                dest: '<%= vars.webapp %>'
            }
        },
        injector: {
            options: {
                template: '<%= vars.templateFolder %>/<%= vars.htmlTemplate %>',
                relative: false,
                addRootSlash: false,
                ignorePath: '<%= vars.webapp %>',
                destFile: '<%= vars.templateFolder %>/<%= vars.indexHtml %>',
                lineEnding: grunt.util.linefeed
            },
            build: {
                files: [{
                    expand: true,
                    cwd: '<%= vars.webapp %>',
                    src: '<%= vars.injectorVar %>'
                }]
            }
        },
        wiredep: {
            options: {
                ignorePath: '../../webapp/'
            },
            build: {
                src: ['<%= vars.templateFolder %>/<%= vars.indexHtml %>']
            }
        },
        useminPrepare: {
            html: '<%= vars.templateFolder %>/<%= vars.indexHtml %>',
            options: {
                root: '<%= vars.webapp %>',
                dest: '<%= vars.webapp %>'
            }
        },
        concat: {},
        cssmin: {},
        uglify: {
            options: {
                preserveComments: 'some'
            }
        },
        usemin: {
            html: '<%= vars.templateFolder %>/<%= vars.indexHtml %>'
        },
        copy: {
            main: {
                expand: true,
                cwd: '<%= vars.webapp %>',
                src: '<%= vars.fontFolder %>/**',
                dest: '<%= vars.dist %>/'
            }
        },
        watch: {
            bower: {
                files: ['bower.json'],
                tasks: ['wiredep']
            },
            styles: {
                files: ['<%= vars.webapp %>/<%= vars.injectorVar[0] %>'],
                tasks: ['autoprefixer', 'injector', 'wiredep']
            },
            scripts: {
                files: ['<%= vars.webapp %>/<%= vars.injectorVar[2] %>'],
                tasks: ['injector', 'wiredep']
            },
            sass: {
                files: ['<%= vars.webapp %>/<%= vars.sassFiles %>'],
                tasks: ['sass']
            }
        }
    });

    grunt.loadNpmTasks('grunt-autoprefixer');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-usemin');
    grunt.loadNpmTasks('grunt-injector');
    grunt.loadNpmTasks('grunt-wiredep');

    grunt.registerTask('update_version', 'Update package.json version', function () {

        grunt.log.writeln("Updating package.json version");

        var packageJson = grunt.config('update_version').build.src;
        packageJson.version = parseVersionFromPomXml();

        grunt.file.write(grunt.config('update_version').build.dest, JSON.stringify(packageJson, null, 2));

        grunt.log.writeln("Package.json was successfully updated");
    });

    grunt.registerTask('build', [
        'useminPrepare',
        'concat',
        'cssmin',
        'uglify',
        'usemin'
    ]);

    grunt.registerTask('dev', [
        'update_version',
        'clean:dist',
        'sass',
        'autoprefixer',
        'injector',
        'wiredep',
        'watch'
    ]);

    grunt.registerTask('prod', [
        'update_version',
        'clean:dist',
        'sass',
        'autoprefixer',
        'injector',
        'wiredep',
        'build',
        'copy',
        'clean:tmp'
    ]);

    grunt.registerTask('default', ['dev']);
}