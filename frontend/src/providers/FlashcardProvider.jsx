import { createContext, useContext, useEffect, useState } from "react";
import * as api from "~/api/flashcards";

const FlashcardContext = createContext(null);

const FlashcardProvider = ({ children }) => {
  const [flashcards, setFlashcards] = useState([]);
  const [exportCSV, setExportCSV] = useState(null);

  useEffect(() => {
    revalidate();
  }, []);

  const revalidate = async () => {
    const flashcards = await api.fetchFlashcards();
    setFlashcards(flashcards);
  };

  const addFlashcards = async (payload) => {
    await api.addFlashcards(payload);
    revalidate();
  };

  const updateFlashcard = async (word, payload) => {
    await api.updateFlashcard(word, payload);
    revalidate();
  };

  const removeFlashcard = async (word) => {
    await api.removeFlashcard(word);
    revalidate();
  };

  const exportFlashcards = async () => {
    const exportCSV = await api.exportFlashcards();
    setExportCSV(exportCSV);
  };

  return (
    <FlashcardContext.Provider
      value={{
        flashcards,
        exportCSV,
        addFlashcards,
        updateFlashcard,
        removeFlashcard,
        exportFlashcards,
      }}
    >
      {children({ flashcards })}
    </FlashcardContext.Provider>
  );
};

export const useFlashcards = () => useContext(FlashcardContext);

export default FlashcardProvider;
