import { useTime } from "../../hooks/useTime"

export function Clock() {

    const time = useTime();

    console.log(time)

    if (!time) {
        return (
          <div className="bg-pink-100 text-pink-400 font-bold text-xl py-2 px-6 rounded-full shadow-sm border-2 border-pink-200 tracking-widest">
            Cargando...
          </div>
        );
    }

  const formattedTime = [time.hora, time.minuto, time.segundo]
    .map((n) => String(n).padStart(2, "0"))
    .join(":");

    
  return (
    <div className="bg-pink-100 text-pink-500 font-bold text-xl py-2 px-6 rounded-full shadow-sm border-2 border-pink-200 tracking-widest">
      {formattedTime}
    </div>
  );
}