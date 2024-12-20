@echo off
echo Running Cucumber tests...

rem Uruchomienie testów z wykorzystaniem Mavena
mvn clean test -Dtest=org.example.RunnerTest

echo Cucumber tests completed.