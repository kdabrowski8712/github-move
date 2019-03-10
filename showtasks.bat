call runcmd.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo Compilation errors
goto fail

:openbrowser
start chrome http://localhost:8080/crud/v1/tasks/getTasks

:fail
echo.
echo There were errors