import { DialogPanel, useClose } from "@headlessui/react";
import { X } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import Input from "~/components/Input";
import FormField from "~/components/FormField";
import Textarea from "~/components/Textarea";
import { useFlashcards } from "~/providers/FlashcardProvider";

const EditFlashcardForm = ({ word, translation }) => {
  const close = useClose();
  const { updateFlashcard, removeFlashcard } = useFlashcards();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const translation = formData.get("translation");

    await updateFlashcard(word, { text: translation });

    close();
  };

  const handleRemove = async () => {
    await removeFlashcard(word);

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
            <h3>Edytuj fiszke</h3>
            <IconButton variant="secondary" icon={X} onClick={close} />
          </header>
          <form onSubmit={handleSubmit}>
            <FormField label="Słowo" className="mt-6">
              <Input name="word" defaultValue={word} disabled />
            </FormField>
            <FormField label="Tłumaczenie" className="mt-4">
              <Textarea name="translation" defaultValue={translation} />
            </FormField>
            <div className="mt-6 flex justify-end gap-2">
              <Button text="Anuluj" variant="secondary" onClick={close} />
              <Button variant="secondary" text="Usuń" onClick={handleRemove} />
              <Button type="submit" text="Zapisz" />
            </div>
          </form>
        </DialogPanel>
      </div>
    </div>
  );
};

export default EditFlashcardForm;
