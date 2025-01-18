import { DialogPanel, useClose } from "@headlessui/react";
import { X } from "lucide-react";
import IconButton from "~/components/IconButton";
import Button from "~/components/Button";
import Input from "~/components/Input";
import FormField from "~/components/FormField";
import { useForm } from "react-hook-form";
import { useFlashcards } from "../context/FlashcardContext";
import { useState } from "react";

const EditFlashcardForm = (params) => {
  const close = useClose();
  const [translation, setTranslation] = useState(params.translation);
  const { register, handleSubmit, getValues } = useForm({
    defaultValues: {
      word: params.word,
      lemma: params.lemma,
      partOfSpeech: params.partOfSpeech,
      transcription: params.transcription,
    },
    values: {
      translation: translation,
    },
  });
  const { sentence, updateFlashcard, removeFlashcard, getLemma, upsertLemma } = useFlashcards();

  const onSubmit = async (data) => {
    await updateFlashcard({
      word: params.word,
      lemma: data.lemma,
      translation: data.translation,
      partOfSpeech: data.partOfSpeech,
      transcription: data.transcription,
    });
    await upsertLemma({
      name: data.lemma,
      translation: translation,
      language: sentence.language,
    });

    close();
  };

  const onRemove = async () => {
    await removeFlashcard({
      word: params.word,
      lemma: params.lemma,
      translation: translation,
      partOfSpeech: params.partOfSpeech,
      transcription: params.transcription,
    });

    close();
  };

  const onLemmaChange = async () => {
    const lemma = await getLemma({
      name: getValues("lemma"),
      language: sentence.language,
    });

    if (lemma.translation) {
      setTranslation(lemma.translation);
    }
  };

  const onTranslationChange = () => {
    setTranslation(getValues("translation"));
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
          <form onSubmit={handleSubmit(onSubmit)}>
            <FormField label="Słowo" className="mt-6">
              <Input {...register("word", { disabled: true })} />
            </FormField>
            <FormField label="Forma bazowa" className="mt-4">
              <Input {...register("lemma", { onChange: onLemmaChange })} />
            </FormField>
            <FormField label="Tłumaczenie" className="mt-4">
              <Input {...register("translation", { onChange: onTranslationChange })} />
            </FormField>
            <FormField label="Część mowy" className="mt-4">
              <Input {...register("partOfSpeech")} />
            </FormField>
            <FormField label="Transkrypcja" className="mt-4">
              <Input {...register("transcription")} />
            </FormField>
            <div className="mt-6 flex justify-end gap-2">
              <Button text="Anuluj" variant="secondary" onClick={close} />
              <Button variant="secondary" text="Usuń" onClick={onRemove} />
              <Button type="submit" text="Zapisz" />
            </div>
          </form>
        </DialogPanel>
      </div>
    </div>
  );
};

export default EditFlashcardForm;
