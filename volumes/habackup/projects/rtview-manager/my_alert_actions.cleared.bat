@echo off
rem
rem **************************************************************************
rem my_alert_actions.bat: Alert Command Batch File for Windows
rem Copyright (c) 2009 Sherrill-Lubinski Corporation. All Rights Reserved.
rem **************************************************************************
rem
SETLOCAL

SET DOMAINNAME=%1
shift

SET ALERTNAME=%1
shift
SET ALERTNAME=%ALERTNAME:~1,-1%
IF "%ALERTNAME%"=="" SET ALERTNAME=N/A
IF "%ALERTNAME%"=="++" SET ALERTNAME=N/A

SET ALERTINDEX=%1
shift
SET ALERTINDEX=%ALERTINDEX:~2,-2%
IF "%ALERTINDEX%"=="" SET ALERTINDEX=N/A
IF "%ALERTINDEX%"=="\"++\"" SET ALERTINDEX=N/A

SET ALERTID=%1
shift
SET ALERTID=%ALERTID:~1,-1%
IF "%ALERTID%"=="" SET ALERTID=N/A
IF "%ALERTID%"=="++" SET ALERTID=N/A

SET ALERTSEVERITY=%1
shift
SET ALERTSEVERITY=%ALERTSEVERITY:~1,-1%
IF "%ALERTSEVERITY%"=="" SET ALERTSEVERITY=N/A
IF "%ALERTSEVERITY%"=="++" SET ALERTSEVERITY=N/A

SET ALERTTEXT=%1
SET ALERTTEXT=%ALERTTEXT:~1%
shift


:LOOP
SET ALERTTEXT=%ALERTTEXT% %1
shift
IF NOT (%1)==() GOTO LOOP

rem echo ----- Alert command script executed: DOMAINNAME=%DOMAINNAME%, ALERTNAME=%ALERTNAME%, ALERTINDEX=%ALERTINDEX%, ALERTID=%ALERTID%, ALERTSEVERITY=%ALERTSEVERITY%, ALERTTEXT=%ALERTTEXT% #####

REM Add custom processing for email, file, etc.

ENDLOCAL
