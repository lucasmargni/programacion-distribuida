export function GameInfo({ myPlayer, currentPlayer, winner, error, onReset }) {
  const getWinnerMessage = () => {
    if (winner === "Empate") return "Empataron!!";
    if (winner === myPlayer) return "Ganaste :D";
    return "Perdiste :(";
  };

  return (
    <div className="text-center">
      {winner ? (
        <h2 className="text-2xl font-bold text-pink-600 my-3">
          {getWinnerMessage()}
        </h2>
      ) : (
        <p className="text-pink-500 font-medium mb-1">
          {currentPlayer === myPlayer
            ? "¡Es tu turno!"
            : `Esperando a ${currentPlayer}...`}
        </p>
      )}

      {winner && (
        <button
          onClick={onReset}
          className="bg-pink-400 hover:bg-pink-600 text-white font-bold py-3 px-7 rounded-full transition-colors duration-200 shadow-md mt-2"
        >
          Jugar de nuevo 
        </button>
      )}

      {error && (
        <div className="bg-pink-300 text-white font-bold p-3 rounded-xl mt-4">
          Oops! {error}
        </div>
      )}
    </div>
  );
}
