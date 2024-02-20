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
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"1\", \"name\":\"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"2\", \"name\":\"Dragon\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"3\", \"name\":\"WaterSpell\", \"damage\": 20.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"4\", \"name\":\"Ork\", \"damage\": 45.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"5\", \"name\":\"FireSpell\", \"damage\": 25.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"6\", \"name\":\"WaterGoblin\", \"damage\":  9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"31\", \"name\":\"Dragon\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"65\", \"name\":\"WaterSpell\", \"damage\": 21.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"25\", \"name\":\"Ork\", \"damage\": 55.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"45\", \"name\":\"WaterSpell\", \"damage\": 23.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"20\", \"name\":\"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"74\", \"name\":\"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"22\", \"name\":\"WaterSpell\", \"damage\": 22.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"41\", \"name\":\"Ork\", \"damage\": 40.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"17\", \"name\":\"RegularSpell\", \"damage\": 28.0, \"element\": \"NORMAL\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"33\", \"name\":\"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"70\", \"name\":\"Dragon\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"55\", \"name\":\"WaterSpell\", \"damage\": 20.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"37\", \"name\":\"Ork\", \"damage\": 45.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"13\", \"name\":\"WaterSpell\", \"damage\": 25.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"66\", \"name\":\"WaterGoblin\", \"damage\":  9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"90\", \"name\":\"Dragon\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"29\", \"name\":\"WaterSpell\", \"damage\": 21.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"95\", \"name\":\"Ork\", \"damage\": 55.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"88\", \"name\":\"FireSpell\", \"damage\": 23.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\":\"11\", \"name\":\"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"99\", \"name\":\"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"53\", \"name\":\"WaterSpell\", \"damage\": 22.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, {\"cardId\":\"44\", \"name\":\"Ork\", \"damage\": 40.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, {\"cardId\":\"81\", \"name\":\"RegularSpell\", \"damage\": 28.0, \"element\": \"NORMAL\", \"cardType\": \"SPELL\"}]"
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

REM --------------------------------------------------
echo 5) acquire packages altenhof
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
echo should fail (no package):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 6) add new packages
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\": \"670\", \"name\": \"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{\"cardId\": \"990\", \"name\": \"RegularSpell\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{\"cardId\": \"363\", \"name\": \"Knight\", \"damage\": 20.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{\"cardId\": \"202\", \"name\": \"RegularSpell\", \"damage\": 45.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{\"cardId\": \"250\", \"name\": \"FireElf\", \"damage\": 25.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\": \"709\", \"name\": \"WaterGoblin\", \"damage\": 9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{\"cardId\": \"744\", \"name\": \"FireSpell\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{\"cardId\": \"424\", \"name\": \"Knight\", \"damage\": 21.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{\"cardId\": \"844\", \"name\": \"FireSpell\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}, {\"cardId\": \"618\", \"name\": \"FireElf\", \"damage\": 23.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"cardId\": \"227\", \"name\": \"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{\"cardId\": \"387\", \"name\": \"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"},{\"cardId\": \"166\", \"name\": \"Knight\", \"damage\": 22.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{\"cardId\": \"237\", \"name\": \"WaterSpell\", \"damage\": 40.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"},{\"cardId\": \"270\", \"name\": \"FireElf\", \"damage\": 28.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
echo.

REM --------------------------------------------------
echo 7) acquire newly created packages altenhof
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
echo should fail (no money):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo end...

REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on
