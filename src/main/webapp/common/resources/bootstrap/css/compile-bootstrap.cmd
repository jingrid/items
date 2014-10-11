@echo off

set CMD_FILE="D:\dev\codebase\com\github\less.js-windows\lessc.cmd"
set LESS_FILE="D:\dev\codebase\com\github\bootstrap\less\realpaas-bootstrap.less"
set RESP_LESS_FILE="D:\dev\codebase\com\github\bootstrap\less\realpaas-responsive.less"
set CSS_FILE="bootstrap.css"
set CSS_MIN_FILE="bootstrap.min.css"
set RESP_CSS_FILE="bootstrap-responsive.css"
set RESP_CSS_MIN_FILE="bootstrap-responsive.min.css"

call %CMD_FILE% %LESS_FILE% > %CSS_FILE%
call %CMD_FILE% %LESS_FILE% > %CSS_MIN_FILE% -compress
call %CMD_FILE% %RESP_LESS_FILE% > %RESP_CSS_FILE%
call %CMD_FILE% %RESP_LESS_FILE% > %RESP_CSS_MIN_FILE% -compress
