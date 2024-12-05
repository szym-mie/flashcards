import Variant from "../Variant";

const Button = ({
  type = "button",
  variant = "primary",
  icon = null,
  text,
  ...props
}) => {
  const Icon = icon;

  return (
    <button
      type={type}
      className={`px-4 py-2 rounded-lg text-[15px] font-medium ${Variant.of(variant, "button")}`}
      {...props}
    >
      <div className="flex flex-row gap-4">
        {icon !== null ? <Icon /> : <></>}
        {text}
      </div>
    </button>
  );
};

export default Button;
