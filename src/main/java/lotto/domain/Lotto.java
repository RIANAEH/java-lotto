package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Lotto {

    private static final int LOTTO_NUMBERS_SIZE = 6;

    private final Set<LottoNumber> numbers;

    public Lotto(final LottoMachine lottoMachine) {
        final Set<LottoNumber> lottoNumbers = new HashSet<>(lottoMachine.makeLottos());
        validateDuplicationAndSize(lottoNumbers);
        this.numbers = lottoNumbers;
    }

    private void validateDuplicationAndSize(final Set<LottoNumber> lottoNumbers) {
        if (lottoNumbers.size() != LOTTO_NUMBERS_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되지 않은 " + LOTTO_NUMBERS_SIZE + "개의 숫자여야합니다.");
        }
    }

    public List<Integer> getIntValues() {
        return numbers.stream()
                .map(LottoNumber::getNumber)
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }

    public Set<LottoNumber> getMatchedNumbers(final Set<LottoNumber> targetNumbers) {
        final Set<LottoNumber> copyNumbers = new HashSet<>(numbers);
        copyNumbers.retainAll(targetNumbers);
        return copyNumbers;
    }

    public boolean contains(LottoNumber number) {
        return numbers.contains(number);
    }

    public Set<LottoNumber> getLottoNumbers() {
        return new HashSet<>(numbers);
    }
}
