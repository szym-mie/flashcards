import { X } from "lucide-react";
import { DialogPanel, useClose, Tab, TabGroup, TabList, TabPanels, TabPanel } from "@headlessui/react";
import IconButton from "~/components/IconButton";
import ExportViewCSV from "./ExportViewCSV";
import ExportViewPDF from "./ExportViewPDF";

const ExportForm = () => {
  const close = useClose();

  const exporters = [
    {
      type: "CSV",
      exporter: <ExportViewCSV />,
    },
    {
      type: "PDF",
      exporter: <ExportViewPDF />,
    },
  ];

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
            <TabGroup>
              <TabList className="flex gap-4">
                {exporters.map(({ type }) => (
                  <Tab
                    key={type}
                    className="rounded-full py-1 px-3 text-sm/6 focus:outline-none data-[selected]:bg-slate-200 data-[hover]:bg-slate-300 data-[selected]:data-[hover]:bg-slate-400 data-[focus]:outline-1 data-[focus]:outline-slate-500"
                  >
                    {type}
                  </Tab>
                ))}
              </TabList>
              <TabPanels className="mt-3">
                {exporters.map(({ type, exporter }) => (
                  <TabPanel key={type} className="rounded-xl bg-white/5 p-3">
                    {exporter}
                  </TabPanel>
                ))}
              </TabPanels>
            </TabGroup>
          </div>
        </DialogPanel>
      </div>
    </div>
  );
};

export default ExportForm;
