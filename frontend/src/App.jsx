import Breadcrumb from "~/components/Breadcrumb";
import CreateFlashcardForm from "~/components/CreateFlashcardForm";
import EditFlashcardForm from "~/components/EditFlashcardForm";

import { Pen, Plus } from "lucide-react";

const flashcardsMockup = [
  {
    word: "Pies",
    translation: "Dog",
  },
  {
    word: "konstantynopolitańczykowianeczka",
    translation: "",
  },
  {
    word: "ayeee",
    translation: "tak jest"
  },
  {
    word: "привет",
    translation: "siema"
  },
  {
    word: "Peter",
    translation: "Piotr"
  },
  {
    word: "Quark",
    translation: "twaróg"
  },
  {
    word: "Polen",
    translation: "Polska"
  },
  {
    word: "spirit",
    translation: "spirytus"
  },
  {
    word: "Meister",
    translation: "mistrz"
  }
];

const App = () => {
  const getTranslationText = text => (text !== "" ? text : <i>Pusty</i>);

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
          <div className="flex flex-wrap justify-center items-stretch gap-3 mt-8 w-full max-w-[800px]">
            {flashcardsMockup.map(({word, translation}) => (
              <Breadcrumb 
                key={word} 
                mainText={word} 
                noteText={getTranslationText(translation)}
                icon={Pen}
                variant="secondary"
              >
                <EditFlashcardForm word={word} translation={translation}/>
              </Breadcrumb>
            ))}
            <Breadcrumb 
              key="___create___" 
              mainText={<i>Dodaj...</i>}
              noteText="fiszkę lub tekst"
              icon={Plus}
              variant="primary"
            >
              <CreateFlashcardForm/>
            </Breadcrumb>
          </div>
        </>
      )}
      <div className="flex-1" />
    </div>
  );
}

export default App;
