import Variant from "../Variant";


const Icon = ({ icon, variant, variantType="icon" }) => {
  const IconComponent = icon;

  return (
    <div
      className={"flex items-center justify-center rounded-full hover:opacity-80 transition-opacity " + Variant.of(variant, variantType)}
    >
      <IconComponent />
    </div>
  );
};
  
export default Icon;