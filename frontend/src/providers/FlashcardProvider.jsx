import { useEffect, useState } from "react";
import { FlashcardContext } from "../context/FlashcardContext";
import FlashcardAPI from "../api/FlashcardAPI";

const api = new FlashcardAPI("http://localhost:8080");

const FlashcardProvider = ({ children }) => {
  const [flashcardMap, setFlashcardMap] = useState(new Map());
  const [exportedCSV, setExportedCSV] = useState("");
  
  useEffect(() => {
    getAllFlashcards();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const createMapFrom = (map) => new Map(map.entries());

  const getAllFlashcards = async () => {
    const newFlashcards = await api.getAll();
    setFlashcardMap(() => {
      const map = new Map();

      for (const flashcard of newFlashcards) {
        map.set(flashcard.word, flashcard);
      }

      return map;
    });
    console.log(flashcardMap);
  };

  const addFlashcards = async ({ text, direction }) => {
    const newFlashcards = await api.add({ text, direction });
    console.log(newFlashcards);
    setFlashcardMap((prevMap) => {
      const map = createMapFrom(prevMap);

      for (const flashcard of newFlashcards) {
        map.set(flashcard.word, flashcard);
      }

      console.log(prevMap);
      return map;
    });
  };

  const updateFlashcard = async (flashcard) => {
    const newFlashcard = await api.update(flashcard);
    setFlashcardMap((prevMap) => {
      const map = createMapFrom(prevMap);

      map.set(newFlashcard.word, newFlashcard);

      return map;
    });
  };

  const removeFlashcard = async (flashcard) => {
    await api.remove(flashcard);
    setFlashcardMap((prevMap) => {
      const map = createMapFrom(prevMap);

      map.delete(flashcard.word);

      return map;
    })
  };

  const exportFlashcards = async () => {
    const exportCSV = await api.export();
    setExportedCSV(exportCSV);
  };

  return (
    <FlashcardContext.Provider
      value={{
        flashcardMap,
        exportedCSV,
        getAllFlashcards,
        addFlashcards,
        updateFlashcard,
        removeFlashcard,
        exportFlashcards,
      }}
    >
      {children}
    </FlashcardContext.Provider>
  );
};

export default FlashcardProvider;
