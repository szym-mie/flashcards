import { useFlashcards } from "../context/FlashcardContext";

const Phrases = () => {
  const { sentence, flashcards } = useFlashcards();

  if (!sentence) return;

  const findFlashcard = (word) => flashcards.find((flashcard) => flashcard.word === word.replace(/[,!?.'":;]/g, ""));

  return (
    <div className="mt-8">
      {sentence.phrases.map((phrase, index) => (
        <div key={index} className="mt-4 flex gap-1.5 text-nowrap">
          {phrase.text.split(" ").map((word, index) => (
            <div key={index} className={phrase.type === "SUBORDINATE" ? "pl-4" : ""}>
              <div className="font-medium">{word}</div>
              <div className="muted font-medium text-sm">{findFlashcard(word)?.translation || "-"}</div>
              <div className="muted font-medium text-sm">{findFlashcard(word)?.transcription || "-"}</div>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
};

export default Phrases;
