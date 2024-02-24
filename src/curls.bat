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
echo.


echo should fail:
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"username\":\"kienboec\", \"password\":\"different\"}"
echo.
echo.



REM --------------------------------------------------
echo 3) create packages (done by "admin")
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 20.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 45.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"FireSpell\", \"damage\": 25.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\":  9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 21.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 55.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 23.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 22.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 40.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"RegularSpell\", \"damage\": 28.0, \"element\": \"NORMAL\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 20.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 45.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 25.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\":  9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, { \"name\":\"WaterSpell\", \"damage\": 21.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 55.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"FireSpell\", \"damage\": 23.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\":\"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"}, { \"name\":\"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}, {\"name\":\"WaterSpell\", \"damage\": 22.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"}, { \"name\":\"Ork\", \"damage\": 40.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"}, { \"name\":\"RegularSpell\", \"damage\": 28.0, \"element\": \"NORMAL\", \"cardType\": \"SPELL\"}]"
echo.
echo.




REM --------------------------------------------------
echo 4) acquire packages kienboec
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"pckgId\": 1}"
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"pckgId\": 2}"
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d  "{\"pckgId\": 3}"
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d  "{\"pckgId\": 4}"
echo.
echo should fail (no money):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"pckgId\": 5}"
echo.
echo.

REM --------------------------------------------------
echo 5) acquire packages altenhof
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"pckgId\": 5}"
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"pckgId\": 6}"
echo.
echo should fail (no package):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{}"
echo.
echo.

REM --------------------------------------------------
echo 6) add new packages
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\": \"WaterGoblin\", \"damage\": 10.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{ \"name\": \"RegularSpell\", \"damage\": 50.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{ \"name\": \"Knight\", \"damage\": 20.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{ \"name\": \"RegularSpell\", \"damage\": 45.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{ \"name\": \"FireElf\", \"damage\": 25.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\": \"WaterGoblin\", \"damage\": 9.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{ \"name\": \"FireSpell\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"},{ \"name\": \"Knight\", \"damage\": 21.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{ \"name\": \"FireSpell\", \"damage\": 55.0, \"element\": \"FIRE\", \"cardType\": \"SPELL\"}, { \"name\": \"FireElf\", \"damage\": 23.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{ \"name\": \"WaterGoblin\", \"damage\": 11.0, \"element\": \"WATER\", \"cardType\": \"MONSTER\"},{\"name\": \"Dragon\", \"damage\": 70.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"},{ \"name\": \"Knight\", \"damage\": 22.0, \"element\": \"NORMAL\", \"cardType\": \"MONSTER\"},{ \"name\": \"WaterSpell\", \"damage\": 40.0, \"element\": \"WATER\", \"cardType\": \"SPELL\"},{ \"name\": \"FireElf\", \"damage\": 28.0, \"element\": \"FIRE\", \"cardType\": \"MONSTER\"}]"
echo.
echo.

REM --------------------------------------------------
echo 7) acquire newly created packages altenhof
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"pckgId\": 7}"
echo.
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"pckgId\": 8}"
echo.
echo should fail (no money):
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"pckgId\": 9}"
echo.
echo.

REM --------------------------------------------------
echo 8) show all acquired cards kienboec
curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo should fail (no token)
curl -i -X GET http://localhost:10001/cards
echo.
echo.

REM --------------------------------------------------
echo 9) show all acquired cards altenhof
curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.


REM --------------------------------------------------
echo 10) show unconfigured deck
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 11) configure deck
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "[{\"cardId\": 1},{\"cardId\": 2},{\"cardId\": 3},{\"cardId\": 4}]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[{\"cardId\": 22},{\"cardId\": 23},{\"cardId\": 24},{\"cardId\": 25}]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

echo should fail and show original from before:
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[{\"cardId\": 100},{\"cardId\": 210},{\"cardId\": 143},{\"cardId\": 144}]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.
echo should fail ... only 3 cards set
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[{\"cardId\": 30},{\"cardId\": 32},{\"cardId\": 33}]"
echo.

REM --------------------------------------------------
echo 12) show configured deck
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 13) show configured deck different representation
echo kienboec
curl -X GET http://localhost:10001/deck?format=plain --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo.
echo altenhof
curl -X GET http://localhost:10001/deck?format=plain --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 14) edit user data
echo.
curl -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer altenhof-mtcgToken"
echo.
curl -X PUT http://localhost:10001/users/kienboec --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"changename\": \"Kienboeck\",  \"bio\": \"me playin...\", \"image\": \":-)\"}"
echo.
curl -X PUT http://localhost:10001/users/altenhof --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"changename\": \"Altenhofer\", \"bio\": \"me codin...\",  \"image\": \":-D\"}"
echo.
curl -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.
echo should fail:
curl -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer altenhof-mtcgToken"
echo.
curl -X PUT http://localhost:10001/users/kienboec --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"changename\": \"Hoax\",  \"bio\": \"me playin...\", \"image\": \":-)\"}"
echo.
curl -X PUT http://localhost:10001/users/altenhof --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"changename\": \"Hoax\", \"bio\": \"me codin...\",  \"image\": \":-D\"}"
echo.
curl -X GET http://localhost:10001/users/someGuy  --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 15) stats
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 16) scoreboard
curl -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 17) battle
start /b "kienboec battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer kienboec-mtcgToken"
start /b "altenhof battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer altenhof-mtcgToken"
ping localhost -n 10 >NUL 2>NUL

REM --------------------------------------------------
echo 18) Stats
echo kienboec
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo altenhof
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 19) scoreboard
curl -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo.
echo.



REM --------------------------------------------------
echo end...

REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on
