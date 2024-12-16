import API from "./API";

class LanguageAPI extends API {
  async getAll() {
    return await this.request("GET:/api/languages");
  }

  async add(payload) {
    return await this.request("POST:/api/languages", payload);
  }
}

export default LanguageAPI;
