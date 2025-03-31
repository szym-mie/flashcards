import { useEffect, useState } from "react";
import { FlashcardContext } from "../context/FlashcardContext";
import FlashcardAPI from "../api/FlashcardAPI";
import LanguageAPI from "../api/LanguageAPI";
import LemmaAPI from "../api/LemmaAPI";
import SpeechAPI from "../api/SpeechAPI";

const flashcardApi = new FlashcardAPI("http://localhost:8080");
const languageApi = new LanguageAPI("http://localhost:8080");
const lemmaApi = new LemmaAPI("http://localhost:8080");
const speechApi = new SpeechAPI("http://localhost:8080");

const FlashcardProvider = ({ children }) => {
  const [sentence, setSentence] = useState(null);
  const [languages, setLanguages] = useState([]);
  const [flashcards, setFlashcards] = useState([]);
  const [partsOfSpeech, setPartsOfSpeech] = useState([]);
  const [exportedCSV, setExportedCSV] = useState("");
  const [exportedPDF, setExportedPDF] = useState();

  useEffect(() => {
    revalidateFlashcards();
    revalidateSentence();
    revalidateLanguages();
    revalidateSpeech();
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
    revalidateSentence();
  };

  const removeFlashcard = async (flashcard) => {
    await flashcardApi.remove(flashcard);
    revalidateFlashcards();
  };

  const exportFlashcardsToCSV = async () => {
    const exportCSV = await flashcardApi.exportCSV();
    setExportedCSV(exportCSV);
  };

  const exportFlashcardsToPDF = async () => {
    const exportPDF = await flashcardApi.exportPDF();
    setExportedPDF(exportPDF);
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

  const revalidateSpeech = async () => {
    const partOfSpeech = await speechApi.getAll();
    setPartsOfSpeech(partOfSpeech);
  };

  const getPartOfSentence = async (name) => {
    return await speechApi.getPartOfSentence(name);
  };

  return (
    <FlashcardContext.Provider
      value={{
        sentence,
        flashcards,
        exportedCSV,
        exportedPDF,
        createFlashcards,
        updateFlashcard,
        removeFlashcard,
        exportFlashcardsToCSV,
        exportFlashcardsToPDF,
        languages,
        addLanguage,
        getLemma,
        upsertLemma,
        partsOfSpeech,
        getPartOfSentence,
      }}
    >
      {children}
    </FlashcardContext.Provider>
  );
};

export default FlashcardProvider;
