@echo off
rem ***********************************************************
rem script to compile Java classes and add to rtvapm_custom.jar

SETLOCAL

:COMPILE
echo.
echo ... Compiling classes
echo.

set CP=.;%RTVAPM_HOME%/common/lib/gmsjrtvutils.jar;"%RTV_HOME%"/lib/gmsjrtvhistorian.jar;"%RTV_HOME%"/lib/gmsjmodels.jar;"%RTV_HOME%"/lib/gmsjjmxds.jar;"%RTV_HOME%"/lib/gmsjjmsadmds.jar;"%RTV_HOME%"/lib/gmsjcacheds.jar;./myclasses.jar;"%RTV_USERPATH%";%RTVAPM_HOME%/solmon/lib/rtvapm_solmon.jar

call javac -classpath %CP% com\sl\rtvapm\custom\*.java
if errorlevel 1 goto ERROR_JAVA

set JARFILE=..\lib\rtvapm_custom.jar
set JAROPTS=cf
if exist %JARFILE% set JAROPTS=uf

echo.
echo ... Generating %JARFILE%
echo.

call jar %JAROPTS% %JARFILE% com\sl\rtvapm\custom\*.class
if errorlevel 1 goto ERROR_JAR
goto OK

:ERROR_JAR

echo.  #
echo.  #  Error generating jar file
echo.  #
goto END

:ERROR_JAVA

echo.  #
echo.  #  Error compiling java sources
echo.  #
goto END

:OK

echo make_classes Ok

:END

ENDLOCAL
