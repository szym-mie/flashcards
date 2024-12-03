class Variant {
  static iconClasses = {
    primary:
      "w-12 h-12 text-white bg-black shadow-[0_2px_4px_0px_rgba(0,0,0,0.3)]",
    secondary:
      "p-1.5 text-black bg-slate-100 [&>svg]:w-[15px] [&>svg]:h-[15px]",
  };

  static solidClasses = {
    primary: "text-white bg-black shadow-[0_2px_4px_0px_rgba(0,0,0,0.3)]",
    secondary:
      "p-1.5 text-black bg-slate-100 [&>svg]:w-[15px] [&>svg]:h-[15px]",
  };

  static outerClasses = {
    primary:
      "text-white bg-black shadow-[0_2px_4px_0px_rgba(0,0,0,0.3)] [&>div>p.muted]:text-[#acacac]",
    secondary: " border p-1.5",
  };

  static buttonClasses = {
    primary: "bg-black text-white",
    secondary: "bg-slate-100 text-black",
  };

  static classes = {
    icon: Variant.iconClasses,
    solid: Variant.solidClasses,
    outer: Variant.outerClasses,
    button: Variant.buttonClasses,
  };

  static defaultClass = (variantClass) => variantClass.secondary;

  /**
   *
   * @param {string} variantName Select variant (primary/secondary)
   * @param {string} classType Specify to get only one class type
   * @returns {string} Variant class string
   */
  static of(variantName, classType) {
    const typeVariantClass = Variant.classes[classType];
    if (typeVariantClass === undefined)
      throw new Error("Unknown class type " + classType);

    const maybeVariantClass = typeVariantClass[variantName];
    if (maybeVariantClass === undefined)
      return Variant.defaultClass(typeVariantClass);

    return maybeVariantClass;
  }
}

export default Variant;
