var gulp = require('gulp');
gulp.task('publish', function() {
  gulp.src('web/images/**')
    .pipe(gulp.dest('build/images/'));
  gulp.src('web/fonts/**')
    .pipe(gulp.dest('build/fonts/'));
});
