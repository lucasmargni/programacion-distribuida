import { Server } from "socket.io";
import { play, checkWinner, reset, getBoard, getCurrentPlayer } from "./tateti.js";
import { getNextAd } from "./ads.js";

const PORT = process.env.PORT || 8000;

const io = new Server(PORT, {
  cors: { origin: "*" },
});

// registro de jugadores conectados: símbolo -> id del socket
const players = { X: null, O: null };

// cada un segundo, se envia la hora
setInterval(() => {
  const now = new Date();
  io.emit("time", {
    hour: now.getHours(),
    minute: now.getMinutes(),
    second: now.getSeconds(),
  });
}, 1000);

// cada 5 segundos, se envia una nueva publicidad
setInterval(() => {
  io.emit("ad", { url: getNextAd() });
}, 5000);

io.on("connect", (socket) => {
  // busca un simbolo libre
  let symbol = null;
  if (players.X === null) symbol = "X";
  else if (players.O === null) symbol = "O";

  // no hay lugar: sala llena
  if (symbol === null) {
    socket.emit("full");
    return;
  }

  players[symbol] = socket.id;
  console.log(`Nuevo cliente conectado: ${socket.id} (${symbol})`);

  // envia al jugador cual es
  socket.emit("assign", symbol);

  // envia el estado actual del juego al jugador conectado
  socket.emit("board", { board: getBoard(), winner: checkWinner(), currentPlayer: getCurrentPlayer() });

  // recibe la jugada de un jugador
  socket.on("play", ({ player, row, col }) => {
    const error = play(player, row, col);

    if (error) {
      socket.emit("error", error);
      return;
    }

    // enviamos tablero actualizado a los jugadores
    io.emit("board", { board: getBoard(), winner: checkWinner(), currentPlayer: getCurrentPlayer() });
  });

  // se resetea el tablero
  socket.on("reset", () => {
    reset();
    io.emit("board", { board: getBoard(), winner: checkWinner(), currentPlayer: getCurrentPlayer() });
  });

  // se desconecta un cliente
  socket.on("disconnect", () => {
    console.log(`Cliente desconectado: ${socket.id} (${symbol})`);
    players[symbol] = null;
  });
});