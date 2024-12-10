import { http, HttpResponse } from "msw";
import Transient from "./transient";

const getAllReturn = [
  {
    word: "hello",
    translation: "hej"
  },
  {
    word: "buy",
    translation: "kup"
  },
  {
    word: "yourself",
    translation: "se"
  },
  {
    word: "glue",
    translation: "klej"
  }
];

const mapTransient = new Transient(() => new Map);

// TODO finish mock handlers
const flashcardControllerHandlers = (host) => {
  const endpoint = (path) => host + path;

  return [
    http.get(endpoint("/api/flashcards"), () => HttpResponse.json(getAllReturn)),
    http.post(endpoint("/api/flashcards"), async (req) => {
      const { text } = await req.request.json();
      const flashcards = text.split(/[ \\n]+/)
        .map(e => ({ word: e, translation: "" }));

      const addedFlashcards = new Map();
      
      for (const flashcard of flashcards) {
        mapTransient.update((m) => {
          const word = flashcard.word;
          const entry = [word, flashcard];
          
          if (!m.has(word)) {
            addedFlashcards.set(word, flashcard);

            const entries = [...m.entries()];
            return new Map(entries.concat([entry]));
          }

          return m;
        });
      }

      return HttpResponse.json(Object.fromEntries(addedFlashcards));
    }),
    http.put(endpoint("/api/flashcards"), async (req) => {
      const { word, translation } = await req.request.json();
      let shouldThrow = true;

      mapTransient.update((m) => {
        if (m.has(word)) {
          m.set(word, { word, translation });
          shouldThrow = false;
        }

        return m;
      });

      if (shouldThrow) {
        return HttpResponse.json({ error: "Not found" }, { status: 400 });
      }

      return HttpResponse.json({ word, translation });
    }),
    http.delete(endpoint("/api/flashcards"), async (req) => {
      const { word, translation } = await req.request.json();

      mapTransient.update((m) => {
        if (m.has(word)) {
          m.delete(word);
        }

        return m;
      });

      return HttpResponse.json({ word, translation });
    }),
    http.get(endpoint("/api/flashcards/export"), async () => {
      const headers = { "Content-Type": "text/csv" };
      const csv = "word,translation\njohn,jacek\nkoeln,kolonia\nsoap,mydlo\n";
      return HttpResponse.text(csv, { headers });
    })
  ];
};

export {
  getAllReturn,
  mapTransient,
  flashcardControllerHandlers
};