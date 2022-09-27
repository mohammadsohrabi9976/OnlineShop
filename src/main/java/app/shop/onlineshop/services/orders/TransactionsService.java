package app.shop.onlineshop.services.orders;

import app.shop.onlineshop.entities.orders.Transactions;
import app.shop.onlineshop.helper.exceptions.DataNotFoundException;
import app.shop.onlineshop.helper.uiModels.StartPaymentVM;
import app.shop.onlineshop.repositories.orders.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository repository;

    public Transactions getByAuthority(String authority) {
        Transactions data = repository.findByAuthority(authority);
        return data;
    }

    public Transactions getById(long id) {
        Optional<Transactions> data = repository.findById(id);
        if (data.isPresent()) return data.get();
        return null;
    }

    public Transactions add(Transactions data) {
        return repository.save(data);
    }

    public Transactions add(StartPaymentVM startPaymentVM) {
        Transactions data = new Transactions();
        data.setRefId("");
        data.setAddDate(new Date());
        data.setAmount(startPaymentVM.getAmount());
        data.setAuthority(startPaymentVM.getAuthority());
        data.setCustomer(startPaymentVM.getCustomer());
        data.setDescription(startPaymentVM.getDescription());
        data.setInvoice(startPaymentVM.getInvoice());
        data.setStatus(startPaymentVM.getStatus());
        data.setPaymentType(startPaymentVM.getPaymentType());
        return repository.save(data);
    }

    public Transactions update(Transactions data) throws DataNotFoundException {
        Transactions oldData = getById(data.getId());
        if (oldData == null) {
            throw new DataNotFoundException("data with id " + data.getId() + " not found");
        }
        oldData.setRefId(data.getRefId());
        oldData.setVerifyStatus(data.getVerifyStatus());
        oldData.setTransactionVerifyStatus(data.getTransactionVerifyStatus());
        return repository.save(oldData);
    }

}

