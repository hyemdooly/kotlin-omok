package domain.game

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.result.TurnResult

class Omok(blackPlayer: BlackPlayer, whitePlayer: WhitePlayer) {
    private var _players: Players
    val players: Players
        get() = _players.copy()

    init {
        _players = Players.from(blackPlayer, whitePlayer)
    }

    fun takeTurn(point: Point): TurnResult {
        val endTurnPlayers = _players.putPoint(point)
        if (endTurnPlayers == _players) return TurnResult.Playing(true, _players)
        _players = endTurnPlayers
        when {
            _players.isFoul -> return TurnResult.Foul(_players.curPlayerColor, _players)
            _players.isPlaying -> return TurnResult.Playing(false, _players)
        }
        return TurnResult.Win(_players.curPlayerColor.next(), _players)
    }

    companion object {
        const val OMOK_BOARD_SIZE = 15
    }
}
