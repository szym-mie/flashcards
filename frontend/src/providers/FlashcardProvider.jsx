import { createContext, useContext, useEffect, useState } from "react";
import * as api from "~/api/flashcards";

const FlashcardContext = createContext(null);

const FlashcardProvider = ({ children }) => {
  const [flashcards, setFlashcards] = useState([]);

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

  return (
    <FlashcardContext.Provider
      value={{ flashcards, addFlashcards, updateFlashcard, removeFlashcard }}
    >
      {children({ flashcards })}
    </FlashcardContext.Provider>
  );
};

export const useFlashcards = () => useContext(FlashcardContext);

export default FlashcardProvider;
