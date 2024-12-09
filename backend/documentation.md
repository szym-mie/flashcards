# Dokumentacja Projektu: Generator Fiszek i Nie Tylko

## Wprowadzenie

Projekt **Generator Fiszek i Nie Tylko** wspiera naukę języków obcych, umożliwiając generowanie fiszek na podstawie tekstu oraz eksportowanie ich w kompatybilnym formacie. Aplikacja oferuje intuicyjny interfejs, obsługę wielu języków i wsparcie dla wielu alfabetów.

---

## Kamień Milowy 1: Podstawowa Aplikacja

Celem pierwszego etapu jest stworzenie aplikacji, która pozwoli na:

- Generowanie fiszek na podstawie wprowadzonego tekstu.
- Eksport wygenerowanych fiszek w standardowym formacie (CSV).
- Interaktywną współpracę z użytkownikiem przy tłumaczeniu słów.

---

## Wymagania Funkcjonalne

### 1. Eksport Fiszek

- Aplikacja generuje fiszki w formacie CSV, zgodnym z aplikacjami do nauki języków obcych.
- Struktura CSV:
    - **Kolumna 1:** Słowo w języku obcym.
    - **Kolumna 2:** Tłumaczenie użytkownika.
- Przykład:

```csv
  tree,drzewo  
  apple,jabłko
```

### 2. Przyjmowanie Bloku Tekstu

- Użytkownik może wprowadzić blok tekstu w dowolnym języku obcym.
- Tekst zostanie przetworzony w celu identyfikacji słów do utworzenia fiszek.

### 3. Interaktywne Odpytywanie o Tłumaczenia

- Aplikacja pyta użytkownika o tłumaczenia poszczególnych słów z tekstu.

### 4. Interfejs Użytkownika

- Prosty, interaktywny interfejs umożliwiający:
    - Wprowadzenie tekstu.
    - Podgląd wygenerowanych fiszek.
    - Eksport zestawów do pliku.

- Pełna obsługa znaków Unicode dla różnych alfabetów, np. chińskiego czy cyrylicy.

### 5. Obsługa Języków Pisanych Od Prawej do Lewej (RTL)

- Aplikacja obsługuje języki takie jak arabski czy hebrajski.
- Kierunek zapisu uwzględniony w procesie tworzenia fiszek oraz ich wyświetlaniu.

---

## API - Swagger UI

