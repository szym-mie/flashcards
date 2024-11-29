const variantClassMap = {
  primary: "bg-black text-white",
  secondary: "bg-slate-100 text-black",
};

const Button = ({ type = "button", variant = "primary", text, ...props }) => {
  return (
    <button
      type={type}
      className={`px-4 py-2 rounded-lg text-[15px] font-medium ${variantClassMap[variant]}`}
      {...props}
    >
      {text}
    </button>
  );
};

export default Button;
