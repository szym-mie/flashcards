const Select = ({ items = [], className = "", ...props }) => {
  return (
    <select
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

export default Select;
