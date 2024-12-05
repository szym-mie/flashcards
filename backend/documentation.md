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
- **Zwracany typ:** `ResponseEntity<String>`
- **Przykład żądania:**

```json
{
    "text": "This is an example sentence."
}
```

- **Przykładowa odpowiedź:**
  Successfully processed!

#### 3. `PUT /api/flashcards/{word}`

- **Opis:** Aktualizuje tłumaczenie istniejącej fiszki.
- **Parametry wejściowe:**
    - Ścieżka: `word` – słowo do przetłumaczenia.
    - Treść: `TranslationRequest` w formacie JSON.
- **Zwracany typ:** `ResponseEntity<String>`
- **Przykład żądania:**

```json
{
    "text": "drzewo"
}
```

- **Przykładowa odpowiedź:**
  Successfully translated!

#### 4. `DELETE /api/flashcards/{word}`

- **Opis:** Usuwa fiszkę na podstawie podanego słowa.
- **Parametry wejściowe:** `word` – słowo do usunięcia.
- **Zwracany typ:** `ResponseEntity<String>`
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

#### 1. Klasa: `FlashcardService`

#### Opis

`FlashcardService` to serwis odpowiedzialny za zarządzanie operacjami na fiszkach. Obsługuje funkcje CRUD dla obiektów typu `Flashcard`.

#### Adnotacje

- `@Service`: Klasa jest komponentem Springa, który zarządza logiką biznesową.

#### Pola

1. **`flashcards`**
    - Typ: `List<Flashcard>`
    - Opis: Lista przechowująca wszystkie fiszki w aplikacji.

#### Metody

1. **`getAll()`**
    - Zwraca wszystkie fiszki w systemie.
    - Zwracany typ: `List<Flashcard>`.

2. **`getByWord(String word)`**
    - Wyszukuje fiszkę na podstawie podanego słowa.
    - Zwracany typ: `Optional<Flashcard>`.

3. **`getByWordOrThrow(String word)`**
    - Wyszukuje fiszkę na podstawie słowa. Jeśli fiszka nie istnieje, rzuca wyjątek `NotFoundException`.
    - Zwracany typ: `Flashcard`.

4. **`add(String word)`**
    - Dodaje nową fiszkę z podanym słowem. Jeśli fiszka już istnieje, usuwa ją przed dodaniem nowej wersji.
    - Parametry:
        - `word` – słowo do dodania.

5. **`update(String word, String translation)`**
    - Aktualizuje tłumaczenie dla istniejącej fiszki.
    - Parametry:
        - `word` – słowo, którego tłumaczenie ma być zaktualizowane.
        - `translation` – nowe tłumaczenie.

6. **`remove(String word)`**
    - Usuwa fiszkę na podstawie podanego słowa.
    - Parametry:
        - `word` – słowo do usunięcia.

---

#### 2. Klasa: `TextProcessorService`

#### Opis

`TextProcessorService` to serwis odpowiedzialny za przetwarzanie tekstu, w tym za ekstrakcję słów z ciągów znaków.

#### Adnotacje

- `@Service`: Klasa jest komponentem Springa, który zarządza logiką biznesową.

#### Metody

1. **`extractWords(String text, Direction direction)`**
    - Ekstraktuje słowa z podanego tekstu, uwzględniając kierunek tłumaczenia.
    - Parametry:
        - `text` – tekst wejściowy do analizy.
        - `direction` – kierunek przetwarzania (np. RTL - od prawej do lewej).
    - Zwracany typ: `List<String>`.

2. **`extractWords(SentenceRequest sentenceRequest)`**
    - Ekstraktuje słowa z obiektu `SentenceRequest`.
    - Parametry:
        - `sentenceRequest` – obiekt DTO zawierający tekst i kierunek tłumaczenia.
    - Zwracany typ: `List<String>`.

#### Działanie

- Usuwa znaki niebędące literami lub spacjami.
- Konwertuje tekst na małe litery.
- Dzieli tekst na słowa, ignorując puste elementy.
- Dostosowuje kolejność słów w zależności od kierunku tłumaczenia (`Direction.RTL` lub `Direction.LTR`).

---

### Pakiet: `model`

#### Klasa: `Flashcard`

#### Opis

`Flashcard` to model danych reprezentujący pojedynczą fiszkę. Zawiera informacje o słowie i jego tłumaczeniu.

---

#### Adnotacje

- `@Getter`: Automatycznie generuje metody `get` dla wszystkich pól klasy.
- `@Setter`: Automatycznie generuje metody `set` dla wszystkich pól klasy.
- `@AllArgsConstructor`: Automatycznie generuje konstruktor z wszystkimi polami klasy.

---

#### Pola

1. **`word`**
    - Typ: `String`
    - Opis: Słowo w języku obcym.

2. **`translation`**
    - Typ: `String`
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

- `@Getter`: Automatycznie generuje metody `get` dla pól klasy.
- `@NotBlank`: Walidacja zapewniająca, że pole nie jest puste ani nie zawiera samych białych znaków.

#### Pola

1. **`text`**
    - Typ: `String`
    - Opis: Tekst wprowadzany przez użytkownika do przetworzenia.
    - Walidacja: `@NotBlank` – pole nie może być puste.

2. **`direction`**
    - Typ: `Direction`
    - Opis: Kierunek tekstu.

---

### 2. Klasa: `TranslationRequest`

#### Opis

`TranslationRequest` to klasa DTO reprezentująca żądanie użytkownika dotyczące aktualizacji tłumaczenia fiszki.

#### Adnotacje

- `@Getter`: Automatycznie generuje metody `get` dla pól klasy.
- `@NotBlank`: Walidacja zapewniająca, że pole nie jest puste ani nie zawiera samych białych znaków.

#### Pola

1. **`text`**
    - Typ: `String`
    - Opis: Nowe tłumaczenie wprowadzane przez użytkownika.
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

### Pakiet: `util`

#### Klasa: `Direction`

#### Opis
`Direction` to typ wyliczeniowy (`enum`) reprezentujący kierunek przetwarzania tekstu. Wykorzystywany jest do określania, czy tekst powinien być przetwarzany od lewej do prawej (LTR - Left to Right) czy od prawej do lewej (RTL - Right to Left).

---

#### Wartości Enum
1. **`LTR`**
    - Opis: Oznacza kierunek przetwarzania tekstu od lewej do prawej.
    - Przykład: Angielski, Polski.

2. **`RTL`**
    - Opis: Oznacza kierunek przetwarzania tekstu od prawej do lewej.
    - Przykład: Arabski, Hebrajski.

---

### Pakiet: `exception`

#### Klasa: `NotFoundException`

#### Opis

`NotFoundException` to klasa wyjątku, która reprezentuje sytuację, gdy poszukiwany zasób nie został znaleziony. Wyjątek ten jest obsługiwany przez Springa i automatycznie generuje odpowiedź HTTP z kodem statusu `404 Not Found`.

---

#### Adnotacje

- `@ResponseStatus(HttpStatus.NOT_FOUND)`: Ustawia status HTTP odpowiedzi na `404 Not Found`, gdy wyjątek zostanie rzucony.

---

#### Dziedziczenie

- Klasa `NotFoundException` dziedziczy z `RuntimeException`, co oznacza, że jest wyjątkiem typu unchecked (nie wymaga jawnej obsługi w kodzie).

---

### Testowanie

- **Pokrycie Testami:**
    - **Klasy:** 100%
    - **Metody:** 90%
    - **Linie Kodów:** 96%

