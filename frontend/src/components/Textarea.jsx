const Textarea = ({ children, className = "", ...props }) => {
  return (
    <textarea
      className={`px-4 py-2.5 h-24 w-full outline-none rounded-lg border disabled:opacity-50 ${className}`}
      {...props}
    >
      {children}
    </textarea>
  );
};

export default Textarea;
