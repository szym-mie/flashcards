/**
 * Base abstract class for API interaction
 * @abstract
 */
class API {
  /**
   * Construct API accessor to certain host
   * @param {string} target Target host URL
   */
  constructor(target) {
    this.target = target;
  }

  /**
   * Escape URL endpoint path
   * @private
   * @param {string} path Path to escape
   * @returns {string} Escaped path
   */
  escapePath(path) {
    const tokens = path.split("/");
    return tokens
      .filter((e) => e.length > 0)
      .map((e) => "/" + e)
      .reduce((a, e) => a + e, "");
  }

  /**
   * Try get Content-Type based on object
   * @param {any} object Any object or string
   * @returns {string|null} Content-Type from object
   */
  static objectToContentType(object) {
    if (object === null) return null;
    const objectClass = object.constructor;

    switch (objectClass) {
      case Object:
        return "application/json";
      default:
        return null;
    }
  }

  /**
   *
   */
  static objectToContentBody(object) {
    if (object === null) return null;
    const objectClass = object.constructor;

    switch (objectClass) {
      case Object:
        return JSON.stringify(object);
      default:
        return object;
    }
  }

  /**
   * Parse and extract response body to an object of Content-Type
   * @private
   * @param {Response} response Response to be parsed
   * @param {string} overrideContentType Override Content-Type detection
   * @returns {any} Object extracted from response
   */
  static async responseToObject(response, overrideContentType = null) {
    const maybeContentType = response.headers.get("Content-Type");
    const foundContentType = maybeContentType !== null ? maybeContentType : "text/html";
    const contentType = overrideContentType !== null ? overrideContentType : foundContentType;

    switch (contentType) {
      case "application/json":
        return await response.json();
      case "application/octet-stream":
        return await response.arrayBuffer();
      default:
        return await response.text();
    }
  }

  /**
   * Send a request to API
   * @private
   * @param {string} endpoint Endpoint specification eg. 'GET:/api/endpoint'
   * @param {any} [payload] Object for JSON, string for any other Content-Type
   * @param {string} [contentType] Override Content-Type to be used with string body type
   * @returns {Promise<any>} Object from response
   */
  async request(endpoint, payload = "", contentType = null) {
    const [method, path] = endpoint.split(":");
    const escapedPath = this.escapePath(path);

    const headers = {};

    const predictContentType = API.objectToContentType(payload);
    const body = API.objectToContentBody(payload);

    if (predictContentType !== null) {
      headers["Content-Type"] = predictContentType;
    }

    if (contentType !== null) {
      headers["Content-Type"] = contentType;
    }

    const hasBody = !(method == "GET" || method == "HEAD");
    const init = hasBody ? { method, headers, body } : { method, headers };

    const res = await fetch(this.target + escapedPath, init);
    return await API.responseToObject(res);
  }
}

export default API;
