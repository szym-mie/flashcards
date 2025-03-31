import { FileDown } from "lucide-react";
import Breadcrumb from "~/components/Breadcrumb";
import ExportForm from "~/components/ExportForm";
import FlashcardProvider from "~/providers/FlashcardProvider";
import FlashcardList from "./components/FlashcardList";

const App = () => {
  return (
    <FlashcardProvider>
      <div className="py-24 px-4 flex flex-col items-center min-h-screen">
        <div className="flex-1" />
        <FlashcardList />
        <div className="flex-1" />
        <div className="h-4"></div>
        <Breadcrumb mainText={<i>Eksportuj...</i>} noteText="do pliku CSV/PDF" icon={FileDown} variant="primary">
          <ExportForm />
        </Breadcrumb>
      </div>
    </FlashcardProvider>
  );
};

export default App;
