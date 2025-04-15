package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Payment;
import java.util.List;

public interface IPaymentDao {
    int addPayment(Payment payment);
    int updatePayment(Payment payment);
    int deletePayment(int paymentId);
    Payment getPaymentById(int paymentId);
    List<Payment> getAllPayments();
    List<Payment> getPaymentsByStudentId(int studentId);
}

