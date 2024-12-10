import { createContext, useContext } from "react";

const FlashcardContext = createContext(null);
const useFlashcards = () => useContext(FlashcardContext);

export { useFlashcards, FlashcardContext };