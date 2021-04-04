@echo off
setlocal
cd /d %~dp0

for %%a in (*.zip) do (
	powershell Expand-Archive "%CD%\%%~nxa" "%CD%\%%~na"
)
pause
exit /b