var gulp = require('gulp');
var sass = require('gulp-sass');
var autoprefixer = require('gulp-autoprefixer');
var plumber = require('gulp-plumber');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var rename = require('gulp-rename');

gulp.task('convertToCss', function() {
  return gulp.src('./assets/scss/**/*.scss')
      .pipe(plumber())
      .pipe(sass())
      .pipe(autoprefixer())
      .pipe(gulp.dest('./src/main/resources/static/css/'));
});

gulp.task("js", function() {
    return gulp.src('./assets/js/**/*.js')
        .pipe(plumber())
        .pipe(concat('script.js'))
        .pipe(uglify())
        .pipe(rename({
            suffix: '_min'
        }))
        .pipe(gulp.dest('./src/main/resources/static/js/'));
});

gulp.task('watch', function() {
    gulp.watch('./assets/scss/**/*.scss', gulp.series('convertToCss'));
    gulp.watch('./assets/js/**/*.js', gulp.series('js'));
});

gulp.task('default', gulp.series(gulp.parallel('convertToCss', 'js')));
