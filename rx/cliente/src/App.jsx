import { useTateti } from "./hooks/useTateti";
import { Board } from "./components/Board/Board";
import { GameInfo } from "./components/GameInfo/GameInfo";
import { RoomFull } from "./components/RoomFull/RoomFull";
import { Clock } from "./components/Clock/Clock";
import { Banner } from "./components/Banner/Banner";

export default function App() {
  const { connected, myPlayer, roomFull, board, currentPlayer, winner, error, play, reset } = useTateti();

  const handlePlay = (row, col) => {
    if (!winner) play(row, col);
  };

  return (
    <div className="min-h-screen bg-pink-50 flex flex-col items-center justify-center">
      <div className="absolute top-6 right-6">
        <Clock />
      </div>
      
      <div className="bg-white px-10 py-8 rounded-3xl shadow-xl shadow-pink-100 text-center w-fit">
        <h1 className="text-5xl font-bold text-pink-600 mb-5 [text-shadow:2px_2px_4px_rgba(255,182,193,0.5)]">
          Ta-Te-Ti
        </h1>

        {!connected ? (
          <p className="text-pink-400 font-semibold text-lg">
            Conectando con el servidor...
          </p>
        ) : roomFull ? (
          <RoomFull />
        ) : (
          <>
            <GameInfo
              myPlayer={myPlayer}
              currentPlayer={currentPlayer}
              winner={winner}
              error={error}
              onReset={reset}
            />
            <Board board={board} onPlay={handlePlay} />
          </>
        )}
      </div>
      <div className="pb-6 px-4 w-full flex justify-center">
        <Banner />
      </div>
    </div>
  );
}
