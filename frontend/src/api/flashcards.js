const API_BASE = "http://localhost:8080";
const API_BASE_FLASHCARDS = `${API_BASE}/api/flashcards`;

export const fetchFlashcards = async () => {
  const result = await fetch(API_BASE_FLASHCARDS, { method: "GET" });

  return await result.json();
};

export const addFlashcards = async (payload) => {
  return await fetch(API_BASE_FLASHCARDS, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
};

export const updateFlashcard = async (word, payload) => {
  return await fetch(`${API_BASE_FLASHCARDS}/${word}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
};

export const removeFlashcard = async (word) => {
  return await fetch(`${API_BASE_FLASHCARDS}/${word}`, { method: "DELETE" });
};

export const exportFlashcards = async () => {
  const result = await fetch(`${API_BASE_FLASHCARDS}/export`, {
    method: "GET",
  });

  return await result.text();
};
