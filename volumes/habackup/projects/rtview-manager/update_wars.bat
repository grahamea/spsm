@echo off

rem Edit these values or pass them in on the command line.

set APPNAME=rtview-manager
set HOST=localhost
set PORTPREFIX=30

rem Edit these values for High Availability. 
rem Set HA_HOST to the hostname of the backup data server.  

set HA_HOST=


if "%1" == "?" goto USAGE
if "%1" == "help" goto USAGE

set SECURE=
if "%1" == "-secure" (
	set SECURE=%1
	shift
)

if not "%1" == "" set APPNAME=%1
if not "%2" == "" set HOST=%2
if not "%3" == "" set PORTPREFIX=%3

call make_rtvagent_war.bat -appname:%APPNAME% -host:%HOST% -port:%PORTPREFIX%72 -package:rtvmgr -ha_host:%HA_HOST%
call make_rtvdata_war.bat -appname:%APPNAME% -host:%HOST% -port:%PORTPREFIX%78 -package:rtvmgr -ha_host:%HA_HOST%
call make_rtvquery_war.bat -appname:%APPNAME% -host:%HOST% -port:%PORTPREFIX%78 -package:rtvmgr -ha_host:%HA_HOST% %SECURE%
call make_rtvadmin_war.bat -appname:%APPNAME% -host:%HOST% -port:%PORTPREFIX%78 -package:rtvmgr -ha_host:%HA_HOST%
call make_rtvpost_war.bat -appname:%APPNAME% -host:%HOST% -port:%PORTPREFIX%75 -package:rtvmgr
call make_ui_war.bat -appname:%APPNAME% -package:rtvmgr
goto :DONE

:USAGE
echo Usage: update_wars.bat [appname [host [portprefix]]]
echo Defaults: %APPNAME% %HOST% %PORTPREFIX%

:DONE
set APPNAME=
set HOST=
set PORTPREFIX=
set HA_HOST=
