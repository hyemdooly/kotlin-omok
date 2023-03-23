package player

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.point.Points
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import domain.state.FoulState
import domain.state.PlayingState
import domain.stone.StoneColor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class PlayersTest {
    private lateinit var blackRenjuRule: OmokRule
    private lateinit var whiteRenjuRule: OmokRule

    @BeforeEach
    fun setUp() {
        blackRenjuRule = BlackRenjuRule()
        whiteRenjuRule = WhiteRenjuRule()
    }

    @Test
    fun `현재 턴의 플레이어가 오목알을 놓는다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.putStone(Point(1, 1))
        val expected = Players(
                WhitePlayer(rule = whiteRenjuRule),
                BlackPlayer(state = PlayingState(Points(Point(1, 1))), rule = blackRenjuRule)
        )

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `모든 플레이어들의 상태가 플레이 중이면 참을 반환한다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.isPlaying
        assertThat(actual).isTrue
    }

    @Test
    fun `한 명이라도 플레이어의 상태가 플레이 중이 아니면 거짓을 반환한다`() {
        val players = Players(BlackPlayer(state = FoulState(), rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.isPlaying
        assertThat(actual).isFalse
    }

    @Test
    fun `현재 턴의 플레이어의 돌 색깔을 반환한다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.curPlayerColor
        assertThat(actual).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `마지막 놓은 돌을 반환한다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule)).putStone(Point(1, 1)).putStone(Point(2, 2))
        val actual = players.getLastPoint()

        assertThat(actual).isEqualTo(Point(2, 2))
    }

    @Test
    fun `플레이어 목록을 반환한다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.toList()

        assertThat(actual).usingRecursiveComparison().isEqualTo(listOf(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule)))
    }

    @Test
    fun `자기 자신을 깊은 복사할 수 있다`() {
        val players = Players(BlackPlayer(rule = blackRenjuRule), WhitePlayer(rule = whiteRenjuRule))
        val actual = players.copy()
        assertThat(actual).isNotSameAs(players)
    }
}
