@echo off
setlocal enabledelayedexpansion

REM Read the JSON content
set "json="
for /f "usebackq delims=" %%a in ("C:\Users\Agbanelo Kellix\Desktop\Lunodumb\serviceAccountKey.json") do (
    set "line=%%a"
    set "json=!json!!line!"
)

REM Set the Heroku config
heroku config:set FIREBASE_SERVICE_ACCOUNT_KEY=!json!