# conveyor

## Логика работы API:
  * *POST: /conveyor/offers*
  1. По API приходит *LoanApplicationRequestDTO*.
  2. На основании *LoanApplicationRequestDTO* происходит прескоринг создаётся 4 кредитных предложения
     LoanOfferDTO на основании всех возможных комбинаций булевских полей *isInsuranceEnabled* и
     *isSalaryClient (false-false, false-true, true-false, true-true)*. 
  3. Ответ на API - список из 4х *LoanOfferDTO* от "худшего" к "лучшему" (чем меньше итоговая ставка, тем лучше).
  * *POST: /conveyor/calculation*
  1. По API приходит *ScoringDataDTO*.
  2. Происходит скоринг данных, высчитывание ставки(rate), полной стоимости кредита(psk),
     размер ежемесячного платежа(*monthlyPayment*),
     график ежемесячных платежей (*List<PaymentScheduleElement>*). 
  3. Ответ на API - *CreditDTO*, насыщенный всеми рассчитанными параметрами.
