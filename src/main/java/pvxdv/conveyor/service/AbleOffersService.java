package pvxdv.conveyor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AbleOffersService {

    /*
    Прескоринг
    Имя, Фамилия - от 2 до 30 латинских букв. Отчество, при наличии - от 2 до 30 латинских букв.
    Сумма кредита - действительно число, большее или равное 10000.
    Срок кредита - целое число, большее или равное 6.
    Дата рождения - число в формате гггг-мм-дд, не позднее 18 лет с текущего дня.
    Email адрес - строка, подходящая под паттерн [\w\.]{2,50}@[\w\.]{2,20}
    Серия паспорта - 4 цифры, номер паспорта - 6 цифр.

    получили
    public class LoanApplicationRequestDTO {
    private BigDecimal amount;
    private Integer term;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private LocalDate birthdate;
    private String passportSeries;
    private String passportNumber;
}
    отдали
public class LoanOfferDTO {
   private Long applicationId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

}

       По API приходит LoanApplicationRequestDTO.
    На основании LoanApplicationRequestDTO происходит прескоринг создаётся 4 кредитных предложения LoanOfferDTO
    на основании всех возможных комбинаций булевских полей
    isInsuranceEnabled и isSalaryClient (false-false, false-true, true-false, true-true).
    Логику формирования кредитных предложений можно придумать самому.
    К примеру: в зависимости от страховых услуг увеличивается/уменьшается процентная ставка и сумма кредита,
    базовая ставка хардкодится в коде через property файл.
    Например цена страховки 100к (или прогрессивная, в зависимости от запрошенной суммы кредита),
    ее стоимость добавляется в тело кредита, но она уменьшает ставку на 3.
    Цена зарплатного клиента 0, уменьшает ставку на 1.
    Ответ на API - список из 4х LoanOfferDTO от "худшего" к "лучшему" (чем меньше итоговая ставка, тем лучше).
     */
  public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
      log.info("тест логгера");

      // заглушка логики
      BigDecimal amount = loanApplicationRequestDTO.getAmount();
      BigDecimal requestedAmount = new BigDecimal(100000);
      //BigDecimal requestedAmount = new BigDecimal(amount.intValue())
      System.out.println(loanApplicationRequestDTO);
      System.out.println("TERM =" + loanApplicationRequestDTO.getTerm());
      Integer term = loanApplicationRequestDTO.getTerm();
      System.out.println(term);
      BigDecimal monthlyPayment = new BigDecimal(1000);
      BigDecimal rate = BigDecimal.valueOf(0);
      BigDecimal totalAmount = new BigDecimal(110000);

      List<LoanOfferDTO> result = new ArrayList<>();
      result.add(new LoanOfferDTO(new Random().nextLong(100), requestedAmount, totalAmount, term, monthlyPayment,
              rate, true, true));
      result.add(new LoanOfferDTO(new Random().nextLong(100), requestedAmount, totalAmount, term, monthlyPayment,
              rate, true, false));
      result.add(new LoanOfferDTO(new Random().nextLong(100), requestedAmount, totalAmount, term, monthlyPayment,
              rate, false, true));
      result.add(new LoanOfferDTO(new Random().nextLong(100), requestedAmount, totalAmount, term, monthlyPayment,
              rate, false, false));
      return  result;
  }
}
