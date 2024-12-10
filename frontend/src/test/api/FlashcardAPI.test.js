import { expect, describe, it, beforeAll, afterAll, afterEach } from "vitest";
import FlashcardAPI from "../../api/FlashcardAPI";
import { flashcardControllerServer } from "./mocks/node";
import { mapTransient, getAllReturn } from "./mocks/handlers";

const host = "http://localhost:8080";

const server = flashcardControllerServer(host)
const api = new FlashcardAPI(host);

// these tests only confirm FlashcardAPI is working properly

beforeAll(() => server.listen({ onUnhandledRequest: "error" }));
afterAll(() => server.close());
afterEach(() => server.resetHandlers());

describe("FlashcardAPI suite", () => {
  it("getAll", async () => {
    const actual = await api.getAll();

    expect(actual).toStrictEqual(getAllReturn);
  });

  it("add non-overlaping", async () => {
    const actual = await api.add({ text: "a b c", direction: "LTR" });
    const remote = Object.fromEntries(mapTransient.retrieve());

    expect(actual).toStrictEqual(remote);
  });

  it("add overlaping", async () => {
    mapTransient.update(() => new Map([
      ["a", { word: "a", translation: "" }],
      ["b", { word: "b", translation: "" }]
    ]));

    const actual = await api.add({ text: "a b c", direction: "LTR" });
    const remoteMap = new Map([["c", { word: "c", translation: "" }]]);
    const remote = Object.fromEntries(remoteMap);

    expect(actual).toStrictEqual(remote);
    mapTransient.reset();
  });

  it("update existing", async () => {
    mapTransient.update(() => new Map([
      ["a", { word: "a", translation: "" }],
      ["b", { word: "b", translation: "" }]
    ]));

    const actual = await api.update({ word: "a", translation: "A" });
    const remoteMap = mapTransient.retrieve();
    const remote = remoteMap.get("a");

    expect(actual).toStrictEqual(remote);
  });

  it("update non-existing", async () => {
    mapTransient.update(() => new Map([
      ["a", { word: "a", translation: "" }],
      ["b", { word: "b", translation: "" }]
    ]));

    const actual = await api.update({ word: "c", translation: "C" });

    expect(actual).toStrictEqual({ error: "Not found" });
    mapTransient.reset();
  });

  it("remove", async () => {
    const removedElement = { word: "a", translation: "A" };
    const persistElement = { word: "b", translation: "B" };

    mapTransient.update(() => new Map([
      ["a", removedElement],
      ["b", persistElement]
    ]));

    const actual = await api.remove(removedElement);

    const remoteMap = mapTransient.retrieve();
    const remote = Object.fromEntries([...remoteMap]);

    expect(actual).toStrictEqual(removedElement);
    expect(remote).toStrictEqual({ "b": persistElement });
  })
});
