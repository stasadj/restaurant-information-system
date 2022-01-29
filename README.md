# Informacioni sistem restorana 🍽 Tim 404 🍔 SIIT 2021/2022

## Predmeti:
- Napredne veb tehnologije 
- Konstrukcija i testiranje softvera

## Članovi tima:
- Albert Makan SW-29/2018
- Anastasija Đurić SW-48/2018
- Dina Petrov SW-52/2018
- Stefan Krnajski SW-69/2018

## Pokretanje:

### Pokretanje aplikacije
Za pokretanje celokupne aplikacije, potrebno je pozicionirati se u root direktorijum, i izvršiti sledece dve komande
```
mvn clean install
mvn --projects backend spring-boot:run
```
### Pokretanje e2e testova
Za e2e testove, potrebno je prvo podici frontend sa sledecim komandama iz root direktorijuma
```
cd frontend
npm i
npm run start
```