Wystarczy uruchomić serwer i 
[przejść na stronę Swagger UI](http://localhost:8080/swagger-ui.html)


## Struktura projektu - backend

Główne pakiety projektu to:

- `controller`
- `service`
- `model`
- `dto`
- `handler`
- `util`
- `exception`

Każdy z pakietów pełni określoną rolę w projekcie i zawiera dedykowane klasy.

---

### Pakiet: `controller`

#### Klasa: `FlashcardController`

#### Opis

`FlashcardController` to klasa kontrolera odpowiedzialna za obsługę żądań związanych z zarządzaniem fiszkami. Wykorzystuje adnotacje Spring REST, aby umożliwić interakcję z API.

---

#### Adnotacje i Konfiguracje

- `@RestController`: Klasa jest kontrolerem REST API.
- `@RequestMapping("/api/flashcards")`: Wszystkie metody w tej klasie obsługują żądania o prefiksie URL `/api/flashcards`.
- `@AllArgsConstructor`: Automatyczne generowanie konstruktora z polami finalnymi.

---

#### Pola

1. **`textProcessorService`**
    - Typ: `TextProcessorService`
    - Opis: Odpowiedzialny za przetwarzanie i analizę tekstu wejściowego.

2. **`flashcardService`**
    - Typ: `FlashcardService`
    - Opis: Zarządza operacjami na fiszkach.

---

#### Endpointy

#### 1. `GET /api/flashcards`

- **Opis:** Zwraca listę wszystkich fiszek.
- **Zwracany typ:** `ResponseEntity<List<Flashcard>>`
- **Przykładowa odpowiedź:**

```json
[
    {
        "word": "tree",
        "translation": "drzewo"
    },
    {
        "word": "apple",
        "translation": "jabłko"
    }
]
```

#### 2. `POST /api/flashcards`

- **Opis:** Przetwarza zdanie wejściowe, ekstraktuje słowa i dodaje je jako nowe fiszki.
- **Parametry wejściowe:** `SentenceRequest` w formacie JSON.
- **Zwracany typ:** `ResponseEntity<List<Flashcard>>`
- **Przykład żądania:**

```json
{
    "text": "This is an example sentence."
}
```

- **Przykładowa odpowiedź:**
  Successfully processed!

#### 3. `PUT /api/flashcards`

- **Opis:** Aktualizuje tłumaczenie istniejącej fiszki.
- **Parametry wejściowe:** `Flashcard` w formacie JSON.
- **Zwracany typ:** `ResponseEntity<Flashcard>`
- **Przykład żądania:**

```json
{
    "word": "tree",
    "translation": "drzewo"
}
```

- **Przykładowa odpowiedź:**
  Successfully translated!

#### 4. `DELETE /api/flashcards`

- **Opis:** Usuwa fiszkę na podstawie podanego słowa.
- **Parametry wejściowe:** `Flashcard` w formacie JSON - fiszka do usunięcia.
- **Zwracany typ:** `ResponseEntity<Flashcard>`
- **Przykład żądania:**

```json
{
    "word": "jabłko",
    "translation": "" 
}
```

- **Przykładowa odpowiedź:**
  Successfully removed!

#### 5. `GET /api/flashcards/export`

- **Opis:** Eksportuje wszystkie fiszki w formacie CSV.
- **Zwracany typ:** `ResponseEntity<String>` (plik CSV jako string).
- **Przykładowa odpowiedź:**

```csv
word,translation  
tree,drzewo  
apple,jabłko
```

---

### Pakiet: `service`

#### 1. Klasa: `TextProcessorService`

#### Opis

`TextProcessorService` to serwis odpowiedzialny za przetwarzanie tekstu, w tym za ekstrakcję słów z ciągów znaków.

#### Adnotacje

- `@Service`: Klasa jest komponentem Springa, który zarządza logiką biznesową.

#### Pola

1. **`wordPattern`**
    - Typ: `Pattern`
    - Opis: Szablon *regex* odpowiedzialny za rozdzielanie bloku tekstu na osobne słowa.

#### Metody

1. **`extractWords(String text)`**
    - Rozdziela podany tekst na słowa
    - Parametry:
        - `text` – tekst wejściowy do analizy.
    - Zwracany typ: `List<String>`.

#### Działanie

- Usuwa znaki niebędące literami lub spacjami.
- Konwertuje tekst na małe litery.
- Dzieli tekst na słowa, ignorując puste elementy.

---

### Pakiet: `repository`

#### Klasa: `FlashcardRepository`

#### Opis

`FlashcardRepository` odpowiada za przechowywanie fiszek oraz dostarcza metody do ich obsługi.

#### Adnotacje

- `@Repository`: Klasa jest komponentem springa odpowiedzialnym za perzystencję danych.

#### Pola

1. `flashcards`
    - Typ: `Map<String, Flashcard>`
    - Opis: Mapa zawierająca fiszki, kluczami są główne słowa z danych fiszek.

#### Metody

1. `getAll()`
    - Zwracany typ: `List<Flashcard>`
    - Opis: Zwraca listę wszystkich fiszek

2. `findByWord(String word)`
    - Zwracany typ: `Optional<Flashcard>`
    - Argumenty:
        - `String word`: Słowo, dla którego ma zostać zwrócona fiszka.
    - Opis: Zwraca `Optional` z fiszką zawierającą dane słowo lub pusty `Optional` jeśli fiszka nie istnieje

3. `findByWordOrThrow(String word)`
    - Zwracany typ: `Flashcard`
    - Argumenty:
        - `String word`: Słowo, dla ktorego ma zostać zwrócona fiszka
    - Opis: Działa analogicznie do `findByWord`, jednak rzuca wyjątek jeśli fiszka nie została znaleziona

4. `updateByWord(String word, String translation)`
    - Typ: `void`
    - Argumenty:
        - `String word`: Słowo, dla którego tłumaczenie ma zostać zmienione
        - `String translation`: Nowe tłumaczenie
    - Opis: Metoda zmienia tłumaczenie fiszki dla danego słowa. **UWAGA**, metoda korzysta z `findWordOrThrow`, rzuca wyjątek jeśli fiszka nie została znaleziona

5. `removeByWord(String word)`
    - Typ: `void`
    - Argumenty:
        - `String word`: Słowo, dla którego fiszka ma zostać usunięta.
    - Opis: Usuwa fiszkę

---

### Pakiet: `model`

#### Klasa: `Flashcard`

#### Opis

`Flashcard` to model danych reprezentujący pojedynczą fiszkę. Zawiera informacje o słowie i jego tłumaczeniu.

---

#### Adnotacje

- `@Data`: Łączy funkcje adnotacji:
    - `@Getter`: Automatycznie generuje metody `get` dla wszystkich pól klasy.
    - `@Setter`: Automatycznie generuje metody `set` dla wszystkich pól klasy.    
    - `@ToString`: Automatycznie generuje metodę `toString` dla klasy.
    - `@EqualsAndHashcode`: Automatycznie generuje metory `equals` i `hashCode`.
    - `@RequiredArgsConstructor`: Automatycznie tworzy konstruktor dla wszystkich wymaganych pól

---

#### Pola

1. **`word`**
    - Typ: `String`
    - Adnotacje:
        - `@NotBlank`: pole nie może być równe `null` ani nie może być pustym łańcuchem (składającym się tylko z białych znaków).
    - Opis: Słowo w języku obcym.

2. **`translation`**
    - Typ: `String`
    - Adnotacje:
        - `@NotNull`: pole nie może być równe `null`
    - Opis: Tłumaczenie słowa wprowadzane przez użytkownika.

---

#### Metody

1. **`getWord()`**
    - Zwraca wartość pola `word`.

2. **`setWord(String word)`**
    - Ustawia wartość pola `word`.

3. **`getTranslation()`**
    - Zwraca wartość pola `translation`.

4. **`setTranslation(String translation)`**
    - Ustawia wartość pola `translation`.

---

### Pakiet: `dto`

#### 1. Klasa: `SentenceRequest`

#### Opis

`SentenceRequest` to klasa DTO (Data Transfer Object) reprezentująca żądanie użytkownika do przetwarzania zdania w aplikacji.

#### Adnotacje

- `@Data`: Łączy funkcje adnotacji:
    - `@Getter`: Automatycznie generuje metody `get` dla wszystkich pól klasy.
    - `@Setter`: Automatycznie generuje metody `set` dla wszystkich pól klasy.    
    - `@ToString`: Automatycznie generuje metodę `toString` dla klasy.
    - `@EqualsAndHashcode`: Automatycznie generuje metory `equals` i `hashCode`.
    - `@RequiredArgsConstructor`: Automatycznie tworzy konstruktor dla wszystkich wymaganych pól

#### Pola

1. **`text`**
    - Typ: `String`
    - Opis: Tekst wprowadzany przez użytkownika do przetworzenia.
    - Walidacja: `@NotBlank` – pole nie może być puste.

---

### Pakiet: `handler`

#### Klasa: `RestResponseEntityExceptionHandler`

#### Opis

`RestResponseEntityExceptionHandler` to klasa odpowiedzialna za globalną obsługę wyjątków w aplikacji. Korzysta z mechanizmów Springa do przechwytywania wyjątków i generowania odpowiednich odpowiedzi HTTP.

---

#### Adnotacje

- `@ControllerAdvice`: Wskazuje, że klasa zawiera globalną logikę obsługi wyjątków dla wszystkich kontrolerów w aplikacji.
- `@ExceptionHandler`: Definiuje metodę obsługującą określony typ wyjątków.

---

#### Metody

#### 1. `handleIOException(IOException exception, WebRequest request)`

- **Opis:** Obsługuje wyjątki typu `IOException` i zwraca odpowiedź z kodem statusu HTTP 500 (Internal Server Error).
- **Parametry:**
    - `exception` – przechwycony wyjątek `IOException`.
    - `request` – obiekt `WebRequest` zawierający szczegóły żądania.
- **Zwracany typ:** `ResponseEntity<Object>`
- **Działanie:**
    - Ustawia ciało odpowiedzi na `"IO exception occurred"`.
    - Generuje odpowiedź z nagłówkami pustymi, kodem statusu `500 Internal Server Error` oraz informacjami o żądaniu.

---

### Testowanie

- **Pokrycie Testami:**
    - **Klasy:** 100%
    - **Metody:** 90%
    - **Linie Kodów:** 96%

