import { useEffect, useRef, useState } from "react";
import { socket } from "../socket";

export function useTateti() {
  const socketRef = useRef(null);

  const [connected, setConnected] = useState(false);
  const [myPlayer, setMyPlayer] = useState(null);
  const [roomFull, setRoomFull] = useState(false);
  const [board, setBoard] = useState([
    [" ", " ", " "],
    [" ", " ", " "],
    [" ", " ", " "],
  ]);
  const [currentPlayer, setCurrentPlayer] = useState(null);
  const [winner, setWinner] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    socketRef.current = socket;

    const onConnect = () => setConnected(true);
    const onDisconnect = () => setConnected(false);
    const onAssign = (symbol) => setMyPlayer(symbol);
    const onFull = () => setRoomFull(true);
    const onBoard = ({ board, currentPlayer, winner }) => {
      setBoard(board);
      setCurrentPlayer(currentPlayer);
      setWinner(winner ?? null);
    };
    const onError = (msg) => {
      setError(msg);
      setTimeout(() => setError(null), 2000);
    };

    socket.on("connect", onConnect);
    socket.on("disconnect", onDisconnect);
    socket.on("assign", onAssign);
    socket.on("full", onFull);
    socket.on("board", onBoard);
    socket.on("error", onError);

    return () => {
      socket.off("connect", onConnect);
      socket.off("disconnect", onDisconnect);
      socket.off("assign", onAssign);
      socket.off("full", onFull);
      socket.off("board", onBoard);
      socket.off("error", onError);
    };
  }, []);

  const play = (row, col) => {
    socketRef.current?.emit("play", { player: myPlayer, row, col });
  };

  const reset = () => {
    socketRef.current?.emit("reset");
  };

  return { connected, myPlayer, roomFull, board, currentPlayer, winner, error, play, reset };
}
