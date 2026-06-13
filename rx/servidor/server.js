import { Server } from "socket.io";
import { play, checkWinner, reset, getBoard, getCurrentPlayer } from "./tateti.js";
import { getNextAd } from "./ads.js";

const PORT = 8000;
let players = 0;

const io = new Server(PORT, {
  cors: { origin: "*" },
});

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
	if (players >= 2) {
		socket.emit("error", "Partida llena");
		socket.disconnect();
		return;
	}

	console.log("Nuevo cliente conectado:", socket.id);
	players++;
	const symbol = players === 1 ? "X" : "O"

	// envia al jugador cual es
	socket.emit("assign", symbol);

	// envia el estado actual del juego al jugador conectado
	socket.emit("board", { board: getBoard(), winner: checkWinner(), currentPlayer: getCurrentPlayer() });

	// recibe la jugada de un jugador
	socket.on("play", ({ player, row, col }) => {
		console.log("Juega el cliente:", socket.id);

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
		reset()
		io.emit("board", { board: getBoard(), winner: checkWinner(), currentPlayer: getCurrentPlayer() });
	});

	// se desconecta un cliente
	socket.on("dis	", () => {
		console.log("Cliente desconectado:", socket.id);
		players--;
	});
});