import { DialogPanel, useClose } from "@headlessui/react";
import { X } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import FormField from "~/components/FormField";
import Textarea from "~/components/Textarea";
import { useContext } from "react";
import FlashcardContext from "../context/FlashcardContext";


const CreateFlashcardForm = () => {
  const close = useClose();
  const {update, setUpdate} = useContext(FlashcardContext);

  const handleSubmit = ev => {
    ev.preventDefault();
    const formData = new FormData(ev.target);
    const text = formData.get("text");
    const direction = "LTR";

    fetch("/api/flashcards", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ text, direction }) })
      .then(status => {
        console.log(status.text());
        setUpdate(update + 1);
        close();
      });

    console.log(text);
  };

  return (
    <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
      <div className="flex min-h-full items-center justify-center p-4 bg-black/65">
        <DialogPanel
          transition
          className="w-full max-w-md rounded-xl bg-white p-6 backdrop-blur-2xl duration-300 ease-out data-[closed]:transform-[scale(95%)] data-[closed]:opacity-0"
        >
          <header className="flex justify-between">
            <h3>Stwórz fiszke</h3>
            <IconButton variant="secondary" icon={X} onClick={close} />
          </header>
          <form onSubmit={handleSubmit}>
            <FormField label="Zawartość" className="mt-4">
              <Textarea name="text" />
            </FormField>
            <div className="mt-6 flex justify-end gap-2">
              <Button text="Anuluj" variant="secondary" onClick={close} />
              <Button type="submit" text="Zapisz" />
            </div>
          </form>
        </DialogPanel>
      </div>
    </div>
  );
};

export default CreateFlashcardForm;
