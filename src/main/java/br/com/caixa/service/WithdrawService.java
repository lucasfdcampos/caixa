package br.com.caixa.service;

import br.com.caixa.model.AtmOpening;
import br.com.caixa.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WithdrawService {
    private static final String NOT_MONEY = "ATM is not enough money";
    private static final String INSUFFICIENT_BANKNOTES = "Insufficient Banknotes";

    @Autowired
    private MoneyService moneyService;

    public Money withdraw(AtmOpening atmOpening, Double value) {

        try {
            Double valueAtmAmount = atmOpening.getCurrent().totalAmount();

            if (valueAtmAmount >= value) {

                Integer hundred, fifty, twenty, ten, five;
                hundred = fifty = twenty = ten = five = 0;

                Money money = moneyService.findById(atmOpening.getCurrent().getId());
                Integer quantHundred = money.getHundred();
                Integer quantFifty = money.getFifty();
                Integer quantTwenty = money.getTwenty();
                Integer quantTen = money.getTen();
                Integer quantFive = money.getFive();

                if (quantHundred > 0) {

                    Double result = value / 100d;
                    int quantDiv = result.intValue();

                    for (int i = 0; i < quantHundred; i++) {
                        hundred++;
                        if (quantDiv <= hundred) {
                            break;
                        }
                    }
                    value = value - (hundred * 100);
                }

                if ((value >= 50) && (quantFifty > 0)) {

                    Double result = value / 50d;
                    int quantDiv = result.intValue();

                    for (int i = 0; i < quantFifty; i++) {
                        fifty++;
                        if (quantDiv <= fifty) {
                            break;
                        }
                    }
                    value = value - (fifty * 50);
                }

                if ((value >= 20) && (quantTwenty > 0)) {

                    Double result = value / 20d;
                    int quantDiv = result.intValue();

                    for (int i = 0; i < quantTwenty; i++) {
                        twenty++;
                        if (quantDiv <= twenty) {
                            break;
                        }
                    }
                    value = value - (twenty * 20);
                }

                if ((value >= 10) && (quantTen > 0)) {

                    Double result = value / 10d;
                    int quantDiv = result.intValue();

                    for (int i = 0; i < quantTen; i++) {
                        ten++;
                        if (quantDiv <= ten) {
                            break;
                        }
                    }
                    value = value - (ten * 10);
                }

                if ((value >= 5) && (quantFive > 0)) {

                    Double result = value / 5d;
                    int quantDiv = result.intValue();

                    for (int i = 0; i < quantFive; i++) {
                        five++;
                        if (quantDiv <= five) {
                            break;
                        }
                    }
                    value = value - (five * 5);
                }

                if (value > 0) {
                    throw new ServiceException(INSUFFICIENT_BANKNOTES);
                }
                money.setFive(quantFive - five);
                money.setTen(quantTen - ten);
                money.setTwenty(quantTwenty - twenty);
                money.setFifty(quantFifty - fifty);
                money.setHundred(quantHundred - hundred);

                return money;

            } else {
                throw new ServiceException(NOT_MONEY);
            }
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
