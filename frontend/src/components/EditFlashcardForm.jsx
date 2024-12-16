import { DialogPanel, useClose } from "@headlessui/react";
import { X } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import Input from "~/components/Input";
import FormField from "~/components/FormField";
import { useFlashcards } from "../context/FlashcardContext";
import { useState } from "react";

const EditFlashcardForm = ({
  word,
  lemma,
  translation: _translation,
  partOfSpeech,
  transcription,
}) => {
  const close = useClose();
  const [translation, setTranslation] = useState(_translation);
  const { sentence, updateFlashcard, removeFlashcard, getLemma, upsertLemma } =
    useFlashcards();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const lemma = formData.get("lemma");
    const translation = formData.get("translation");
    const partOfSpeech = formData.get("partOfSpeech");
    const transcription = formData.get("transcription");

    await updateFlashcard({
      word,
      lemma,
      translation,
      partOfSpeech,
      transcription,
    });
    await upsertLemma({
      name: lemma,
      translation,
      language: sentence.language,
    });

    close();
  };

  const handleRemove = async () => {
    await removeFlashcard({ word, translation });
    close();
  };

  const handleLemmaChange = async (event) => {
    const lemma = await getLemma({
      name: event.target.value,
      language: sentence.language,
    });

    if (lemma.translation) {
      setTranslation(lemma.translation);
    }
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
            <FormField label="Forma bazowa" className="mt-4">
              <Input
                name="lemma"
                defaultValue={lemma}
                onChange={handleLemmaChange}
              />
            </FormField>
            <FormField label="Tłumaczenie" className="mt-4">
              <Input
                name="translation"
                value={translation}
                onChange={(event) => setTranslation(event.target.value)}
              />
            </FormField>
            <FormField label="Część mowy" className="mt-4">
              <Input name="partOfSpeech" defaultValue={partOfSpeech} />
            </FormField>
            <FormField label="Transkrypcja" className="mt-4">
              <Input name="transcription" defaultValue={transcription} />
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
