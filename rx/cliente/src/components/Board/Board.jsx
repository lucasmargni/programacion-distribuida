import { Cell } from "../Cell/Cell";

export function Board({ board, onPlay }) {
  return (
    <div className="grid grid-cols-3 gap-3 my-5">
      {board.map((row, rowIndex) =>
        row.map((cell, colIndex) => (
          <Cell
            key={`${rowIndex}-${colIndex}`}
            value={cell}
            onClick={() => onPlay(rowIndex, colIndex)}
          />
        ))
      )}
    </div>
  );
}
