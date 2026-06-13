const PLAYER_1 = "X";
const PLAYER_2 = "O";

let CURR_PLAYER = PLAYER_1;
let TATETI_BOARD = [
  [" ", " ", " "],
  [" ", " ", " "],
  [" ", " ", " "],
];

export const play = (player, row, col) => {
  // Verifica que sea su turno
  if (player !== CURR_PLAYER) {
    return "No es tu turno";
  }

  // Verifica posiciones validas
  if (row >= 3 || row < 0 || col >= 3 || col < 0) {
    return "Posicion invalida";
  }

  // Verifica posicion vacia
  if (TATETI_BOARD[row][col] !== " ") {
    return "Posicion ya jugada";
  }

  TATETI_BOARD[row][col] = player;

  CURR_PLAYER = CURR_PLAYER === PLAYER_1 ? PLAYER_2 : PLAYER_1;
};

export const checkWinner = () => {
  // Filas
  for (let i = 0; i < 3; i++) {
    if (
      TATETI_BOARD[i][0] !== " " &&
      TATETI_BOARD[i][0] === TATETI_BOARD[i][1] &&
      TATETI_BOARD[i][0] === TATETI_BOARD[i][2]
    ) {
      return TATETI_BOARD[i][0];
    }
  }

  // Columnas
  for (let j = 0; j < 3; j++) {
    if (
      TATETI_BOARD[0][j] !== " " &&
      TATETI_BOARD[0][j] === TATETI_BOARD[1][j] &&
      TATETI_BOARD[0][j] === TATETI_BOARD[2][j]
    ) {
      return TATETI_BOARD[0][j];
    }
  }

  // Diagonales
  if (
    TATETI_BOARD[0][0] !== " " &&
    TATETI_BOARD[0][0] === TATETI_BOARD[1][1] &&
    TATETI_BOARD[0][0] === TATETI_BOARD[2][2]
  ) {
    return TATETI_BOARD[0][0];
  }
  if (
    TATETI_BOARD[0][2] !== " " &&
    TATETI_BOARD[0][2] === TATETI_BOARD[1][1] &&
    TATETI_BOARD[0][2] === TATETI_BOARD[2][0]
  ) {
    return TATETI_BOARD[0][2];
  }

  // Empate (tablero lleno sin ganador)
  const full = TATETI_BOARD.every((row) => !row.includes(" "));
  if (full) return "Empate";

  return null;
};

export const reset = () => {
  CURR_PLAYER = PLAYER_1;
  TATETI_BOARD = [
    [" ", " ", " "],
    [" ", " ", " "],
    [" ", " ", " "],
  ];
};

export const getBoard = () => {
  return TATETI_BOARD;
};

export const getCurrentPlayer = () => {
  return CURR_PLAYER;
}
