import { useEffect, useState } from "react";
import { socket } from "../socket";

const SERVER_URL = "http://localhost:8000";

export function useTime() {
  const [time, setTime] = useState(null);

  useEffect(() => {
    const handleHora = ({ hour, minute, second }) => {
      setTime({ hora: hour, minuto: minute, segundo: second });
    };

    socket.on("time", handleHora);

    return () => {
      socket.off("time", handleHora);
    };
  }, []);

  return time;
}