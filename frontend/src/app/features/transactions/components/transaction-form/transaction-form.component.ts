import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Transaction, TransactionDTO } from '../../models/transaction';
import { NgIf } from '@angular/common';
import { TransactionService } from '../../services/transaction.service';

@Component({
  selector: 'app-transaction-form',
  imports: [FormsModule, NgIf],
  templateUrl: './transaction-form.component.html',
  styleUrl: './transaction-form.component.css'
})
export class TransactionFormComponent {
  @Input({ required: true }) transaction!: Transaction;
  @Output() closed = new EventEmitter<void>();
  @Output() transactionUpdated = new EventEmitter<Transaction>();

  constructor(private transactionService: TransactionService) {

  }

  closeForm() {
    this.closed.emit();
  }

  saveTransaction() {
    const t: TransactionDTO = {
      title: this.transaction.title,
      date: this.transaction.date,
      credit: this.transaction.credit,
      amount: this.transaction.amount,
      description: this.transaction.description,
      categoryId: this.transaction.category?.id,
      accountId: this.transaction.account?.id
    }

    if (t) {
      console.log(t);
      // Call the update method to send the PUT request
      this.transactionService.updateTransaction(this.transaction.id, t)
    .subscribe({
    next: updatedTransaction => {
      console.log('Transaction updated successfully:', updatedTransaction);
      this.transactionUpdated.emit(updatedTransaction);
      this.closed.emit(); // Close the form after successful update
    },
    error: error => {
      console.error('Error updating transaction:', error);
    },
    complete: () => {
      console.log('Transaction update complete');
    }
    });
  }
}
}
