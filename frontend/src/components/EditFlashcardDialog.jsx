import { Dialog, DialogPanel } from "@headlessui/react";
import { Pen, X } from "lucide-react";
import { useState } from "react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import Input from "~/components/Input";
import FormField from "~/components/FormField";
import Textarea from "~/components/Textarea";

const EditFlashcardDialog = ({ word, translation }) => {
  const [isOpen, setIsOpen] = useState(false);

  const open = () => {
    setIsOpen(true);
  };

  const close = () => {
    setIsOpen(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const word = formData.get("word");
    const translation = formData.get("translation");

    console.log(word, translation);
  };

  return (
    <>
      <IconButton icon={Pen} variant="secondary" onClick={open} />
      <Dialog
        open={isOpen}
        as="div"
        className="relative z-10 focus:outline-none"
        onClose={close}
      >
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
                  <Input name="word" defaultValue={word} />
                </FormField>
                <FormField label="Tłumaczenie" className="mt-4">
                  <Textarea name="translation" defaultValue={translation} />
                </FormField>
                <div className="mt-6 flex justify-end gap-2">
                  <Button text="Anuluj" variant="secondary" onClick={close} />
                  <Button type="submit" text="Zapisz" />
                </div>
              </form>
            </DialogPanel>
          </div>
        </div>
      </Dialog>
    </>
  );
};

export default EditFlashcardDialog;
