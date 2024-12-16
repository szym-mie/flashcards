import { useFlashcards } from "../context/FlashcardContext";

const Sentence = () => {
  const { sentence, flashcards } = useFlashcards();

  if (!sentence) return;

  const sentenceWords = sentence.text.split(" ");

  const findFlashcard = (word) =>
    flashcards.find((flashcard) => flashcard.word === word);

  return (
    <div className="mt-8 flex gap-1.5 text-nowrap">
      {sentenceWords.map((word, index) => (
        <div key={index}>
          <div className="font-medium">{word}</div>
          <div className="muted font-medium text-sm">
            {findFlashcard(word)?.translation || "-"}
          </div>
          <div className="muted font-medium text-sm">
            {findFlashcard(word)?.transcription || "-"}
          </div>
        </div>
      ))}
    </div>
  );
};

export default Sentence;
