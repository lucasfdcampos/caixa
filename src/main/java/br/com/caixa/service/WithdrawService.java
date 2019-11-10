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

            if (valueAtmAmount > value) {
                Money money = moneyService.findById(atmOpening.getCurrent().getId());
                Integer quantHundred = money.getHundred();
                Integer quantFifty = money.getFifty();
                Integer quantTwenty = money.getTwenty();
                Integer quantTen = money.getTen();
                Integer quantFive = money.getFive();

                while(value != 0) {
                    if (value >= 100) {
                        while (value >= 100 && quantHundred > 0) {
                            value = value % 100d;
                            quantHundred--;
                        }
                    } else if (value >= 50) {
                        while (value >= 50 && quantFifty > 0) {
                            value = value % 50d;
                            quantFifty--;
                        }
                    } else if (value >= 20) {
                        while (value >= 20 && quantTwenty > 0) {
                            value = value % 20d;
                            quantTwenty--;
                        }
                    } else if (value >= 10) {
                        while (value >= 10 && quantTen > 0) {
                            value = value % 10d;
                            quantTen--;
                        }
                    } else if (value >= 5) {
                        while (value >= 5 && quantFive > 0) {
                            value = value % 5d;
                            quantFive--;
                        }
                    }
                }

                if (value > 0) {
                    throw new ServiceException(INSUFFICIENT_BANKNOTES);
                }
                money.setFive(quantFive);
                money.setTen(quantTen);
                money.setTwenty(quantTwenty);
                money.setFifty(quantFifty);
                money.setHundred(quantHundred);

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
