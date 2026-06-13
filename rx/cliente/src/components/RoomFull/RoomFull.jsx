export function RoomFull() {
  return (
    <div className="text-center py-4">
      <p className="text-4xl mb-3">🚫</p>
      <h2 className="text-xl font-bold text-pink-600 mb-2">Sala llena</h2>
      <p className="text-pink-400 font-medium">
        Ya hay dos jugadores en partida. Intentá de nuevo más tarde.
      </p>
    </div>
  );
}
