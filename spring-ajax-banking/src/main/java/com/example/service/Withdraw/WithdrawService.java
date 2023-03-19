package com.example.service.Withdraw;

import com.example.model.Withdraw;
import com.example.repository.DepositRepository;
import com.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawService implements IWithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public Withdraw getById(Long id) {
        return null;
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void deleted(Withdraw withdraw) {

    }

    @Override
    public void deletedById(Long id) {

    }

    @Override
    public boolean existByIdEqual(Long id) {
        return false;
    }
}
