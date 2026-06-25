@echo off
SET LOGS_DIR=%~dp0..\logs
forfiles /p "%LOGS_DIR%" /s /m *.log /d -30 /c "cmd /c del @file"
pause
