export function Cell({ value, onClick }) {
  const isEmpty = value.trim() === "";

  return (
    <button
      onClick={onClick}
      disabled={!isEmpty}
      className={`
        w-24 h-24 bg-white border-2 border-pink-200 rounded-2xl
        text-5xl font-bold
        flex items-center justify-center
        hover:bg-pink-50 active:scale-95
        transition-all duration-150
        disabled:cursor-not-allowed
        shadow-sm
        ${value === "X" ? "text-pink-500" : "text-purple-400"}
      `}
    >
      {isEmpty ? "" : value}
    </button>
  );
}
