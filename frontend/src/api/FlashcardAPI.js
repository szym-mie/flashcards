import API from "./API";

class FlashcardAPI extends API {
  async getAll() {
    return await this.request("GET:/api/flashcards");
  }

  async add(payload) {
    return await this.request("POST:/api/flashcards", payload);
  }

  async update(flashcard) {
    return await this.request("PUT:/api/flashcards", flashcard);
  }

  async remove(flashcard) {
    return await this.request("DELETE:/api/flashcards", flashcard);
  }

  async export() {
    return await this.request("GET:/api/flashcards/export");
  }
}

export default FlashcardAPI;
