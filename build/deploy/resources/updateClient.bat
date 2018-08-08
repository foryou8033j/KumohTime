@echo off
echo 이 창을 절대절대 종료하지 마세요!!!
echo .
echo KumohTime 업데이트 중
powershell -Command "(New-Object Net.WebClient).DownloadFile('%1%2/kumohtime.jar', 'KumohTime.jar')"
powershell -Command "Invoke-WebRequest %1%2/kumohtime.jar -OutFile KumohTime.jar"
echo 클라이언트 업데이트 완료
echo .

start /B ..\KumohTime.exe
exit