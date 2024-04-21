# Ohjeet käyttöön ja toimintaan

## Ohjelmassa käytetään kurssilla saatua backendiä ja tietokantaa. Ohjelma sisältää kaikki tunnilla läpikäydyt perusasiat. 

## Ohjelman sisältämät extra ominaisuudet:

1. Ohjelma sisältää sisään ja uloskirjautumisen

- Uloskirjautumisen vikaa ei saatu korjattua.
- Uloskirjautumiselle on tehty oma ViewModel ja Screen. Screen sisältää tekstin, joka vaihtuu Kirjaa minut ulos-painiketta painamalla. Saman painikkeen tulisi hoitaa uloskirjautuminen.
- Kun uloskirjautuminen on suoritettu, niin Kirjaudu sisään-painike enabloituu ja siitä pääsee takaisin Login-screeniin. LoginScreenistä ei pääse enää takaisin aikaisempaan LogOutScreeniin.
- Room-tietokannan luominen onnistuu ja tokeni toimii normaalisti.
- Eri toiminnallisuuksien suorituksen yhteydessä tarkistetaan ettei token ole null.

2. Ohjelma sisältää myös rekisteröitymisen

- Rekisteröinnillä on oma ViewModel ja screen.
- Rekisteröinnin yhteydessä luodaan uusi käyttäjätunnus tietokantaan.

3. Ohjelmassa käytetään lokalisointia 
