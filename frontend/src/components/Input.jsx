const Input = ({ type = "text", className = "", ...props }) => {
  return (
    <input
      type={type}
      className={`px-4 h-12 w-full outline-none rounded-lg border disabled:opacity-50 ${className}`}
      {...props}
    />
  );
};

export default Input;
