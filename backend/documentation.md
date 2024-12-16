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
  - W wypadku sukcesu: 204 No Cotent

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
  - W wypadku sukcesu: 204 No Cotent

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
    - W wypadku sukcesu: 204 No Cotent

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

---

## Kamień milowy 2: Funkcjonalności związane z morfologią oraz fonetyką słów. Podstawowa warstwa persystencji.
W tym etapie dodajemy funkcjonalności związane z morfologią oraz fonetyką słów. Ponadto stworzymy podstawową warstwę persystencji.

---

### Wymagania funkcjonalne:

- Podział słów na części mowy.
- Formy fleksyjne.
- Wyświetlanie tekstu w interlinii.
- (Transliteracja) i transkrypcja.
- Wiele tłumaczeń jednego słowa.
- Eksportowanie tego do csv w kolejnych kolumnach.
- Persystencja form słownikowych.

---

### Sekcja: Baza Danych

#### Struktura Bazy Danych SQLite

Aplikacja korzysta z bazy danych SQLite, która składa się z dwóch tabel: `Translation` i `Language`. Struktura bazy danych umożliwia przechowywanie informacji o tłumaczeniach słów w różnych językach, z odniesieniem do tabeli języków.

#### Schemat Tabel

1. **Tabela: `Translation`**
    - Przechowuje dane dotyczące tłumaczeń słów.

   #### Kolumny:
    - **`id`** *(int)*: Unikalny identyfikator tłumaczenia.
        - Klucz główny.
    - **`word`** *(text)*: Słowo w formie bazowej (np. "run").
    - **`translation`** *(text)*: Tłumaczenie słowa (np. "bieg").
    - **`language`** *(varchar(2))*: Kod języka powiązanego z tłumaczeniem (np. "en").
        - Klucz obcy odnoszący się do tabeli `Language`.

2. **Tabela: `Language`**
    - Przechowuje dane o językach.

   #### Kolumny:
    - **`id`** *(varchar(2))*: Kod języka (np. "en").
        - Klucz główny.
    - **`language`** *(varchar(30))*: Pełna nazwa języka (np. "English").

#### Relacje

- **Relacja między tabelami**:
    - Kolumna `language` w tabeli `Translation` jest kluczem obcym odnoszącym się do kolumny `id` w tabeli `Language`.
    - Relacja umożliwia powiązanie każdego tłumaczenia z odpowiednim językiem.

#### Przykładowe Dane w Bazie Danych

1. **Tabela `Language`**

| id  | language  |
|-----|-----------|
| en  | English   |
| fr  | French    |
| es  | Spanish   |

2. **Tabela `Translation`**

| id  | word   | translation | language |
|-----|--------|-------------|----------|
| 1   | run    | bieg        | en       |
| 2   | courir | biegać      | fr       |
| 3   | correr | correr      | es       |

#### Obsługa w Aplikacji

- **Repozytoria**:
    - Aplikacja używa repozytoriów Spring Data JPA, takich jak `LemmaRepository` i `LanguageRepository`, aby zapewnić dostęp do danych w tabelach `Translation` i `Language`.

- **Usługi**:
    - Logika aplikacji, realizowana w warstwie usług (`LanguageService`, `LemmaService`), pozwala na interakcję z bazą danych w sposób abstrakcyjny, bez konieczności bezpośredniego pisania zapytań SQL.
---

### Sekcja: Diagram Przepływu (Flowchart)

#### Opis

W celu lepszego zrozumienia architektury aplikacji i przepływu danych między komponentami, poniżej znajduje się link do diagramu przepływu (flowchart).

#### Link do Flowcharta

