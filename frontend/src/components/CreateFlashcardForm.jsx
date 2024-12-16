import { Dialog, DialogPanel, useClose } from "@headlessui/react";
import { Plus, X } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import FormField from "~/components/FormField";
import Textarea from "~/components/Textarea";
import Select from "~/components/Select";
import { useFlashcards } from "../context/FlashcardContext";
import { useState } from "react";
import CreateLanguageForm from "./CreateLanguageForm";

const CreateFlashcardForm = () => {
  const close = useClose();
  const { sentence, addFlashcards, languages } = useFlashcards();
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const text = formData.get("text");
    const language = formData.get("language");

    await addFlashcards({
      text,
      language: languages.find(({ id }) => id === language),
    });

    close();
  };

  return (
    <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
      <div className="flex min-h-full items-center justify-center p-4 bg-black/65">
        <DialogPanel
          transition
          className="w-full max-w-md rounded-xl bg-white p-6 backdrop-blur-2xl duration-300 ease-out data-[closed]:transform-[scale(95%)] data-[closed]:opacity-0"
        >
          <header className="flex justify-between">
            <h3>Stwórz fiszki</h3>
            <IconButton variant="secondary" icon={X} onClick={close} />
          </header>
          <form onSubmit={handleSubmit}>
            <FormField label="Język" className="mt-4">
              <div className="flex items-center gap-4">
                <IconButton
                  variant="secondary"
                  icon={Plus}
                  onClick={() => setIsDialogOpen(true)}
                />
                <Select
                  name="language"
                  items={languages}
                  defaultValue={sentence?.language?.id}
                />
              </div>
            </FormField>
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
      <Dialog open={isDialogOpen} onClose={setIsDialogOpen}>
        <CreateLanguageForm />
      </Dialog>
    </div>
  );
};

export default CreateFlashcardForm;
