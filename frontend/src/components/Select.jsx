import { forwardRef } from "react";

const SelectRender = ({ items = [], className = "", ...props }, ref) => {
  return (
    <select
      ref={ref}
      className={`px-4 h-12 w-full outline-none rounded-lg border disabled:opacity-50 ${className}`}
      {...props}
    >
      {items.map(({ id, name }) => (
        <option key={id} value={id}>
          {name}
        </option>
      ))}
    </select>
  );
};

const Select = forwardRef(SelectRender);

export default Select;
