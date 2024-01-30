package com.cpt.payments.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.dto.Transaction;
import com.cpt.payments.pojo.TransactionReqRes;

@Component
public class TransactionMapper {
	
	private final ModelMapper modelMapper;

    @Autowired
    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction toDTO(TransactionReqRes request) {
        return modelMapper.map(request, Transaction.class);
    }

    public TransactionReqRes toResponseObject(Transaction userDTO) {
        return modelMapper.map(userDTO, TransactionReqRes.class);
    }
}
