import Flashcard from "~/components/Flashcard";
import CreateFlashcardDialog from "~/components/CreateFlashcardDialog";

const flashcardsMockup = [
  {
    word: "Pies",
    translation: "Dog",
  },
  {
    word: "konstantynopolitańczykowianeczka",
    translation: "",
  },
];

function App() {
  return (
    <div className="py-24 px-4 flex flex-col items-center min-h-screen">
      <div className="flex-1" />
      {!flashcardsMockup.length ? (
        <header className="text-center">
          <h1>Brak fiszek</h1>
          <p className="mt-2 muted">
            Nie posiadasz żadnych fiszek, kliknij przycisk poniżej aby dodać
            nowe.
          </p>
        </header>
      ) : (
        <>
          <header className="text-center">
            <h1>Twoje fiszki</h1>
            <p className="mt-2 muted">Lista twoich wszystkich (2) fiszek.</p>
          </header>
          <div className="flex flex-col gap-y-3 mt-8 w-full max-w-[464px]">
            {flashcardsMockup.map((flashcard) => (
              <Flashcard key={flashcard.word} {...flashcard} />
            ))}
          </div>
        </>
      )}
      <CreateFlashcardDialog className="mt-12" />
      <div className="flex-1" />
    </div>
  );
}

export default App;
