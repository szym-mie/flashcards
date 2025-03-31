import { useEffect, useState } from "react";
import { ClipboardCopy, Download, Check } from "lucide-react";
import Button from "~/components/Button";
import { useFlashcards } from "../context/FlashcardContext";

const ExportViewCSV = () => {
  const [copied, setCopied] = useState(false);
  const { exportedCSV, exportFlashcardsToCSV } = useFlashcards();

  useEffect(() => {
    exportFlashcardsToCSV();
  }, [exportFlashcardsToCSV]);

  const copyToClipboard = () => {
    navigator.clipboard.writeText(exportedCSV);
    setCopied(true);
  };

  const downloadCSV = () => {
    const blob = new Blob([exportedCSV], { type: "text/csv" });

    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "data.csv";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  return (
    <div>
      <pre className="bg-slate-200 border rounded-xl p-4 max-h-60 overflow-y-scroll">{exportedCSV}</pre>
      <div className="mt-6 flex justify-end gap-2">
        <Button
          icon={copied ? Check : ClipboardCopy}
          text={copied ? "Skopiowane" : "Kopiuj"}
          onClick={copyToClipboard}
        />
        <Button icon={Download} text="Pobierz" onClick={downloadCSV} />
      </div>
    </div>
  );
};

export default ExportViewCSV;
