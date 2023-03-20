package com.example.service.transfer;

import com.example.model.Transfer;
import com.example.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TransferServiceIpm implements ITransferService{
    @Autowired
    private TransferRepository transferRepository;
    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id);
    }

    @Override
    public Transfer getById(Long id) {
        return null;
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void deleted(Transfer transfer) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existByIdEqual(Long id) {
        return false;
    }
}
