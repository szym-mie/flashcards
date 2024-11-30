import { Dialog } from "@headlessui/react";
import { useState } from "react";
import Icon from "./Icon";
import Variant from "../Variant";


const Breadcrumb = ({ mainText, noteText, icon, variant, children }) => {
  const [open, setOpen] = useState(false);

  const openDialog = () => {
    setOpen(true);
  };

  const closeDialog = () => {
    setOpen(false);
  }

  return (
    <>
      <div 
        onClick={openDialog}
        className={"hover:underline cursor-pointer px-2 py-2 flex gap-4 items-center justify-between rounded-2xl " + Variant.of(variant, "outer")}
      >
        <div>
          <p className="truncate">{mainText}</p> 
          <p className="truncate muted text-xs">{noteText}</p>
        </div>
        <div className="flex gap-2 items-center">
          <Icon icon={icon} variant={variant} variantType="solid"/>
        </div>
      </div>
      <Dialog open={open} onClose={closeDialog}>
        {children}
      </Dialog>
    </>
  );
};

export default Breadcrumb;
