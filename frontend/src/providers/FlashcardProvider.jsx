import { useEffect, useState } from "react";
import { FlashcardContext } from "../context/FlashcardContext";
import FlashcardAPI from "../api/FlashcardAPI";

const api = new FlashcardAPI("http://localhost:8080");

const FlashcardProvider = ({ children }) => {
  const [flashcards, setFlashcards] = useState([]);
  const [exportedCSV, setExportedCSV] = useState("");
  
  useEffect(() => {
    revalidate();
  }, []);

  const revalidate = async () => {
    const flashcards = await api.getAll();
    setFlashcards(flashcards)
  };

  const addFlashcards = async ({ text, direction }) => {
    await api.add({ text, direction });
    revalidate()
  };

  const updateFlashcard = async (flashcard) => {
    await api.update(flashcard);
    revalidate()
  };

  const removeFlashcard = async (flashcard) => {
    await api.remove(flashcard);
    revalidate()
  };

  const exportFlashcards = async () => {
    const exportCSV = await api.export();
    setExportedCSV(exportCSV);
  };

  return (
    <FlashcardContext.Provider
      value={{
        flashcards,
        exportedCSV,
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
