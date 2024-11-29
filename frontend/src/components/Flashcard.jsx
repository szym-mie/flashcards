import { X } from "lucide-react";
import IconButton from "~/components/IconButton";
import EditFlashcardDialog from "~/components/EditFlashcardDialog";

const Flashcard = ({ word, translation, className = "", ...props }) => {
  return (
    <div
      className={`px-8 py-6 flex gap-4 items-center justify-between rounded-2xl border ${className}`}
      {...props}
    >
      <p className="truncate">{word}</p>
      <div className="-mr-3 flex gap-2 items-center">
        <EditFlashcardDialog word={word} translation={translation} />
        <IconButton icon={X} variant="secondary" />
      </div>
    </div>
  );
};

export default Flashcard;