[Kliknij tutaj, aby otworzyć diagram przepływu aplikacji](https://miro.com/welcomeonboard/cXllaENlQU9oUzNYa1FoSG5FMVc4NWJpNFh5Rk5YVlBZM3l0V1hIMTMyWXZOSGsvbGlkVG85Vkt2OXZNTW9IN2ZtN1l3dUhsWHpZMmN2eWFvYUtiYmZ2YlNVWGJ4clg5QWJBQnlVd0kxbFBuUFBEQ0RLR3JEWU9BaTREZGpjRlAhZQ==?share_link_id=791400547424)

---

## Struktura projektu - backend

Główne pakiety projektu to:

- `config`
- `controller`
- `dto`
- `expection`
- `handler`
- `model`
- `repository`
- `service` 

Każdy z pakietów pełni określoną rolę w projekcie i zawiera dedykowane klasy.
Poniżej znajduję się opis zmian wprowadzonych w kamieniu milowym 2.

---

### Pakiet: `config`

#### 1. Klasa: `DataConfig`

#### Opis

`DataConfig` to klasa konfiguracji Springa, odpowiedzialna za konfigurację źródła danych (`DataSource`) aplikacji.

#### Adnotacje

- `@Bean`: Wskazuje, że metoda zwraca komponent zarządzany przez kontener Springa.

#### Pola

1. **`environment`**
    - Typ: `Environment`
    - Opis: Obiekt zarządzany przez Springa, który przechowuje właściwości środowiskowe aplikacji.

#### Metody

1. **`dataSource()`**
    - Konfiguruje i zwraca źródło danych (`DataSource`).
    - Zwracany typ: `DataSource`.
    - Szczegóły konfiguracji:
        - Pobiera dane połączenia z pliku konfiguracyjnego aplikacji za pomocą `environment`.

---

#### 2. Klasa: `WebConfiguration`

#### Opis

`WebConfiguration` to klasa konfiguracji Springa, odpowiedzialna za obsługę zasobów statycznych oraz reguł CORS.

#### Adnotacje

- `@Configuration`: Wskazuje, że klasa zawiera konfigurację Springa.
- `@Override`: Używana do nadpisania metod z interfejsu `WebMvcConfigurer`.

#### Metody

1. **`addResourceHandlers(ResourceHandlerRegistry registry)`**
    - Konfiguruje obsługę zasobów statycznych.
    - Parametry:
        - `registry` – rejestrator obsługi zasobów.
    - Szczegóły:
        - Mapuje zasoby `/static/**` na katalog `classpath:/static/`.
        - Dodaje cache z okresem trwania 6000 sekund.
        - Dodaje niestandardowy resolver `PathResourceResolver`.

2. **`addCorsMappings(CorsRegistry registry)`**
    - Konfiguruje reguły CORS.
    - Parametry:
        - `registry` – rejestrator reguł CORS.
    - Szczegóły:
        - Pozwala na dostęp do wszystkich endpointów (`/**`) z określonych źródeł:
            - `http://localhost:5173`
            - `http://localhost:5174`
        - Akceptuje metody HTTP: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`.
        - Zezwala na wszystkie nagłówki.

---

### Pakiet: `controller`

#### 2. Klasa: `LanguageController`

#### Opis

`LanguageController` zarządza operacjami związanymi z językami, takimi jak pobieranie, tworzenie i aktualizowanie.

#### Adnotacje

- `@RestController`: Klasa kontrolera obsługująca żądania HTTP w architekturze REST.
- `@RequestMapping("/api/languages")`: Mapuje endpointy kontrolera do ścieżki `/api/languages`.
- `@AllArgsConstructor`: Generuje konstruktor dla wszystkich pól klasy.

#### Pola

1. **`languageService`**
    - Typ: `LanguageService`
    - Opis: Serwis zarządzający operacjami na językach.

#### Endpointy

1. **`GET /api/languages`**
    - Opis: Pobiera wszystkie języki.
    - Zwracany typ: `ResponseEntity<List<Language>>`.

2. **`POST /api/languages`**
    - Opis: Tworzy nowy język.
    - Parametry:
        - `language` – obiekt reprezentujący język.
    - Zwracany typ: `ResponseEntity<Language>`.

3. **`PUT /api/languages`**
    - Opis: Aktualizuje istniejący język.
    - Parametry:
        - `language` – obiekt języka do aktualizacji.
    - Zwracany typ: `ResponseEntity<Language>`.

---

#### 3. Klasa: `LemmaController`

#### Opis

`LemmaController` zarządza operacjami związanymi z lemmami, takimi jak ich wyszukiwanie i tworzenie.

#### Adnotacje

- `@RestController`: Klasa kontrolera obsługująca żądania HTTP w architekturze REST.
- `@RequestMapping("/api/lemmas")`: Mapuje endpointy kontrolera do ścieżki `/api/lemmas`.
- `@AllArgsConstructor`: Generuje konstruktor dla wszystkich pól klasy.

#### Pola

1. **`lemmaRepository`**
    - Typ: `LemmaRepository`
    - Opis: Repozytorium zarządzające lemmami.

#### Endpointy

1. **`GET /api/lemmas/{name}`**
    - Opis: Pobiera lemmę na podstawie jej nazwy i języka.
    - Parametry:
        - `name` – nazwa lemmy (parametr ścieżki).
        - `language.id` – ID języka (parametr żądania).
        - `language.name` – nazwa języka (parametr żądania).
    - Zwracany typ: `ResponseEntity<Lemma>`.

2. **`POST /api/lemmas`**
    - Opis: Tworzy lub aktualizuje lemmę.
    - Parametry:
        - `lemma` – obiekt reprezentujący lemmę.
    - Zwracany typ: `ResponseEntity<Lemma>`.

---

### Pakiet: `dto`

#### 1. Klasa: `FlashcardsResponse`

#### Opis

`FlashcardsResponse` to obiekt transferu danych (DTO) używany do zwracania informacji o przetworzonym zdaniu oraz listy fiszek.

#### Adnotacje

- `@AllArgsConstructor`: Generuje konstruktor dla wszystkich pól klasy.
- `@Getter`: Generuje metody getter dla wszystkich pól klasy.

#### Pola

1. **`sentence`**
    - Typ: `Sentence`
    - Opis: Przetworzone zdanie, które było źródłem dla fiszek.

2. **`flashcards`**
    - Typ: `List<Flashcard>`
    - Opis: Lista fiszek wygenerowanych na podstawie zdania.

---

### Pakiet: `handler`

#### 1. Klasa: `RestResponseEntityExceptionHandler`

#### Opis

`RestResponseEntityExceptionHandler` to klasa globalnego obsługiwania wyjątków w aplikacji, używana do przechwytywania i przetwarzania wyjątków rzucanych przez kontrolery REST.

#### Adnotacje

- `@ControllerAdvice`: Umożliwia definiowanie globalnej obsługi wyjątków dla wszystkich kontrolerów w aplikacji.

#### Metody

1. **`handleIOException(IOException exception, WebRequest request)`**
    - Obsługuje wyjątki typu `IOException`.
    - Parametry:
        - `exception`: Obiekt wyjątku `IOException`.
        - `request`: Obiekt żądania `WebRequest`.
    - Zwracany typ: `ResponseEntity<Object>`
    - Działanie: Zwraca odpowiedź HTTP z kodem `500 Internal Server Error` i komunikatem `"IO exception occurred"`.

2. **`handleNoSuchElementException(NoSuchElementException exception)`**
    - Obsługuje wyjątki typu `NoSuchElementException`.
    - Parametry:
        - `exception`: Obiekt wyjątku `NoSuchElementException`.
    - Zwracany typ: `ResponseEntity<String>`
    - Działanie: Zwraca odpowiedź HTTP z kodem `404 Not Found`.

3. **`handleResourceAlreadyExists(ResourceAlreadyExistsException exception)`**
    - Obsługuje wyjątki typu `ResourceAlreadyExistsException`.
    - Parametry:
        - `exception`: Obiekt wyjątku `ResourceAlreadyExistsException`.
    - Zwracany typ: `ResponseEntity<String>`
    - Działanie: Zwraca odpowiedź HTTP z kodem `409 Conflict`.

---

### Pakiet: `model`

#### 1. Klasa: `Flashcard`

#### Opis

`Flashcard` reprezentuje jednostkę słownika zawierającą informacje o słowie, jego podstawowej formie (lemma), tłumaczeniu, części mowy oraz transkrypcji.

#### Adnotacje

- `@Data`: Automatycznie generuje metody `get`, `set`, `equals`, `hashCode` i `toString`.
- `@NotBlank`: Wymaga, aby pola nie były puste ani null.
- `@NotNull`: Wymaga, aby pola były ustawione.

#### Pola

1. **`word`**
    - Typ: `String`
    - Opis: Słowo reprezentowane przez fiszkę. Wartość nie może być pusta.

2. **`lemma`**
    - Typ: `String`
    - Opis: Podstawowa forma słowa (lemma).

3. **`translation`**
    - Typ: `String`
    - Opis: Tłumaczenie słowa. Domyślnie puste.

4. **`partOfSpeech`**
    - Typ: `String`
    - Opis: Część mowy (np. czasownik, rzeczownik). Domyślnie puste.

5. **`transcription`**
    - Typ: `String`
    - Opis: Transkrypcja fonetyczna słowa. Domyślnie puste.

#### Konstruktor

1. **`Flashcard(String word)`**
    - Parametry:
        - `word`: Słowo reprezentowane przez fiszkę.
    - Działanie: Wymusza niepustą wartość dla `word`.

---

#### 2. Klasa: `Language`

#### Opis

`Language` reprezentuje język, w którym operuje aplikacja.

#### Adnotacje

- `@Entity`: Klasa mapowana na tabelę w bazie danych.
- `@Data`: Automatycznie generuje metody `get`, `set`, `equals`, `hashCode` i `toString`.
- `@Id`: Oznacza pole jako klucz główny.
- `@NotBlank`: Wymaga, aby pola nie były puste ani null.
- `@AllArgsConstructor`: Generuje konstruktor z wszystkimi polami.

#### Pola

1. **`id`**
    - Typ: `String`
    - Opis: Unikalny identyfikator języka (np. "en" dla angielskiego).

2. **`name`**
    - Typ: `String`
    - Opis: Nazwa języka (np. "English").

#### Konstruktor

- **Domyślny konstruktor** dla JPA.

---

#### 3. Klasa: `Lemma`

#### Opis

`Lemma` reprezentuje podstawową formę słowa związaną z językiem.

#### Adnotacje

- `@Entity`: Klasa mapowana na tabelę w bazie danych.
- `@IdClass(LemmaId.class)`: Klucz główny klasy składa się z pól `name` i `language`.
- `@Data`: Automatycznie generuje metody `get`, `set`, `equals`, `hashCode` i `toString`.
- `@Id`: Oznacza pola jako klucz główny.
- `@NotBlank`: Wymaga, aby pola nie były puste ani null.
- `@NotNull`: Wymaga, aby pola były ustawione.
- `@ManyToOne`: Relacja wiele-do-jednego z encją `Language`.
- `@JoinColumn(name = "language_id")`: Kolumna klucza obcego do `Language`.

#### Pola

1. **`name`**
    - Typ: `String`
    - Opis: Nazwa lematy.

2. **`translation`**
    - Typ: `String`
    - Opis: Tłumaczenie lematy.

3. **`language`**
    - Typ: `Language`
    - Opis: Język powiązany z lematą.

#### Konstruktor

- **Domyślny konstruktor** dla JPA.

---

#### 4. Klasa: `LemmaId`

#### Opis

`LemmaId` reprezentuje klucz złożony dla encji `Lemma`.

#### Adnotacje

- `@Data`: Automatycznie generuje metody `get`, `set`, `equals`, `hashCode` i `toString`.
- `@NoArgsConstructor`: Generuje domyślny konstruktor dla JPA.

#### Pola

1. **`name`**
    - Typ: `String`
    - Opis: Nazwa lematy.

2. **`language`**
    - Typ: `String`
    - Opis: Identyfikator języka.

---

#### 5. Klasa: `Sentence`

#### Opis

`Sentence` reprezentuje zdanie powiązane z określonym językiem.

#### Adnotacje

- `@Value`: Tworzy niemutowalną klasę z polami tylko do odczytu.
- `@AllArgsConstructor`: Generuje konstruktor z wszystkimi polami.
- `@NotBlank`: Wymaga, aby pola nie były puste ani null.
- `@NotNull`: Wymaga, aby pola były ustawione.

#### Pola

1. **`text`**
    - Typ: `String`
    - Opis: Tekst zdania.

2. **`language`**
    - Typ: `Language`
    - Opis: Język zdania.

---

### Pakiet: `repository`

#### 2. Interfejs: `LanguageRepository`

#### Opis

`LanguageRepository` jest interfejsem repozytorium dla encji `Language`, opartym na Spring Data JPA. Umożliwia operacje CRUD na encji języka.

#### Adnotacje

- `@Repository`: Oznacza interfejs jako komponent Springa, odpowiedzialny za operacje na bazie danych.

#### Dziedziczenie

- Dziedziczy po `JpaRepository<Language, String>`:
    - `Language`: Typ zarządzanej encji.
    - `String`: Typ klucza głównego encji.

---

#### 3. Interfejs: `LemmaRepository`

#### Opis

`LemmaRepository` jest interfejsem repozytorium dla encji `Lemma`, opartym na Spring Data JPA. Umożliwia operacje CRUD oraz dodatkowe wyszukiwanie na podstawie słowa i języka.

#### Adnotacje

- `@Repository`: Oznacza interfejs jako komponent Springa, odpowiedzialny za operacje na bazie danych.
- `@Query`: Definiuje niestandardowe zapytanie JPQL.

#### Dziedziczenie

- Dziedziczy po `JpaRepository<Lemma, Long>`:
    - `Lemma`: Typ zarządzanej encji.
    - `Long`: Typ klucza głównego encji.

#### Metody

1. **`findOneByNameAndLanguage(String name, Language language)`**
    - Opis: Wyszukuje lemat na podstawie nazwy i języka.
    - Parametry:
        - `name`: Nazwa lematy.
        - `language`: Obiekt języka powiązanego z lematą.
    - Zwracany typ: `Optional<Lemma>`.
---


### Pakiet: `service`

#### 1. Klasa: `LanguageService`

#### Opis

`LanguageService` to serwis odpowiedzialny za zarządzanie operacjami na encji `Language`, w tym za tworzenie, aktualizowanie oraz pobieranie listy języków.

#### Adnotacje

- `@Service`: Klasa jest komponentem Springa, który zarządza logiką biznesową.
- `@AllArgsConstructor`: Generuje konstruktor z wszystkimi wymaganymi polami.

#### Pola

1. **`languageRepository`**
    - Typ: `LanguageRepository`
    - Opis: Repozytorium Spring Data JPA dla encji `Language`, umożliwiające operacje na bazie danych.

#### Metody

1. **`getAll()`**
    - Opis: Pobiera listę wszystkich języków z repozytorium.
    - Zwracany typ: `List<Language>`.

2. **`create(Language language)`**
    - Opis: Tworzy nowy język. Sprawdza, czy język o danym `id` już istnieje, a jeśli tak, zgłasza wyjątek.
    - Parametry:
        - `language`: Obiekt języka do utworzenia.
    - Zwracany typ: `Language`.

   #### Wyjątki:
    - `ResourceAlreadyExistsException`: Jeśli język o danym `id` już istnieje.

3. **`update(Language language)`**
    - Opis: Aktualizuje istniejący język. Jeśli język o danym `id` nie istnieje, zgłasza wyjątek.
    - Parametry:
        - `language`: Obiekt języka do zaktualizowania.
    - Zwracany typ: `void`.

   #### Wyjątki:
    - `NoSuchElementException`: Jeśli język o danym `id` nie istnieje.

---

### Testowanie

- **Pokrycie Testami:**
    - **Klasy:** 92%
    - **Metody:** 90%
    - **Linie Kodów:** 91%
---


