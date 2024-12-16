import API from "./API";

class LemmaAPI extends API {
  async getOne({ name, language }) {
    return await this.request(
      `GET:/api/lemmas/${name}?language=${language.id}`,
    );
  }

  async upsert(payload) {
    return await this.request(`POST:/api/lemmas`, payload);
  }
}

export default LemmaAPI;
