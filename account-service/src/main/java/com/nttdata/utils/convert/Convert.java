package com.nttdata.utils.convert;

import com.nttdata.domain.models.AccountDto;
import com.nttdata.infraestructure.document.Account;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static AccountDto entityToDto(Account type) {
    AccountDto accountDto = new AccountDto();
    BeanUtils.copyProperties(type, accountDto);
    return accountDto;
  }

  public static Account dtoToEntity(AccountDto accountDto) {
    Account account = new Account();
    BeanUtils.copyProperties(accountDto, account);
    return account;
  }
}
