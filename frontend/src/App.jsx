import { Pen, Plus } from "lucide-react";
import Breadcrumb from "~/components/Breadcrumb";
import CreateFlashcardForm from "~/components/CreateFlashcardForm";
import EditFlashcardForm from "~/components/EditFlashcardForm";
import FlashcardProvider from "~/providers/FlashcardProvider";

const App = () => {
  const getTranslationText = (text) => (text !== "" ? text : <i>Pusty</i>);

  return (
    <FlashcardProvider>
      {({ flashcards }) => (
        <div className="py-24 px-4 flex flex-col items-center min-h-screen">
          <div className="flex-1" />
          {!flashcards.length ? (
            <>
              <header className="mb-8 text-center">
                <h1>Brak fiszek</h1>
                <p className="mt-2 muted">
                  Nie posiadasz żadnych fiszek, kliknij przycisk poniżej aby
                  dodać nowe.
                </p>
              </header>
              <Breadcrumb
                mainText={<i>Dodaj...</i>}
                noteText="Nowy tekst"
                icon={Plus}
                variant="primary"
              >
                <CreateFlashcardForm />
              </Breadcrumb>
            </>
          ) : (
            <>
              <header className="text-center">
                <h1>Twoje fiszki</h1>
                <p className="mt-2 muted">
                  Lista twoich wszystkich ({flashcards.length}) fiszek.
                </p>
              </header>
              <div className="flex flex-wrap justify-center items-stretch gap-3 mt-8 w-full max-w-[800px]">
                {flashcards.map(({ word, translation }) => (
                  <Breadcrumb
                    key={word}
                    mainText={word}
                    noteText={getTranslationText(translation)}
                    icon={Pen}
                    variant="secondary"
                  >
                    <EditFlashcardForm word={word} translation={translation} />
                  </Breadcrumb>
                ))}
                <Breadcrumb
                  mainText={<i>Dodaj...</i>}
                  noteText="fiszkę lub tekst"
                  icon={Plus}
                  variant="primary"
                >
                  <CreateFlashcardForm />
                </Breadcrumb>
              </div>
            </>
          )}
          <div className="flex-1" />
        </div>
      )}
    </FlashcardProvider>
  );
};

export default App;
