# Generator fiszek i nie tylko

Celem projektu jest stworzenie aplikacji wspierającej naukę języków obcych
w zakresie m.in. słownictwa, morfologii i syntaksy.

Praca została podzielona na 3 kamienie milowe. W każdym z nich zespół ma
za zadanie dostarczyć aplikację spełniającą przedstawione wymagania.

## Kamień milowy 1
Podstawowa aplikacja, która przyjmie dłuższy tekst i na jego podstawie
wygeneruje zestaw fiszek w standardowym formacie, który można zaimportować
do różnych aplikacji.
### Wymagania funkcjonalne
* Eksport fiszek do formatu zgodnego z zewnętrznymi aplikacjami.
> Większość aplikacji akceptuje format CSV, gdzie w pierwszej kolumnie jest słowo, 
> a w drugiej jego tłumaczenie. Przykładowo:
> ```
> tree,drzewo
> apple,jabłko
> ```
* Przyjmowanie bloku tekstu w obcym języku.
* Odpytywanie użytkownika o tłumaczenia kolejnych słów.
* Interaktywny interfejs użytkownika.
* Wsparcie znaków Unicode (alfabety niełacińskie).
* Wsparcie języków pisanych od prawej do lewej (RTL).
* (Ręczne tworzenie pojedynczych fiszek.)
### Wymagania niefunkcjonalne
* Aplikacja napisana w obiektowym języku programowania (preferowana Java).
* Kod napisany zgodnie ze sztuką na rok 2024.
* Pokrycie testami jednostkowymi na poziomie _co najmniej_ 75%.
* Istnieją podstawowe testy funkcjonalne.
* Błędy spowodowane przez użytkownika lub stan systemu są obsługiwane.
* Kod korzysta z wzorców obiektowych i dobrych praktyk tam gdzie to możliwe.
* Statyczna analiza kodu nie wykazuje błędów, "zapachów" (ang. _smells_)
ani złych praktyk.
* Istnieje udokumentowany styl kodu.
* Sposób uruchomienia oraz obsługi jest udokumentowany i prosty.
* Zawiera podstwową dokumentację. Przykłady:
  * sensowny changelog,
  * diagram klas modelu danych,
  * diagram komponentów,
  * diagramy przepływu,
  * diagram sekwencji,
  * wysokopoziomowy opis architektury,
  * FAQ,
  * itp.

## Kamień milowy 2
Funkcjonalności będą związane m.in. z warstwą persystencji, obsługą morfologii.

## Kamień milowy 3
Funkcjonalności będą skoncentrowane na syntaksie zdań.