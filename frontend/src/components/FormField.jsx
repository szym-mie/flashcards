const FormField = ({ children, label, className }) => {
  return (
    <div className={className}>
      <label htmlFor="" className="text-sm font-medium mb-1.5 block">
        {label}
      </label>
      {children}
    </div>
  );
};

export default FormField;
