import API from "./API";

class FlashcardAPI extends API {
  async getAll() {
    return await this.request("GET:/api/flashcards");
  }

  async getSentence() {
    return await this.request("GET:/api/flashcards/sentence");
  }

  async create(payload) {
    return await this.request("POST:/api/flashcards", payload);
  }

  async update(flashcard) {
    return await this.request("PUT:/api/flashcards", flashcard);
  }

  async remove(flashcard) {
    return await this.request("DELETE:/api/flashcards", flashcard);
  }

  async exportCSV() {
    return await this.request("GET:/api/flashcards/export_csv", null, "text/csv");
  }

  async exportPDF() {
    return await this.request("GET:/api/flashcards/export_pdf", null, "application/octet-stream");
  }
}

export default FlashcardAPI;
