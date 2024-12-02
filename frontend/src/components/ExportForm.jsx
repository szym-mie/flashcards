import { DialogPanel, useClose } from "@headlessui/react";
import { X, ClipboardCopy, Download } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import { useContext, useEffect, useState } from "react";
import FlashcardContext from "../context/FlashcardContext";

const ExportForm = () => {
  const close = useClose();
  const {flashcards} = useContext(FlashcardContext);
  const [CSVBody, setCSVBody] = useState("");

  useEffect(() => {
    fetch("/api/flashcards/export", { method: "GET" })
      .then(res => res.text())
      .then(text => { setCSVBody(text); });
  }, [flashcards, setCSVBody]);

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
            <pre className="bg-slate-200 border rounded-xl p-4 max-h-60 overflow-y-scroll">{CSVBody}</pre>
            <div className="mt-6 flex justify-end gap-2">
              <Button icon={ClipboardCopy} text={"Kopiuj"} />
              <Button icon={Download} text={"Pobierz"} />
            </div>
          </div>
        </DialogPanel>
      </div>
    </div>
  );
}

export default ExportForm;