@echo off

REM --------------------------------------------------
REM Monster Trading Cards Game
REM --------------------------------------------------
title Monster Trading Cards Game
echo CURL Testing for Monster Trading Cards Game
echo.

REM --------------------------------------------------
echo 1) Create Users (Registration)
REM Create User
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"username\":\"kienboec\",\"password\":\"daniel\", \"coins\":20, \"elo\":80}"
echo.
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"username\":\"altenhof\", \"password\":\"markus\", \"coins\":20, \"elo\":100}"
echo.
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"username\":\"admin\", \"password\":\"istrator\", \"coins\":20, \"elo\":80}"
echo.



echo should fail:
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"username\":\"kienboec\", \"password\":\"daniel\", \"coins\":\"20\", \"elo\":\"100\"}"
echo.
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"username\":\"kienboec\", \"password\":\"different\", \"coins\":\"20\", \"elo\":\"80\"}"
echo.
echo.


REM --------------------------------------------------
echo 2) Login Users
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"username\":\"kienboec\", \"password\":\"daniel\"}"
echo.
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"username\":\"altenhof\", \"password\":\"markus\"}"
echo.
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"username\":\"admin\",    \"password\":\"istrator\"}"
echo.



echo should fail:
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"username\":\"kienboec\", \"password\":\"different\"}"
echo.
echo.



REM --------------------------------------------------
echo 3) create packages (done by "admin")
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"1\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"cardId\":\"2\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"cardId\":\"3\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"cardId\":\"4\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"cardId\":\"5\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"6\", \"Name\":\"WaterGoblin\", \"Damage\":  9.0}, {\"cardId\":\"31\", \"Name\":\"Dragon\", \"Damage\": 55.0}, {\"cardId\":\"65\", \"Name\":\"WaterSpell\", \"Damage\": 21.0}, {\"cardId\":\"25\", \"Name\":\"Ork\", \"Damage\": 55.0}, {\"cardId\":\"45\", \"Name\":\"WaterSpell\", \"Damage\": 23.0}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"20\", \"Name\":\"WaterGoblin\", \"Damage\": 11.0}, {\"cardId\":\"74\", \"Name\":\"Dragon\", \"Damage\": 70.0}, {\"cardId\":\"22\", \"Name\":\"WaterSpell\", \"Damage\": 22.0}, {\"cardId\":\"41\", \"Name\":\"Ork\", \"Damage\": 40.0}, {\"cardId\":\"17\", \"Name\":\"RegularSpell\", \"Damage\": 28.0}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"33\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"cardId\":\"70\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"cardId\":\"55\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"cardId\":\"37\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"cardId\":\"13\", \"Name\":\"WaterSpell\",   \"Damage\": 25.0}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"66\", \"Name\":\"WaterGoblin\", \"Damage\":  9.0}, {\"cardId\":\"90\", \"Name\":\"Dragon\", \"Damage\": 55.0}, {\"cardId\":\"29\", \"Name\":\"WaterSpell\", \"Damage\": 21.0}, {\"cardId\":\"95\", \"Name\":\"Ork\", \"Damage\": 55.0}, {\"cardId\":\"88\", \"Name\":\"FireSpell\",    \"Damage\": 23.0}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"11\", \"Name\":\"WaterGoblin\", \"Damage\": 11.0}, {\"cardId\":\"99\", \"Name\":\"Dragon\", \"Damage\": 70.0}, {\"cardId\":\"53\", \"Name\":\"WaterSpell\", \"Damage\": 22.0}, {\"cardId\":\"44\", \"Name\":\"Ork\", \"Damage\": 40.0}, {\"cardId\":\"81\", \"Name\":\"RegularSpell\", \"Damage\": 28.0}]"
echo.
echo.



REM --------------------------------------------------
echo 4) acquire packages kienboec
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo.
echo should fail (no money):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo.
echo.

pause

REM --------------------------------------------------
echo end...

REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on
