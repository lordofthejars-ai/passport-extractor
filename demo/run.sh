#!/bin/bash

read -p "***** Remember to have Quarkus app up and running by using quarkus dev command (./mvnw quarkus:dev) *****"

printf "\n"

read -p "* Image before"

./imgcat ./p2.jpg

printf "\n"

passportdata=`curl -s -X POST localhost:8080/passport -H 'Content-Type: multipart/form-data' -F 'picture=@./p2.jpg'`

read -p "* Detected Image"

printf "\n"

curl -s http://localhost:8080/passport/boundaries > tmp.jpg

./imgcat ./tmp.jpg

read -p "* Detected Name"

printf "\n"

curl -s http://localhost:8080/passport/name > tmp.jpg

./imgcat ./tmp.jpg

read -p "* Detected Surname"

printf "\n"

curl -s http://localhost:8080/passport/surname > tmp.jpg

./imgcat ./tmp.jpg

read -p "* Detected Expire Date"

printf "\n"

curl -s http://localhost:8080/passport/expire > tmp.jpg

./imgcat ./tmp.jpg

read -p "* Detected Passport Number"

printf "\n"

curl -s http://localhost:8080/passport/number > tmp.jpg

./imgcat ./tmp.jpg

read -p "* Processed Data"

printf "\n"

echo $passportdata