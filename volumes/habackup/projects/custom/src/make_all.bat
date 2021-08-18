@echo off

if exist ..\lib\*.jar erase ..\lib\*.jar

call make_classes.bat
if errorlevel 1 goto ERROR_1

goto END

:ERROR_1
echo Built stopped at make_clases.bat due to error


:END
