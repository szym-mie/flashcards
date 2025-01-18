import { useEffect, useState } from "react";
import { FlashcardContext } from "../context/FlashcardContext";
import FlashcardAPI from "../api/FlashcardAPI";
import LanguageAPI from "../api/LanguageAPI";
import LemmaAPI from "../api/LemmaAPI";

const flashcardApi = new FlashcardAPI("http://localhost:8080");
const languageApi = new LanguageAPI("http://localhost:8080");
const lemmaApi = new LemmaAPI("http://localhost:8080");

const FlashcardProvider = ({ children }) => {
  const [sentence, setSentence] = useState(null);
  const [languages, setLanguages] = useState([]);
  const [flashcards, setFlashcards] = useState([]);
  const [exportedCSV, setExportedCSV] = useState("");

  useEffect(() => {
    revalidateFlashcards();
    revalidateSentence();
    revalidateLanguages();
  }, []);

  const revalidateFlashcards = async () => {
    const flashcards = await flashcardApi.getAll();
    setFlashcards(flashcards);
  };

  const revalidateSentence = async () => {
    const sentence = await flashcardApi.getSentence();
    setSentence(sentence);
  };

  const createFlashcards = async (payload) => {
    await flashcardApi.create(payload);
    revalidateFlashcards();
    revalidateSentence();
  };

  const updateFlashcard = async (flashcard) => {
    await flashcardApi.update(flashcard);
    revalidateFlashcards();
  };

  const removeFlashcard = async (flashcard) => {
    await flashcardApi.remove(flashcard);
    revalidateFlashcards();
  };

  const exportFlashcards = async () => {
    const exportCSV = await flashcardApi.export();
    setExportedCSV(exportCSV);
  };

  const revalidateLanguages = async () => {
    const languages = await languageApi.getAll();
    setLanguages(languages);
  };

  const addLanguage = async (payload) => {
    await languageApi.add(payload);
    revalidateLanguages();
  };

  const getLemma = async (payload) => {
    return await lemmaApi.getOne(payload);
  };

  const upsertLemma = async (payload) => {
    return await lemmaApi.upsert(payload);
  };

  return (
    <FlashcardContext.Provider
      value={{
        sentence,
        flashcards,
        exportedCSV,
        createFlashcards,
        updateFlashcard,
        removeFlashcard,
        exportFlashcards,
        languages,
        addLanguage,
        getLemma,
        upsertLemma,
      }}
    >
      {children}
    </FlashcardContext.Provider>
  );
};

export default FlashcardProvider;
