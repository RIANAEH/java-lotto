package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lotto.constant.ErrorMessage.ERROR_WINNING_LOTTO_DUPLICATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    @Test
    @DisplayName("보너스 넘버가 당첨번호와 중복될 경우 예외를 발생시킨다")
    void throwExceptionWhenDuplicate() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 6;

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_WINNING_LOTTO_DUPLICATE.message());
    }

    @Test
    @DisplayName("당첨 번호와 보너스 번호를 가진 set을 반환한다")
    void makeWinningAndBonusNumbers() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int bonusNumber = 7;

        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusNumber);
        Set<LottoNumber> result = winningLotto.getWinningAndBonusNumber();
        Set<LottoNumber> expected = initExpected();

        assertThat(result).isEqualTo(expected);
    }

    private HashSet<LottoNumber> initExpected() {
        return new HashSet<>(List.of(
                LottoNumber.valueOf(2),
                LottoNumber.valueOf(3),
                LottoNumber.valueOf(1),
                LottoNumber.valueOf(4),
                LottoNumber.valueOf(5),
                LottoNumber.valueOf(6),
                LottoNumber.valueOf(7)
        ));
    }
}
