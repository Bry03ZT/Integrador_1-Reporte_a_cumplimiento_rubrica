@echo off
SET DB_NAME=sistema_laptops
SET DB_USER=postgres
SET DB_HOST=localhost
SET DB_PORT=5432
SET BACKUP_DIR=%~dp0..\backups
SET DATE_STR=%DATE:~10,4%-%DATE:~4,2%-%DATE:~7,2%
SET BACKUP_FILE=%BACKUP_DIR%\backup_%DATE_STR%.sql
IF NOT EXIST "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"
pg_dump -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -F p -f "%BACKUP_FILE%" %DB_NAME%
pause
