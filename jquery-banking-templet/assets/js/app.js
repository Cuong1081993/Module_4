class Customer {
    constructor(id, fullName, email, phone, address, balance, isDeleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isDeleted = isDeleted;
    }
}

class Deposit {
    constructor(id, customerId, transactionAmount, createdAt, createdBy, updatedAT, updatedBy, isDeleted) {
        this.id = id;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAT;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
    }
}