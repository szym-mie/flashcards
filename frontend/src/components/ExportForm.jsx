import { X, ClipboardCopy, Download, Check } from "lucide-react";
import { DialogPanel, useClose } from "@headlessui/react";
import { useFlashcards } from "~/providers/FlashcardProvider";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import { useEffect, useState } from "react";

const ExportForm = () => {
  const close = useClose();
  const [copied, setCopied] = useState(false);
  const { exportCSV, exportFlashcards } = useFlashcards();

  useEffect(() => {
    exportFlashcards();
  }, [exportFlashcards]);

  const copyToClipboard = () => {
    navigator.clipboard.writeText(exportCSV);
    setCopied(true);
  };

  const downloadCSV = () => {
    const blob = new Blob([exportCSV], { type: "text/csv" });

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
    <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
      <div className="flex min-h-full items-center justify-center p-4 bg-black/65">
        <DialogPanel
          transition
          className="w-full max-w-md rounded-xl bg-white p-6 backdrop-blur-2xl duration-300 ease-out data-[closed]:transform-[scale(95%)] data-[closed]:opacity-0"
        >
          <header className="flex justify-between">
            <h3>Eksportuj fiszki</h3>
            <IconButton variant="secondary" icon={X} onClick={close} />
          </header>
          <div>
            <div className="h-4"></div>
            <pre className="bg-slate-200 border rounded-xl p-4 max-h-60 overflow-y-scroll">
              {exportCSV}
            </pre>
            <div className="mt-6 flex justify-end gap-2">
              <Button
                icon={copied ? Check : ClipboardCopy}
                text={copied ? "Skopiowane" : "Kopiuj"}
                onClick={copyToClipboard}
              />
              <Button icon={Download} text="Pobierz" onClick={downloadCSV} />
            </div>
          </div>
        </DialogPanel>
      </div>
    </div>
  );
};

export default ExportForm;
