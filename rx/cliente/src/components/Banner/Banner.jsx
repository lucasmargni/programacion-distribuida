import { useBanner } from "../../hooks/useBanner";

export function Banner() {
  const url = useBanner();

  if (!url) {
    return (
      <div className="h-[120px] w-[320px] bg-pink-100 animate-pulse rounded-3xl border-4 border-pink-200 shadow-lg shadow-pink-100 flex items-center justify-center">
        <span className="text-pink-400 font-semibold text-lg tracking-wide">
          Cargando anuncio...
        </span>
      </div>
    );
  }

  return (
    <div className="h-[220px] w-fit max-w-full rounded-3xl overflow-hidden border-4 border-pink-200 shadow-lg shadow-pink-100 bg-white transition-transform duration-300">
      <img
        src={url}
        alt="Banner publicitario"
        className="h-full w-auto max-w-full object-contain block"
      />
    </div>
  );
}