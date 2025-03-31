import API from "./API";

class SpeechAPI extends API {
  getPartOfSpeechMapping(value) {
    const PART_OF_SPEECH_MAP = {
      NOUN: "Rzeczownik",
      VERB: "Czasownik",
      ADJECTIVE: "Przymiotnik",
      ADVERB: "Przysłówek",
      NUMERAL: "Liczebnik",
      PRONOUN: "Zaimek",
      PREPOSITION: "Przyimek",
      CONJUNCTION: "Spójnik",
      PARTICLE: "Partykuła",
      INTERJECTION: "Wykrzyknik",
      ARTICLE: "Rodzajnik",
      OTHER: "Inne",
    };

    return {
      id: value ?? "OTHER",
      name: PART_OF_SPEECH_MAP[value] ?? "Nieznane",
    };
  }

  getPartOfSentenceMapping(value) {
    const PART_OF_SENTENCE_MAP = {
      SUBJECT: "Podmiot",
      PREDICATE: "Orzeczenie",
      OBJECT: "Dopełnienie",
      ADVERBIAL_MODIFIER: "Okolicznik",
      ATTRIBUTE: "Przydawka",
      COORDINATE: "Współrzędne",
      SUBORDINATE: "Podrzędne",
      OTHER: "Inne",
    };

    return {
      id: value ?? "OTHER",
      name: PART_OF_SENTENCE_MAP[value] ?? "Nieznane",
    };
  }

  async getAll() {
    const response = await this.request("GET:/api/speech");
    return response.map(this.getPartOfSpeechMapping);
  }

  async getPartOfSentence(name) {
    const response = await this.request(`GET:/api/speech/${name}`);
    return response.map(this.getPartOfSentenceMapping);
  }
}

export default SpeechAPI;
