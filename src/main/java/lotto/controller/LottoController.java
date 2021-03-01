package lotto.controller;

import lotto.domain.LottoResultStatistics;
import lotto.domain.lottos.LottoTicket;
import lotto.domain.lottos.LottoTickets;
import lotto.domain.lottos.amount.LottoAmount;
import lotto.domain.lottos.winnerlotto.LottoBonusNumber;
import lotto.domain.lottos.winnerlotto.LottoWinner;
import lotto.domain.money.Money;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    public void lottoStart() {
        Money money = initMoney();
        LottoAmount lottoAmount = initLottoAmount(money);
        LottoTickets lottoTickets = initLottoTickets(lottoAmount);

        LottoWinner lottoWinner = initLottoWinner();

        LottoResultStatistics lottoResultStatistics =
                LottoResultStatistics.calculateResultStatistics(lottoTickets, lottoWinner);
        OutputView.printRewardResultBoard(lottoResultStatistics);
        OutputView.printFinalResult(lottoResultStatistics, money);
    }

    private LottoAmount initLottoAmount(Money money) {
        try {
            return new LottoAmount(money, InputView.getManualCountInput());
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return initLottoAmount(money);
        }
    }

    private Money initMoney() {
        try {
            return new Money(InputView.getMoneyInput());
        } catch (NullPointerException | IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return initMoney();
        }
    }

    private LottoTickets initLottoTickets(LottoAmount lottoAmount) {
        try {
            LottoTickets lottoTickets =
                    LottoTickets.createLottoTickets(lottoAmount, InputView.getManualNumbersInput(lottoAmount.getManualAmount()));
            OutputView.printTickets(lottoTickets, lottoAmount);
            return lottoTickets;
        } catch (NullPointerException | IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return initLottoTickets(lottoAmount);
        }
    }

    private LottoWinner initLottoWinner() {
        LottoTicket lottoWinnerTicket = initLottoWinnerTicket();
        LottoBonusNumber lottoBonusNumber = initLottoWinnerBonusNumber(lottoWinnerTicket);
        return new LottoWinner(lottoWinnerTicket, lottoBonusNumber);
    }

    private LottoTicket initLottoWinnerTicket() {
        try {
            return LottoTicket.createManualLottoTicket(InputView.getWinnerNumbersInput());
        } catch (NullPointerException | IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return initLottoWinnerTicket();
        }
    }

    private LottoBonusNumber initLottoWinnerBonusNumber(LottoTicket lottoWinnerTicket) {
        try {
            return LottoBonusNumber.of(InputView.getBonusNumberInput(), lottoWinnerTicket);
        } catch (NullPointerException | IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return initLottoWinnerBonusNumber(lottoWinnerTicket);
        }
    }
}