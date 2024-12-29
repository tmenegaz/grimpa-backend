@echo off
set description=%1%
 if "%1%" == "" (
 set /p description=Nome do arquivo:
 )
 for /f "tokens=1-9 delims=/" %%a in ("%date%") do set ymd=%%c%%b%%a
 for /f "tokens=1-9 delims=:," %%a in ("%time%") do set hmss=%%a%%b%%c%%d0

 set hmss=%hmss: =0%
 set ymd=%ymd: =0%

 set filename=V%ymd%%hmss%__%description%.sql

 rem echo DIR:%~dp0
 set path=%~dp0

 echo TODO > "%path%\%filename%"
 echo Novo arquivo: %filename%
 echo Gerado em %path%

 pause
 exit