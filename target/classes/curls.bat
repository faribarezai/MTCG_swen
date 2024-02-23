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
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\"]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\"]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

echo should fail and show original from before:
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\"]"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.
echo should fail ... only 3 cards set
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\"]"
echo.

REM --------------------------------------------------
echo 12) show configured deck
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo.
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo end...

REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on
