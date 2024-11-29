const variantClassMap = {
  primary:
    "w-12 h-12 text-white bg-black shadow-[0_2px_4px_0px_rgba(0,0,0,0.3)]",
  secondary: "p-1.5 text-black bg-slate-100 [&>svg]:w-[15px] [&>svg]:h-[15px]",
};

const IconButton = ({
  icon: Icon,
  type = "button",
  variant = "primary",
  className = "",
  ...props
}) => {
  return (
    <button
      type={type}
      className={`flex items-center justify-center rounded-full hover:opacity-80 transition-opacity ${variantClassMap[variant]} ${className}`}
      {...props}
    >
      <Icon />
    </button>
  );
};

export default IconButton;
