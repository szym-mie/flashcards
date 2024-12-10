class Transient {
    /**
     * Creates a transient with default initial value, the value gets reset
     * each time `retrieve()` method is called
     * @param {function()} supplier Reset value supplier
     */
    constructor(supplier) {
        /**
         * Reset value supplier
         */
        this.supplier = supplier;
        /**
         * Internal reference holder
         * @private
         */
        this.value = null;

        this.reset();
    }

    /**
     * Reset this transient to initial value
     */
    reset() {
        this.value = this.supplier();
    }

    /**
     * Updates this transient based on previous value
     * @param {function(any)} mapper Mapper of values 
     * @returns This reference
     */
    update(mapper) {
        this.value = mapper(this.value)
        return this;
    }

    /**
     * Returns a value and resets the transient
     * @returns Final value of the transient
     */
    retrieve() {
        const value = this.value;
        this.reset();
        return value;
    }
}

export default Transient;