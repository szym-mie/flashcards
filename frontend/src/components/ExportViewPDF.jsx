import { useEffect } from "react";
import { Download, BookOpen } from "lucide-react";
import Button from "~/components/Button";
import { useFlashcards } from "../context/FlashcardContext";

const ExportViewPDF = () => {
  const { exportedPDF, exportFlashcardsToPDF } = useFlashcards();

  useEffect(() => {
    exportFlashcardsToPDF();
  }, []);

  const createPDFBlobURL = () => {
    const blob = new Blob([exportedPDF], { type: "application/pdf" });
    return URL.createObjectURL(blob);
  };

  const withPDF = (action) => {
    const url = createPDFBlobURL();
    action(url);
    URL.revokeObjectURL(url);
  };

  const viewInNewTab = () => {
    withPDF((url) => window.open(url, "_blank").focus());
  };

  const downloadPDF = () => {
    withPDF((url) => {
      const a = document.createElement("a");
      a.href = url;
      a.download = "flashcards.pdf";
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  };

  return (
    <div>
      <div>
        <object className="w-full" data={createPDFBlobURL()} type="application/pdf"></object>
      </div>
      <div className="mt-6 flex justify-end gap-2">
        <Button icon={BookOpen} text="Podgląd" onClick={viewInNewTab} />
        <Button icon={Download} text="Pobierz" onClick={downloadPDF} />
      </div>
    </div>
  );
};

export default ExportViewPDF;
