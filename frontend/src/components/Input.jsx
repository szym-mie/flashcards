import { forwardRef } from "react";

const InputRender = ({ type = "text", className = "", ...props }, ref) => {
  return (
    <input
      ref={ref}
      type={type}
      className={`px-4 h-12 w-full outline-none rounded-lg border disabled:opacity-50 ${className}`}
      {...props}
    />
  );
};

const Input = forwardRef(InputRender);

export default Input;
